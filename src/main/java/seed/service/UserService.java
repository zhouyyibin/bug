package seed.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seed.controller.body.ModelBody;
import seed.entity.Department;
import seed.entity.Project;
import seed.entity.Role;
import seed.entity.User;
import seed.mapper.DepartmentMapper;
import seed.mapper.ProjectMapper;
import seed.mapper.UserMapper;
import seed.tools.Base64Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    DepartmentMapper departmentMapper;

    @Autowired
    ProjectService projectService;

    @Autowired
    ProjectMapper projectMapper;

    @Autowired
    RoleService roleService;

    public User get(Long userId){
        User user = userMapper.get(userId);
        if(user ==null){
            return null;
        }
        List<String> roleIds =user.getRoleBeans();
        if(roleIds!=null&&roleIds.size()>0){
            List<Role> roles = new ArrayList<>();
            roleIds.forEach(roleId->{
                Role role=roleService.get(Long.parseLong(roleId));
                roles.add(role);
            });
            user.setRoles(roles);
        }
        
        return user;
    }

    public User getByAccount(String name){
       return userMapper.getByAccount(name);
    }

    public User getByAccountAndPassword(String name,String password){
        return userMapper.getByAccountAndPassword(name, password);
    }

    public Long save(User data){
        data.setPassword(Base64Utils.md5Password(data.getAccount()+"abc!"));
        Department department = departmentMapper.get(data.getDepartmentId());
        if(department==null){
            return userMapper.save(data);
        }
        data.setDepartmentCode(department.getCode());
        return userMapper.save(data);

    }

    public List<User> find(int page, int size,Long departmentId,String keyword,int status){

        return userMapper.find((page-1)*size,size,departmentId,keyword,status);

    }

    public int getCount(Long departmentId,String keyword,int status){
        return userMapper.getCount(departmentId,keyword,status);
    }

    public void delete( Long id){
        userMapper.delete(id);
    }

    public void update(User data){
        User user = userMapper.get(data.getDepartmentId());
        if(user!=null){
            data.setDepartmentCode(data.getDepartmentCode());
        }
        userMapper.update(data);
    }

    public void updateLogin(Long id){
        userMapper.updateLogin(id,new Date());

    }

    public void updateRoleId(Long id, String roleId){
        userMapper.updateRoleId(id,roleId);
    }

    public List<User> stringIdsToListUser(String stringIds){
        List<User> sendMailUsers = new ArrayList<>();
        if(stringIds!=null&&!stringIds.isEmpty()){
            List<String> lists = Arrays.asList(stringIds.split(","));
            lists.forEach(list->{
                User user =  get(Long.parseLong(list));
                if(user!=null){
                    sendMailUsers.add(user);
                }
            });
        }
        return sendMailUsers;
    }

    public void updatePassword(Long id,  String password){
        userMapper.updatePassword(id,password);
    }

    public void updateStatus(Long id,int status){
        if(status==0){//如果将用户暂停，将用户从项目基础数据中移除
            List<Project> projects = projectService.find(1,-1);
            if(projects.size()!=0){
                projects.forEach(project -> {
                    JSONArray modelsArray=JSONArray.parseArray(project.getModels());
                    if(modelsArray.size()>0){
                        JSONArray modelBodies = new JSONArray();
                        modelsArray.forEach(item->{
                           ModelBody body = JSONObject.parseObject(item.toString(),ModelBody.class);
                            if(body.getLeadingId().equals(id+"")){
                                ModelBody modelBody = new ModelBody();
                                modelBody.setLeadingName("");
                                modelBody.setLeadingAccount("");
                                modelBody.setLeadingName("");
                                modelBody.setName(body.getName());
                                modelBodies.add(modelBody);
                            }else{
                                modelBodies.add(body);
                            }
                        });
                        projectMapper.updateModels(project.getId(),JSONArray.toJSONString(modelBodies));
                    }

                    JSONArray sendersArray=JSONArray.parseArray(project.getSenders());
                    if(sendersArray.size()>0){
                        JSONArray usersArray = new JSONArray();
                        sendersArray.forEach(item->{
                            User body = JSONObject.parseObject(item.toString(),User.class);
                            if(!body.getId().toString().equals(id+"")){
                                usersArray.add(item);
                            }
                        });
                        projectMapper.updateSenders(project.getId(),JSONArray.toJSONString(usersArray));
                    }
                });
            }

        }
        userMapper.updateStatus(id,status);
    }

}
