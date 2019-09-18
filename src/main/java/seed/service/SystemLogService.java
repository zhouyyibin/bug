package seed.service;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seed.controller.body.LoginBody;
import seed.entity.*;
import seed.mapper.SystemLogMapper;
import seed.tools.jwt.JwtUtil;
import seed.tools.restful.RestfulResult;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SystemLogService {

    @Autowired
    SystemLogMapper mapper;


    public SystemLog get(Long id) {
        return mapper.get(id);
    }

    public void save(Object[] args, String fileName, RestfulResult restfulResult) {


        SystemLog systemLog = new SystemLog();


        if (fileName.contains("login")) {//登陆
            User user = JwtUtil.getUserByTokent(((LoginBody) restfulResult.getData()).getToken());
            systemLog.setCreatorId(user.getId());
            systemLog.setCreatorName(user.getName());
            systemLog.setAction("login");
            systemLog.setContent(user.getName() + "于" +new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(systemLog.getCreatorTime())  + "登陆");

        } else if (fileName.contains("save")) {//新增
             systemLog = getNameByFileName("save", fileName, restfulResult.getData(),systemLog);


        } else if (fileName.contains("update")) {//修改
            systemLog = getNameByFileName("save", fileName, restfulResult.getData(),systemLog);

        } else if (fileName.contains("delete")) {//删除
            systemLog = getNameByFileName("save", fileName, restfulResult.getData(),systemLog);

        }else{
            systemLog = null;
        }

        if(systemLog !=null){
//            systemLog.setRequest(JSON.toJSONString(args));
//            systemLog.setResponse(JSON.toJSONString(restfulResult));
            systemLog.setCreatorTime(new Date());
            mapper.save(systemLog);
        }

    }

    public List<SystemLog> find(int page, int size) {
        return mapper.find((page - 1) * size, size);
    }

    public int getCount() {
        return mapper.getCount();
    }

    public Long save(SystemLog data) {
        return mapper.save(data);
    }

    private SystemLog getNameByFileName(String regex, String fileName, Object data, SystemLog log) {
        if (fileName == null || fileName.isEmpty()) {
            return log;
        }
        if (regex.equals("save")) {
            log.setAction("新增");
        } else if (regex.equals("update")) {
            log.setAction("修改");
        } else if (regex.equals("delete")) {
            log.setAction("删除");
        }
        switch (fileName.replace(regex, "")) {
            case "User":
                User user = ((User) data);
                log.setDataId(user.getId());
                log.setDataName(user.getName());
                log.setCreatorId(user.getCreatorId());
                log.setCreatorName(user.getCreatorName());
                log.setPermission("用户");
                log.setContent(user.getCreatorName()
                        + "于"
                        + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(user.getCreatorTime())
                        + log.getAction()
                        + "一条"
                        + log.getPermission()
                        + ","
                        + log.getPermission()
                        + "名"
                        + log.getAction()
                        + "为"
                        + user.getName());
                break;
            case "Bug":
                Bug bug = ((Bug) data);
                log.setDataId(bug.getId());
                log.setDataName(bug.getTitle());
                log.setPermission("Bug");
                log.setCreatorId(bug.getCreatorId());
                log.setCreatorName(bug.getCreatorName());
                log.setContent(bug.getCreatorName()
                        + "于"
                        + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(bug.getCreatorTime())
                        + log.getAction()
                        + "一条"
                        + log.getPermission()
                        + ","
                        + log.getPermission()
                        + "名"
                        + log.getAction()
                        + "为"
                        + bug.getTitle());
                break;
            case "Company":
                Company company = ((Company) data);
                log.setDataId(company.getId());
                log.setDataName(company.getName());
                log.setPermission("公司");
                log.setCreatorId(company.getCreatorId());
                log.setCreatorName(company.getCreatorName());
                log.setContent(company.getCreatorName()
                        + "于"
                        + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(company.getCreatorTime())
                        + log.getAction()
                        + "一条"
                        + log.getPermission()
                        + ","
                        + log.getPermission()
                        + "名"
                        + log.getAction()
                        + "为"
                        + company.getName());
                break;
            case "Department":
                Department department = ((Department) data);
                log.setDataId(department.getId());
                log.setDataName(department.getName());
                log.setPermission("部门");
                log.setCreatorId(department.getCreatorId());
                log.setCreatorName(department.getCreatorName());
                log.setContent(department.getCreatorName()
                        + "于"
                        + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(department.getCreatorTime())
                        + log.getAction()
                        + "一条"
                        + log.getPermission()
                        + ","
                        + log.getPermission()
                        + "名"
                        + log.getAction()
                        + "为"
                        + department.getName());
                break;

            case "Permission":
                Permission permission = ((Permission) data);
                log.setDataId(permission.getId());
                log.setDataName(permission.getName());
                log.setPermission("权限");
                log.setCreatorId(permission.getCreatorId());
                log.setCreatorName(permission.getCreatorName());
                log.setContent(permission.getCreatorName()
                        + "于"
                        + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(permission.getCreatorTime())
                        + log.getAction()
                        + "一条"
                        + log.getPermission()
                        + ","
                        + log.getPermission()
                        + "名"
                        + log.getAction()
                        + "为"
                        + permission.getName());
                break;

            case "Role":
                Role role = ((Role) data);
                log.setDataId(role.getId());
                log.setDataName(role.getName());
                log.setPermission("角色");
                log.setCreatorId(role.getCreatorId());
                log.setCreatorName(role.getCreatorName());
                log.setContent(role.getCreatorName()
                        + "于"
                        + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(role.getCreatorTime())
                        + log.getAction()
                        + "一条"
                        + log.getPermission()
                        + ","
                        + log.getPermission()
                        + "名"
                        + log.getAction()
                        + "为"
                        + role.getName());
                break;

            case "SystemClassification":
                SystemClassification systemClassification = ((SystemClassification) data);
                log.setDataId(systemClassification.getId());
                log.setDataName(systemClassification.getName());
                log.setCreatorId(systemClassification.getCreatorId());
                log.setCreatorName(systemClassification.getCreatorName());
                log.setPermission("系统类别");
                log.setContent(systemClassification.getCreatorName()
                        + "于"
                        + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(systemClassification.getCreatorTime())
                        + log.getAction()
                        + "一条"
                        + log.getPermission()
                        + ","
                        + log.getPermission()
                        + "名"
                        + log.getAction()
                        + "为"
                        + systemClassification.getName());
                break;

            case "BugLog":
                BugLog bugLog = ((BugLog) data);
                log.setDataId(bugLog.getId());
                log.setDataName(bugLog.getDescribe());
                log.setPermission("Bug记录");
                log.setCreatorId(bugLog.getCreatorId());
                log.setCreatorName(bugLog.getCreatorName());
                log.setContent(bugLog.getCreatorName()
                        + "于"
                        + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(bugLog.getCreatorTime())
                        + log.getAction()
                        + "一条"
                        + log.getPermission()
                        + ","
                        + log.getPermission()
                        + "名"
                        + log.getAction()
                        + "为"
                        + bugLog.getDescribe());
                break;

            default:
                break;
        }
        return log;

    }


}