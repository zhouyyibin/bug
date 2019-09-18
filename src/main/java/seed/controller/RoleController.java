package seed.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import seed.entity.Role;
import seed.entity.User;
import seed.service.RoleService;
import seed.tools.jwt.JwtUtil;
import seed.tools.restful.RestfulAntPageResult;
import seed.tools.restful.RestfulResult;
import seed.tools.jwt.CheckToken;

import javax.validation.Valid;
import java.util.List;

import static seed.Application.ROLE_NAME_ALREADY_ERROR;
import static seed.tools.restful.RestfulResult.failure;
import static seed.tools.restful.RestfulResult.success;

@RequestMapping("/role")
@RestController
@CrossOrigin
public class RoleController {
    @Autowired
    private RoleService service;

    @CheckToken
    @RequestMapping(value = "", method = RequestMethod.POST)
    public RestfulResult saveRole(@Valid @RequestBody Role body,@RequestHeader("Authorization") String token) {
        Role role = service.getByName(body.getName());
        if(role!=null){
            return failure(ROLE_NAME_ALREADY_ERROR);
        }
        User user = JwtUtil.getUserByTokent(token);
        body.setCreatorId(user.getId());
        body.setCreatorName(user.getName());
        body.setModifierId(user.getId());
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
        List<Role> list = service.find(page,size);
        int count = service.getCount();
        return new RestfulAntPageResult<>(page,size,count, list);
    }


    @CheckToken
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public RestfulResult deleteRole(@PathVariable(value = "id") Long id) {
       service.delete(id);
        return RestfulResult.success();
    }

    @CheckToken
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public RestfulResult updateRole(@PathVariable(value = "id") Long id, @Valid @RequestBody Role body) {
        Role role = service.getByName(body.getName());
        if(role!=null&&!role.getId().equals(id)){
            return failure(ROLE_NAME_ALREADY_ERROR);
        }
        service.update(body.getId(),body.getName(),body.getDescribe());
        return success(service.get(id));
    }

    @CheckToken
    @RequestMapping(value = "/{id}/permissions", method = RequestMethod.PUT)
    public RestfulResult updatePermissionRole(@PathVariable(value = "id") Long id, @Valid @RequestBody Role body) {
        service.updatePermission(body.getId(),body.getPermissions().toJSONString());
        return success(service.get(id));
    }
}
