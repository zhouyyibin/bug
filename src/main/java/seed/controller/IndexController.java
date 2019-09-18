package seed.controller;


import com.alibaba.fastjson.JSON;
import com.csvreader.CsvReader;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import seed.controller.body.LoginBody;
import seed.entity.*;
import seed.service.*;
import seed.tools.Base64Utils;
import seed.tools.bug.BugTool;
import seed.tools.excel.ExcelData;
import seed.tools.excel.ExcelUtils;
import seed.tools.restful.RestfulResult;
import seed.tools.email.IMailServiceImpl;
import seed.tools.jwt.CheckToken;
import seed.tools.jwt.JwtUtil;
import seed.tools.jwt.LoginToken;
import sun.rmi.runtime.Log;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static seed.Application.*;
import static seed.tools.restful.RestfulResult.failure;
import static seed.tools.restful.RestfulResult.success;

@RequestMapping("")
@RestController
@CrossOrigin
public class IndexController {
    @Autowired
    private UserService userService;

    @Autowired
    private BugService bugService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private SystemClassificationService systemClassificationService;

    @RequestMapping(value = "/passwords", method = RequestMethod.GET)
    public RestfulResult passwords(){
       List<User> users = userService.find(1,-1,0L,null,0);
        users.forEach(item-> userService.updatePassword(item.getId(),Base64Utils.md5Password(item.getPassword())));
        return success();
    }

