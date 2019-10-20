package seed.controller;


import com.alibaba.fastjson.JSONArray;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import seed.entity.*;
import seed.service.*;
import seed.tools.excel.ExcelData;
import seed.tools.excel.ExcelUtils;
import seed.tools.restful.RestfulAntPageResult;
import seed.tools.restful.RestfulResult;
import seed.tools.jwt.CheckToken;
import seed.tools.jwt.JwtUtil;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static seed.Application.BUG_NAME_ERROR;
import static seed.tools.restful.RestfulResult.failure;
import static seed.tools.restful.RestfulResult.success;

@RequestMapping("/bug")
@RestController
@CrossOrigin
public class BugController {
    @Autowired
    private BugService service;

    @Autowired
    private UserService userService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private RoleService roleService;

    @CheckToken
    @RequestMapping(value = "", method = RequestMethod.POST)
    public RestfulResult saveBug(@Valid @RequestBody Bug body,@RequestHeader("Authorization") String token) {
        Bug bug = service.getByTitle(body.getTitle());

        if(bug!=null){
            return failure(BUG_NAME_ERROR);
        }

        User user = JwtUtil.getUserByTokent(token);
        body.setCreatorId(user.getId());
        body.setCreatorName(user.getName());
        body.setModifierId(user.getId());
        body.setModifierName(user.getName());
        body.setStatus(Bug.STATUS_CREATE);
        if(body.getAnnexs()!=null&&body.getAnnexs().size()>0){
            body.setAnnex(StringUtils.join(body.getAnnexs(), ","));
        }
//        if(body.getSendMailUsers()!=null&&body.getSendMailUsers().size()>0){
//            body.setSendMails(JSONArray.toJSONString(body.getSendMailUsers()));
//        }
        service.save(body);
        return success(service.get(body.getId()));
    }

    @CheckToken
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public RestfulResult updateBug(@PathVariable("id") long id, @Valid @RequestBody BugLog body, @RequestHeader("Authorization") String token) {
        User user = JwtUtil.getUserByTokent(token);
        body.setCreatorId(user.getId());
        body.setCreatorName(user.getName());
        body.setModifierId(user.getId());
        body.setModifierName(user.getName());
        if(body.getAnnexs()!=null&&body.getAnnexs().size()>0){
            body.setAnnex(StringUtils.join(body.getAnnexs(), ","));
        }
        service.update(body);
        return success(service.get(body.getId()));
    }

    @CheckToken
    @RequestMapping(value = "/{id}/base", method = RequestMethod.PUT)
    public RestfulResult updateBugBase(@PathVariable("id") long id,@Valid @RequestBody Bug body,@RequestHeader("Authorization") String token) {
        User user = JwtUtil.getUserByTokent(token);
        body.setCreatorId(user.getId());
        body.setCreatorName(user.getName());
        body.setModifierId(user.getId());
        body.setModifierName(user.getName());
        if(body.getAnnexs()!=null&&body.getAnnexs().size()>0){
            body.setAnnex(StringUtils.join(body.getAnnexs(), ","));
        }
//        if(body.getSendMailUsers()!=null&&body.getSendMailUsers().size()>0){
//            body.setSendMails(JSONArray.toJSONString(body.getSendMailUsers()));
//        }
        service.updateBase(body);
        return success(service.get(body.getId()));
    }

    @CheckToken
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public RestfulResult getBug(@PathVariable("id") long id) {
       // Bug bug = service.get(id);
//        if(bug!=null){
//            bug.setSendMailUsers(userService.stringIdsToListUser(bug.getSendMails()));
//        }
        return success(service.get(id));
    }

    @CheckToken
    @RequestMapping(value = "/list/{page}", method = RequestMethod.GET)
    public RestfulAntPageResult list(@RequestHeader("Authorization") String token,
                              @PathVariable("page") int page,
                              @RequestParam(value = "size", required = false, defaultValue = "25") int size,
                              @RequestParam(value ="type",required = false, defaultValue = "0") int type,
                                     @RequestParam(value = "department", required = false, defaultValue = "0") String department,
                                     @RequestParam(value = "project", required = false, defaultValue = "0") String project,
                                     @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword) throws UnsupportedEncodingException {
        User tUser = JwtUtil.getUserByTokent(token);

        User user = userService.get(tUser.getId());

        if(!"".equals(keyword)){
            java.net.URLDecoder.decode(keyword,"utf-8");
        }

        Project temp = projectService.get(Long.parseLong(project));
        if(temp!=null){
            project = temp.getName();
        }

        if(user.getSetData()!=1&& StringUtils.isEmpty(keyword)&&type==0&&department.equals(0)){
            return new RestfulAntPageResult<>(page,size,0, new ArrayList<>());
        }


        if("0".equals(project)){
            project = null;
        }

        List<Bug> list = service.find(page,size,type,user.getId(),keyword,department,project);
        int count = service.getCount(type,user.getId(),keyword,department,project);
        return new RestfulAntPageResult<>(page,size,count, list);
    }

