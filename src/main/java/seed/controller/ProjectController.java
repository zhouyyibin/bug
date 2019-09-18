package seed.controller;


import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.annotation.JsonAlias;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import seed.controller.body.ModelBody;
import seed.entity.Bug;
import seed.entity.Project;
import seed.entity.User;
import seed.service.ProjectService;
import seed.service.UserService;
import seed.tools.jwt.CheckToken;
import seed.tools.jwt.JwtUtil;
import seed.tools.restful.RestfulAntPageResult;
import seed.tools.restful.RestfulResult;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static seed.Application.ROLE_NAME_PROJECT_ERROR;
import static seed.tools.restful.RestfulResult.failure;
import static seed.tools.restful.RestfulResult.success;

@RequestMapping("/project")
@RestController
@CrossOrigin
public class ProjectController {
    @Autowired
    private ProjectService service;

    @Autowired
    private UserService userService;



    @CheckToken
    @RequestMapping(value = "", method = RequestMethod.POST)
    public RestfulResult saveProject(@Valid @RequestBody Project body,@RequestHeader("Authorization") String token) {
        Project project = service.getByName(body.getName());
        if(project!=null){
            return failure(ROLE_NAME_PROJECT_ERROR);
        }
        User user = JwtUtil.getUserByTokent(token);
        body.setCreatorId(user.getId());
        body.setCreatorName(user.getName());
        body.setModifierId(user.getId());
        body = toProjectBase(body);
        service.save(body);
        return success(body);
    }

    @CheckToken
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public RestfulResult get(@PathVariable("id") long id) {
        return success(service.get(id));
    }

    @CheckToken
    @RequestMapping(value = "/list/{page}", method = RequestMethod.GET)
    public RestfulAntPageResult list(@PathVariable("page") int page,
                              @RequestParam(value = "size", required = false, defaultValue = "25") int size)  {
        List<Project> list = service.find(page,size);
        int count = service.getCount();
        return new RestfulAntPageResult<>(page,size,count, list);
    }


    @CheckToken
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public RestfulResult deleteProject(@PathVariable(value = "id") Long id) {
       service.delete(id);
        return RestfulResult.success();
    }

    @CheckToken
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public RestfulResult updateProject(@PathVariable(value = "id") Long id, @Valid @RequestBody Project body) {
        Project project = service.getByName(body.getName());
        if(project!=null
                &&!project.getId().equals(body.getId())
                &&project.getName().equals(body.getName())){
            return failure(ROLE_NAME_PROJECT_ERROR);
        }
        service.update(toProjectBase(body));
        return success(service.get(id));
    }


    Project toProjectBase(Project body){
        List<User> senders = new ArrayList<>();
        if(body.getSenderBeans()!=null && body.getSenderBeans().size()>0){
            body.getSenderBeans().forEach(item->{
                User temp = userService.get(Long.parseLong(((Map)item).get("id").toString()));
                if(temp!=null){
                    senders.add(temp);
                }
            });
        }
        body.setSenders(JSONArray.toJSONString(senders));

        List<ModelBody> modelBeans = new ArrayList<>();
        if(body.getModelBeans() != null && body.getModelBeans().size()>0){
            body.getModelBeans().forEach(item->{
                Map bug = (Map)item;
                User temp = userService.get(Long.parseLong(bug.get("leadingId").toString()));
                if(temp!=null){
                    ModelBody modelBody = new ModelBody();
                    modelBody.setName(bug.get("name").toString());
                    modelBody.setLeadingId(temp.getId()+"");
                    modelBody.setLeadingAccount(temp.getAccount());
                    modelBody.setLeadingName(temp.getName());

                    List<ModelBody.Sender> senderList = new ArrayList<>();
                    try {
                        JSONArray sendersJSONArray =(JSONArray) bug.get("senders");
                        sendersJSONArray.forEach(value->{
                            Map userMap = (Map)value;
                           User user =userService.get(Long.parseLong(userMap.get("id").toString()));
                           if(user!=null){
                               ModelBody.Sender sender = new ModelBody.Sender();
                               sender.setAccount(user.getAccount());
                               sender.setId(user.getId());
                               sender.setName(user.getName());
                               senderList.add(sender);
                           }
                        });
                    }catch (Exception e){

                    }finally {
                        modelBody.setSenders(senderList);
                    }

                    modelBeans.add(modelBody);
                }
            });
        }
        body.setModels(JSONArray.toJSONString(modelBeans));


        if(body.getVersionBeans()!=null&&body.getVersionBeans().size()>0){
            body.setVersions(StringUtils.join(body.getVersionBeans(), ","));
        }

        return body;
    }

}
