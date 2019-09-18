package seed.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seed.entity.*;
import seed.mapper.BugLogMapper;
import seed.mapper.BugMapper;
import seed.tools.DateUtil;
import seed.tools.email.MailUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static seed.entity.Bug.STATUS_CREATE;

@Service
@Transactional
public class BugService {


    @Autowired
    BugMapper mapper;

    @Autowired
    BugLogMapper bugLogMapper;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    UserService userService;

    @Autowired
    ProjectService projectService;

    @Autowired
    MailUtil mailUtil;


    public Bug get(Long id){
        Bug  bug= mapper.get(id);
        if(bug == null){
            return null;
        }
        if(bug.getSendMails()!=null&&!bug.getSendMails().isEmpty()){
            String[] users = bug.getSendMails().split("\\,");
            if(users.length>0){
                List<User> userList = new ArrayList<>();
                for(int i=0;i<users.length;i++){
                    User user  = userService.get(Long.parseLong(users[i]));
                    if(user!=null){
                        userList.add(user);
                    }
                }
                bug.setSendMailUsers(userList);
            }
        }

        return bug;
    }

    public Bug getByTitle(String title){
        return mapper.getByTitle(title);
    }

    public Long  save(Bug data){
        data.setAllCycles(DateUtil.getDuration(data.getSettlingTime(),data.getStartTime())+"");
        mapper.save(data);
        BugLog bugLog = new BugLog();
        bugLog.setCreatorId(data.getCreatorId());
        bugLog.setCreatorName(data.getModifierName());
        bugLog.setBugId(data.getId());
        bugLog.setStatus(STATUS_CREATE);
        bugLogMapper.save(bugLog);
       return data.getId();
    }

    public List<Bug> find(int page, int size,int type,Long userId,String keyword,String department,String project){
        Long leadingId=0L;
        Long creatorId=0L;
         if(type == 1){//指派给我的
             leadingId = userId;
        }else if(type == 2){// 由我创建的
             creatorId = userId;
        }else if(type == 3){//抄送给我的
             leadingId=-1L;
             creatorId=-1L;

         }

         Department department1 = departmentService.get(Long.parseLong(department));
         if(department1==null){
             return mapper.find((page-1)*size,size,leadingId,creatorId,keyword,null,project,userId);
         }
        return mapper.find((page-1)*size,size,leadingId,creatorId,keyword,department1.getCode(),project,userId);
    }

    public List<Bug> findTop(int page, int size,int type,Long userId){
        Long leadingId=0L;
        Long creatorId=0L;
        if(type == 1){//指派给我的
            leadingId = userId;
        }else if(type == 2){// 由我创建的
            creatorId = userId;
        }
        return mapper.findTop((page-1)*size,size,leadingId,creatorId);
    }

    public int getCount(int type,Long userId,String keyword,String department,String project){
        Long leadingId=0L;
        Long creatorId=0L;
        if(type == 1){//指派给我的
            leadingId = userId;
        }else if(type == 2){// 由我创建的
            creatorId = userId;
        }else if(type == 3){//抄送给我的
            leadingId=-1L;
            creatorId=-1L;

        }

        Department department1 = departmentService.get(Long.parseLong(department));
        if(department1==null){
            return mapper.getCount(leadingId,creatorId,keyword,null,project,userId);
        }
        return mapper.getCount(leadingId,creatorId,keyword,department1.getCode(),project,userId);

    }

    public void delete(Long id){
        mapper.delete(id);
    }

    public void update(BugLog bugLog){
        Bug bug = new Bug();
        bug.setModifierId(bugLog.getCreatorId());
        bug.setModifierName(bugLog.getModifierName());
        bug.setId(bugLog.getId());
        bug.setStatus(bugLog.getStatus());
        mapper.update(bug);
        bugLog.setBugId(bugLog.getId());
        if(bug.getStatus()==Bug.STATUS_FINISH){
            Bug bug1 = get(bug.getId());
            mailUtil.sendMailToCreateByBugFinish(bug1);
        }
        bugLogMapper.save(bugLog);
    }

    public void updateBase(Bug bug){
        BugLog bugLog = new BugLog();
        bugLog.setCreatorId(bug.getCreatorId());
        bugLog.setCreatorName(bug.getModifierName());
        bugLog.setBugId(bug.getId());
        bugLog.setStatus(Bug.STATUS_EDIT);
        bugLogMapper.save(bugLog);
        mapper.updateBase(bug);
    }

   public int getCountAllFinish(Long leadingId,Long creatorId){
      return mapper.getCountAllFinish(leadingId,creatorId,Bug.STATUS_FINISH);
   }

   public int getCountAllTimeOut(Long leadingId,Long creatorId){
      return mapper.getCountAllTimeOut(leadingId,creatorId,Bug.STATUS_TIMEOUT);
   }

   public int getCountAllDoing(Long leadingId,Long creatorId){
        return mapper.getCountAllDoing(leadingId,creatorId,Bug.STATUS_FINISH);

   }