    @CheckToken
    @RequestMapping(value = "/{department}/list", method = RequestMethod.GET)
    public RestfulAntPageResult departmentList(@PathVariable("department") String department)  {
        if(department.equals("0")){
            List<Bug> list = service.findByDepartment("0");
            int count = service.getCountByDepartment("0");
            return new RestfulAntPageResult<>(1,1000,count, list);
        }

       Department depart = departmentService.get(Long.parseLong(department));

        if(depart==null){
            return new RestfulAntPageResult<>(1,1000,0, new ArrayList<>());
        }

        List<Bug> list = service.findByDepartment(depart.getCode());
        int count = service.getCountByDepartment(depart.getCode());
        return new RestfulAntPageResult<>(1,1000,count, list);

    }


    @CheckToken
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public RestfulResult deleteBug(@PathVariable(value = "id") Long id) {
       service.delete(id);
        return RestfulResult.success();
    }

    @CheckToken
    @RequestMapping(value = "/statistics/count", method = RequestMethod.GET)
    public RestfulResult getCount( @RequestHeader("Authorization") String token,
                                   @RequestParam(value = "project", required = false, defaultValue = "") String project,
                                   @RequestParam(value = "department", required = false, defaultValue = "0") String department,
                                   @RequestParam(value = "type", required = false, defaultValue = "0") int type) {
        List<BugService.StatisticsBean> statisticsBeans = new ArrayList<>();
        User user = JwtUtil.getUserByTokent(token);
        if(department==null ||department.isEmpty()||"0".equals(department)){
            statisticsBeans.add(new BugService.StatisticsBean("所有未解决",0,service.getCountAllDoing(0L,0L)));
            statisticsBeans.add(new BugService.StatisticsBean("所有已解决",1,service.getCountAllFinish(0L,0L)));
            statisticsBeans.add(new BugService.StatisticsBean("所有超时",2,service.getCountAllTimeOut(0L,0L)));
        }else{
            statisticsBeans.add(new BugService.StatisticsBean("所有未解决",0,service.getCountDepartmentDoing(department)));
            statisticsBeans.add(new BugService.StatisticsBean("所有已解决",1,service.getCountDepartmentFinish(department)));
            statisticsBeans.add(new BugService.StatisticsBean("所有超时",2,service.getCountDepartmentTimeOut(department)));
        }
        statisticsBeans.add(new BugService.StatisticsBean("指派给我未解决",10,service.getCountAllDoing(user.getId(),0L)));
        statisticsBeans.add(new BugService.StatisticsBean("指派给我已解决",11, service.getCountAllFinish(user.getId(),0L)));
        statisticsBeans.add(new BugService.StatisticsBean("指派给我超时",12,service.getCountAllTimeOut(user.getId(),0L)));
        statisticsBeans.add(new BugService.StatisticsBean("我创建未解决",20,service.getCountAllDoing(0L,user.getId())));
        statisticsBeans.add(new BugService.StatisticsBean("我创建已解决",21,service.getCountAllFinish(0L,user.getId())));
        statisticsBeans.add(new BugService.StatisticsBean("我创建超时",22,service.getCountAllTimeOut(0L,user.getId())));
        statisticsBeans.add(new BugService.StatisticsBean("项目未解决",30,service.getCountProjectDoing(project)));
        statisticsBeans.add(new BugService.StatisticsBean("项目已解决",31,service.getCountProjectFinish(project)));
        statisticsBeans.add(new BugService.StatisticsBean("项目超时",32,service.getCountProjectTimeOut(project)));
        statisticsBeans.add(new BugService.StatisticsBean("部门未解决",40,service.getCountDepartmentDoing(department)));
        statisticsBeans.add(new BugService.StatisticsBean("部门已解决",41,service.getCountDepartmentFinish(department)));
        statisticsBeans.add(new BugService.StatisticsBean("部门超时",42,service.getCountDepartmentTimeOut(department)));
        return success(statisticsBeans);
    }

