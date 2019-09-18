package seed.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import seed.entity.User;
import seed.service.UserService;
import seed.tools.Base64Utils;
import seed.tools.email.MailUtil;
import seed.tools.jwt.JwtUtil;
import seed.tools.restful.RestfulAntPageResult;
import seed.tools.restful.RestfulResult;
import seed.tools.jwt.CheckToken;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static seed.Application.ACCOUNT_ALREADY_ERROR;
import static seed.Application.PASSWORD_ERROR;
import static seed.tools.restful.RestfulResult.failure;
import static seed.tools.restful.RestfulResult.success;

@RequestMapping("/user")
@RestController
@CrossOrigin
public class UserController {
    @Autowired
    private UserService service;

    @Autowired
    private MailUtil mailUtil;

    @CheckToken
    @RequestMapping(value = "", method = RequestMethod.POST)
    public RestfulResult saveUser(@Valid @RequestBody User body,@RequestHeader("Authorization") String token) {
        User temp = service.getByAccount(body.getAccount());
        if(temp!=null){
            return failure(ACCOUNT_ALREADY_ERROR);
        }
        User user = JwtUtil.getUserByTokent(token);
        body.setCreatorId(user.getId());
        body.setCreatorName(user.getName());
        body.setModifierId(user.getId());
        service.save(body);
        mailUtil.createUser(body);
        return success(body);
    }

    @CheckToken
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public RestfulResult getUser(@PathVariable("id") long id) {
        return success(service.get(id));
    }

    @CheckToken
    @RequestMapping(value = "/list/{page}", method = RequestMethod.GET)
    public RestfulAntPageResult list(@PathVariable("page") int page,
                              @RequestParam(value = "department_id", required = false, defaultValue = "0") Long departmentId,
                                     @RequestParam(value = "states", required = false, defaultValue = "1") int status,
                              @RequestParam(value = "size", required = false, defaultValue = "25") int size,
                                     @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword)  {
        List<User> list = service.find(page,size,departmentId,keyword,status);
        int count = service.getCount(departmentId,keyword,status);
        if(size<0 && list.size()>0){
            List<Map> allList = new ArrayList<>();
                list.forEach(item->{
                    Map<String,Object> map = new HashMap();
                    map.put("id",item.getId()+"");
                    map.put("name",item.getName());
                    map.put("status",item.getStatus());
                    allList.add(map);
                });
            return new RestfulAntPageResult<>(page,size,count, allList);
        }
        return new RestfulAntPageResult<>(page,size,count, list);
    }

    @CheckToken
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public RestfulResult deleteUser(@PathVariable(value = "id") Long id) {
        User user =service.get(id);
        if(user == null){
            return failure();
        }
        service.updateStatus(id,user.getStatus()==1?0:1);
        return success(service.get(id));
    }

    @CheckToken
    @RequestMapping(value = "/del/{id}", method = RequestMethod.DELETE)
    public RestfulResult del(@PathVariable(value = "id") Long id,@RequestHeader("Authorization") String token) {
        User user =service.get(id);
        if(user == null){
            return failure();
        }
        User my = JwtUtil.getUserByTokent(token);

        if(my.getId().equals(user.getId())){//自己不能删除自己
            return failure();
        }
        service.delete(id);
        return success(service.get(id));
    }

    @CheckToken
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public RestfulResult updateUser(@PathVariable(value = "id") Long id, @Valid @RequestBody User body) {
        User user = service.getByAccount(body.getAccount());
        if(user!=null&&!user.getId().equals(body.getId())){
            return failure(ACCOUNT_ALREADY_ERROR);
        }
        if(body.getRoleBeans()!=null&&body.getRoleBeans().size()>0){
            body.setRoleId(StringUtils.join(body.getRoleBeans(), ","));
        }
        service.update(body);
        return success(service.get(id));
    }

    @CheckToken
    @RequestMapping(value = "/{id}/role", method = RequestMethod.PUT)
    public RestfulResult updateRoleIdUser(@PathVariable(value = "id") Long id, @Valid @RequestBody User body) {
        if(body.getRoleBeans()!=null&&body.getRoleBeans().size()>0){
            body.setRoleId(StringUtils.join(body.getRoleBeans(), ","));
        }
        service.updateRoleId(body.getId(),body.getRoleId());
        return success(service.get(id));
    }

    @CheckToken
    @RequestMapping(value = "/{id}/reset", method = RequestMethod.PUT)
    public RestfulResult reset(@PathVariable(value = "id") Long id, @Valid @RequestBody User body) {
        User user = service.get(id);
        service.updatePassword(body.getId(),Base64Utils.md5Password(user.getAccount()+"abc!"));
        mailUtil.resetPassword(user);
        return success(user);
    }

    @CheckToken
    @RequestMapping(value = "/change/password", method = RequestMethod.PUT)
    public RestfulResult changePassword( @Valid @RequestBody User body,@RequestHeader("Authorization") String token) {
        User temp = JwtUtil.getUserByTokent(token);
        User user =service.get(temp.getId());
//        if(body.getPassword()!=null&&!body.getPassword().matches("^(?=.*[0-9].*)(?=.*[A-Z].*)(?=.*[a-z].*).{6,30}$")){
//            return failure(PASSWORD_ERROR);
//        }
        if(!user.getPassword().equals(Base64Utils.md5Password(body.getOldPassword()))){
            return failure(PASSWORD_ERROR);
        }
        service.updatePassword(user.getId(),Base64Utils.md5Password(body.getPassword()));
        return success(user);
    }

}