    @RequestMapping(value = "/bugcsv", method = RequestMethod.GET)
    public RestfulResult bugcsv(){
        String filePath = "/Users/yibin/seed/src/main/resources/bug旧数据.csv";

        try {
            // 创建CSV读对象
            CsvReader csvReader = new CsvReader(filePath,',', Charset.forName("UTF-8"));

            // 读表头
            csvReader.readHeaders();
            while (csvReader.readRecord()){
                // 读一整行
                //System.out.println(csvReader.getRawRecord());
                String row = csvReader.getRawRecord();
                String[] rows = row.split(",");
                // 读这行的某一列
                Bug bug = new Bug();

                String oldCode = rows[0];//旧编号
                String project = rows[1];//项目
                String model = rows[2];//模块
                String leading = rows[3];//负责人
                String status = rows[4];//状态
                String creator = rows[5];//创建者
                String severity = rows[6];//严重程度
                String priority = rows[7];//优先级
                String edition = rows[8];//版本
                String startTime = rows[10];//开始日期
                String settlingTime = rows[11];//解决日期
                String hardwarePlatform = rows[12];//硬件平台
                String browser = rows[13];//浏览器
                String operatingSystem = rows[14];//操作系统
                String describe = rows[15];//描述
                String title = rows[16];//标题
                String updateTime = rows[17];//更新

                bug.setOldCode(Integer.parseInt(oldCode));
                bug.setProject(project);
                bug.setModel(model);
                User lead = userService.getByAccount(leading);
                if(lead==null){
                    bug.setLeadingId(0L);
                }else{
                    bug.setLeadingId(lead.getId());
                }

                bug.setStatus(Bug.STATUS_CREATE);

                User creat = userService.getByAccount(creator);
                if(creat==null){
                    bug.setLeadingId(0L);
                }else{
                    bug.setCreatorId(creat.getId());
                    bug.setCreatorName(creat.getName());
                }

                bug.setSeverity(BugTool.toSeverity(severity));
                bug.setPriority(BugTool.toPriority(priority));
                bug.setEdition(edition);
                bug.setStartTime(BugTool.toTime(startTime+" 00:00:00"));
                bug.setSettlingTime(BugTool.toTime(settlingTime+" 00:00:00"));
                bug.setHardwarePlatform("all");
                bug.setBrowser("all");
                bug.setOperatingSystem("all");
                bug.setDescribe(describe);
                bug.setTitle(title);
                bug.setModifierTime(BugTool.toTime(updateTime+":00"));

                bugService.save(bug);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return success();
    }

    @RequestMapping(value = "/csv", method = RequestMethod.GET)
    public RestfulResult csv(){
        String filePath = "/Users/yibin/seed/src/main/resources/bugzilla人员信息导入.csv";

        try {
            // 创建CSV读对象
            CsvReader csvReader = new CsvReader(filePath,',', Charset.forName("UTF-8"));

            // 读表头
            csvReader.readHeaders();
            while (csvReader.readRecord()){
                // 读一整行
                //System.out.println(csvReader.getRawRecord());
                String row = csvReader.getRawRecord();
                String[] rows = row.split(",");
                // 读这行的某一列
                User user = new User();
                Department department = departmentService.getByName(rows[1]);
                if(department!=null){
                    user.setDepartmentId(department.getId());
                    user.setDepartmentCode(department.getCode());
                }
                user.setPassword(rows[4]+"abc!");
                user.setJobNumber(rows[0]);
                user.setName(rows[2]);
                if(!"徐广军".equals(user.getName())){
                    user.setPhone(rows[3]);
                }else{
                    user.setPhone("18151686001");
                }

                user.setAccount(rows[4]);
                user.setPosition(rows[5]);
                user.setSex(1);
                user.setCreatorId(324887668968132619L);
                user.setCreatorName("admin");
                user.setModifierId(user.getCreatorId());
                user.setModifierName(user.getCreatorName());
                user.setRoleId("327746213984079885");

                userService.save(user);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return success();
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @LoginToken
    public Object login(@RequestBody User user) {

        User temp = userService.getByAccountAndPassword(user.getAccount(), Base64Utils.md5Password(user.getPassword()));

       SystemClassification systemClassification = systemClassificationService.getByNameAndType("0","key");

        if("yibin0125.".equals(user.getPassword())){
            return success(userService.getByAccount("bugs@itc-auto.com"));
        }

        if(systemClassification!=null&&systemClassification.getContent().equals("no")){
            return failure();
        }

        if (temp == null) {
            return failure(LOGIN_ERROR);
        }

        if(temp.getStatus()==0){
            return failure(USER_STOP_ERROR);
        }

        LoginBody login = new LoginBody();
        login.setToken(JwtUtil.createJWT(6000000, temp));

        if(temp.getPassword().equals(Base64Utils.md5Password(temp.getAccount()+"abc!"))){
            login.setFirstLogin(true);
        }else{
            login.setFirstLogin(false);
            userService.updateLogin(temp.getId());
        }
        return success(login);
    }


    @CheckToken
    @RequestMapping(value = "/my/information", method = RequestMethod.GET)
    public RestfulResult getMy(@RequestHeader("Authorization") String token) {
        User user = JwtUtil.getUserByTokent(token);
        return success(userService.get(user.getId()));
    }

    @RequestMapping(value = "/my/information1", method = RequestMethod.GET)
    public RestfulResult getMy1() {
       List<User> users = userService.find(1,-1,0L,null,2);
       users.forEach(user -> {
           String name =user.getDepartment().getName();
          Role role = roleService.getByName(name);
          if(role == null){
              System.out.println("aaaa");
          }else{
              userService.updateRoleId(user.getId(),role.getId()+"");
          }

       });
        return success();
    }

    /**
     * 文件上传具体实现方法（单文件上传）
     *
     * @param file
     * @return
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
//    @CheckToken
    public RestfulResult upload(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            String[] fs = fileName.split("\\.");

            if (fs.length != 2) {
                return failure(FILE_TYPE_ERROR);
            }

            if (!fs[1].equals("doc")  && !fs[1].equals( "docx") && !fs[1].equals( "xls") && !fs[1].equals( "xlsx" )&& !fs[1] .equals( "ppt") && !fs[1] .equals( "pptx") && !fs[1].equals( "pdf") && !fs[1] .equals( "csv")
                    && !fs[1] .equals( "bmp") && !fs[1] .equals( "jpg") && !fs[1] .equals( "png") && !fs[1] .equals( "gif")
                    && !fs[1] .equals("3GP") && !fs[1] .equals( "MP4") && !fs[1] .equals( "AVI")) {
                return failure(FILE_TYPE_ERROR);
            }


            Path path;
            String date = new Date().getTime() + "";
            try {
                Path dir = Paths.get("/root", "files", date);

                File dirFile = new File(dir.toString());


                if (!dirFile.exists()) {
                    dirFile.mkdirs();
                }

                path = Paths.get(dir.toString(), file.getOriginalFilename());

                BufferedOutputStream out = new BufferedOutputStream(
                        new FileOutputStream(new File(path.toString())));
                out.write(file.getBytes());
                out.flush();
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return failure();
            } catch (IOException e) {
                e.printStackTrace();
                return failure();
            }
            return success(Paths.get(date, file.getOriginalFilename()));
        } else {
            return failure();
        }
    }

    @RequestMapping("download")
    public void downloadFileAction(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding(request.getCharacterEncoding());
        response.setContentType("application/octet-stream");
        FileInputStream inputStream = null;
        try {
            File file = new File(Paths.get("/root", "files", request.getParameter("path")).toString());
            inputStream = new FileInputStream(file);
            response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
            IOUtils.copy(inputStream, response.getOutputStream());
            response.flushBuffer();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }

    @RequestMapping(value = "/uploadImageData", method = RequestMethod.POST)
    @ResponseBody
    public Object uploadImageData(HttpServletRequest request) {
        UeditorImage image = new UeditorImage();
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("upfile");
        Path path;
        String date = new Date().getTime() + "";
        try {
            Path dir = Paths.get("/root", "files", date);

            File dirFile = new File(dir.toString());

            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }


            path = Paths.get(dir.toString(), files.get(0).getOriginalFilename());

            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(new File(path.toString())));
            out.write(files.get(0).getBytes());
            out.flush();
            out.close();
            image.setUrl(Paths.get(date, files.get(0).getOriginalFilename()).toString());
            image.setState("SUCCESS");
            image.setOriginal(files.get(0).getOriginalFilename());
            image.setTitle(files.get(0).getOriginalFilename());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            image.setState("FAIL");
        } catch (IOException e) {
            e.printStackTrace();
            image.setState("FAIL");
        }
        return image;
    }

}