    @CheckToken
    @RequestMapping(value = "/top/list", method = RequestMethod.GET)
    public RestfulAntPageResult listTop(@RequestHeader("Authorization") String token)  {
        User user = JwtUtil.getUserByTokent(token);
        List<Bug> list = service.findTop(1,10,1,user.getId());
        return new RestfulAntPageResult<>(1,10,10, list);
    }



    @RequestMapping(value = "/{id}/export", method = RequestMethod.GET)
    public void excel(HttpServletResponse response,
                      @PathVariable("id") String id,
                      @RequestParam(value ="type",required = false, defaultValue = "0") int type,
                      @RequestParam(value = "department", required = false, defaultValue = "0") String department,
                      @RequestParam(value = "project", required = false, defaultValue = "0") String project,
                      @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword) throws Exception {

        User user = userService.get(Long.parseLong(id));
        List<Bug> list;

        if(!"".equals(keyword)){
            java.net.URLDecoder.decode(keyword,"utf-8");
        }

        Project temp = projectService.get(Long.parseLong(project));
        if(temp!=null){
            project = temp.getName();
        }
        if("0".equals(project)){
            project = null;
        }

        if(user.getSetData()!=1&& StringUtils.isEmpty(keyword)&&type==0&&department.equals(0)){
            list = new ArrayList<>();
        }else{
            list = service.find(1,10000,type,user.getId(),keyword,department,project);
        }


        ExcelData data = new ExcelData();
        data.setName("BUG报表");
        SimpleDateFormat fdate=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String titleStr ="Bug编号,发生客户,发生地,发生厂区,生产机型,项目阶段,项目编号,项目,影响版本,模块,Bug类型,Bug标题,问题描述,原因分析,解决措施,严重程度" +
                ",优先级,负责部门,负责人,反馈日期,开始时间,要求解决时间,实际解决日期,处理周期,工时损耗,当前进度,状态,提出部门,创建人,备注";
        String[] titlesArray =titleStr.split(",");

        //添加表头
        List<String> titles = new ArrayList();
        for(int i=0;i<titlesArray.length;i++){
            titles.add(titlesArray[i]);
        }
        data.setTitles(titles);

        if(list.size()==0){
            List<List<Object>> rows = new ArrayList();
            List<Object> row =new ArrayList();
            titles.forEach(title->row.add(""));
            rows.add(row);
            data.setRows(rows);
        }else{
            //添加列
            List<List<Object>> rows = new ArrayList();
            for (Bug bug:list) {
                List<Object> row =new ArrayList();
                row.add(bug.getCode());
                row.add("");
                row.add("");
                row.add("");
                row.add("");
                row.add("");
                row.add("");
                row.add(bug.getProject());
                row.add(bug.getEdition());
                row.add(bug.getModel());
                row.add(bug.getType());
                row.add(bug.getTitle());
                row.add(bug.getDescribe());
                row.add("");
                row.add("");
                row.add(service.getNameBySeverity(Integer.parseInt(bug.getSeverity())));
                row.add(service.getNameByPriority(Integer.parseInt(bug.getPriority())));
                row.add("");
                User leading = userService.get(bug.getLeadingId());
                if(leading!=null){
                    row.add(leading.getName());
                }
                row.add("");
                row.add(fdate.format(bug.getStartTime()));
                row.add(fdate.format(bug.getSettlingTime()));
                row.add("");
                row.add(bug.getAllCycles());
                row.add("");
                row.add("");
                row.add(service.getNameByStatus(bug.getStatus()));
                row.add("");
                row.add(bug.getCreatorName());
                row.add("");
                rows.add(row);
                data.setRows(rows);
            }
        }



        String fileName=fdate.format(new Date())+".xls";
        ExcelUtils.exportExcel(response, fileName, data);
    }

    @CheckToken
    @RequestMapping(value = "/statistics/all/count", method = RequestMethod.GET)
    public RestfulResult getCountAll( @RequestHeader("Authorization") String token) {
        return success(service.getStatistics());
    }

}