    public int getCountProjectFinish(String project){
        return mapper.getCountByProjectAndStatus(project,Bug.STATUS_FINISH);
    }

    public int getCountProjectTimeOut(String project){
        return mapper.getCountByProjectAndStatus(project,Bug.STATUS_TIMEOUT);
    }

    public int getCountProjectDoing(String project){
        return mapper.getCountByProjectAndEtStatus(project,Bug.STATUS_FINISH);

    }

    public List<Bug> findByDepartment( String departmentCode){
        return  mapper.findByDepartment(departmentCode);
    }

    public int getCountByDepartment(String departmentCode){

        return mapper.getCountByDepartment(departmentCode);

    }

    public List<Bug> findByDepartmentId( String departmentId){
        if(departmentId.equals("0")){
            return mapper.findByDepartment(null);
        }

        Department department = departmentService.get(Long.parseLong(departmentId));

        if(department==null){
            return new ArrayList<>();
        }

        return  mapper.findByDepartment(department.getCode());
    }

    public static class StatisticsBean{
        public StatisticsBean(String name,int code,int count){
            this.name = name;
            this.code = code;
            this.count = count;
        }
       public  String name;
       public int code;
       public int count;
    }

    public int getCountDepartmentFinish(String department){
        if(department.equals("0")){
            return mapper.getCountByDepartmentAndStatus(null,Bug.STATUS_FINISH);
        }
        Department department1 = departmentService.get(Long.parseLong(department));
        if(department1 == null){
            return mapper.getCountByDepartmentAndStatus(null,Bug.STATUS_FINISH);
        }
        return mapper.getCountByDepartmentAndStatus(department1.getCode(),Bug.STATUS_FINISH);

    }

    public int getCountDepartmentTimeOut(String department){
        if(department.equals("0")){
            return mapper.getCountByDepartmentAndStatus(null,Bug.STATUS_TIMEOUT);
        }
        Department department1 = departmentService.get(Long.parseLong(department));
        if(department1 == null){
            return mapper.getCountByDepartmentAndStatus(null,Bug.STATUS_TIMEOUT);
        }
        return mapper.getCountByDepartmentAndStatus(department1.getCode(),Bug.STATUS_TIMEOUT);
    }

    public int getCountDepartmentDoing(String department){
        if(department.equals("0")){
            return mapper.getCountByDepartmentAndEtStatus(null,Bug.STATUS_FINISH);
        }
        Department department1 = departmentService.get(Long.parseLong(department));
        if(department1 == null){
            return mapper.getCountByDepartmentAndEtStatus(null,Bug.STATUS_FINISH);
        }
        return mapper.getCountByDepartmentAndEtStatus(department1.getCode(),Bug.STATUS_FINISH);

    }

    public Map<String,Object> getStatistics(){
        List<Statistics> statusList = new ArrayList<>();
        for(int i=0;i<5;i++){
            Statistics temp = new Statistics();
           temp.setCount(mapper.getCountByStatus(i));
            temp.setName(getNameByStatus(i));
            statusList.add(temp);
        }

        List<Statistics> severityList = new ArrayList<>();
        for(int i=1;i<6;i++){
            Statistics temp = new Statistics();
            temp.setCount(mapper.getCountBySeverity(i+""));
            temp.setName(getNameBySeverity(i));
            severityList.add(temp);
        }

        List<Statistics> priorityList = new ArrayList<>();
        for(int i=1;i<5;i++){
            Statistics temp = new Statistics();
            temp.setCount(mapper.getCountByPriority(i+""));
            temp.setName(getNameByPriority(i));
            priorityList.add(temp);
        }

        List<Statistics> projectList = new ArrayList<>();
        List<Project> projects = projectService.find(1,1000000);
        if(projects.size()>0){
            projects.forEach(project -> {
                Statistics temp = new Statistics();
                temp.setName(project.getName());
                temp.setCount(mapper.getCountByProject(project.getName()));
                projectList.add(temp);
            });
        }

        Map<String,Object> map = new HashMap();
        map.put(1+"",statusList);
        map.put(2+"",severityList);
        map.put(3+"",priorityList);
        map.put(4+"",projectList);

        return map;
    }

    public String getNameByStatus(int status){
        if(status==0){
            return "未确认";
        }else if(status==1){
            return "已确认";
        }else if(status==2){
            return "处理中";
        }else if(status==3){
            return "已解决";
        }else if(status==4){
            return "已通过";
        }
        return "其他";
    }

    public String getNameBySeverity(int severity){
        if(severity==1){
            return "功能改进";
        }else if(severity==2){
            return "普通问题";
        }else if(severity==3){
            return "功能缺失";
        }else if(severity==4){
            return "严重崩溃";
        }else if(severity==5){
            return "阻碍进度";
        }
        return "其他";
    }

    public String getNameByPriority(int priority){
        if(priority==1){
            return "低";
        }else if(priority==2){
            return "一般";
        }else if(priority==3){
            return "高";
        }else if(priority==4){
            return "紧急";
        }
        return "其他";
    }

}