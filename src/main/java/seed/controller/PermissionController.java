package seed.controller;


import com.alibaba.fastjson.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import seed.entity.Permission;
import seed.entity.User;
import seed.service.PermissionService;
import seed.tools.jwt.JwtUtil;
import seed.tools.restful.RestfulAntPageResult;
import seed.tools.restful.RestfulResult;
import seed.tools.jwt.CheckToken;

import javax.validation.Valid;
import java.util.List;
import static seed.tools.restful.RestfulResult.success;

@RequestMapping("/permission")
@RestController
@CrossOrigin
public class PermissionController {
    @Autowired
    private PermissionService service;

    @CheckToken
    @RequestMapping(value = "", method = RequestMethod.POST)
    public RestfulResult savePermission(@Valid @RequestBody Permission body,@RequestHeader("Authorization") String token) {
        String actions = body.getActionEntitySet().toJSONString();
        body.setActionEntity(actions);
        User user = JwtUtil.getUserByTokent(token);
        body.setCreatorId(user.getId());
        body.setCreatorName(user.getName());
        body.setModifierId(user.getId());
        body.setModifierName(user.getName());
        service.save(body);
        return success(body);
    }

    @CheckToken
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public RestfulResult get(@PathVariable("id") long id) {
        return success(service.get(id));
    }

    @CheckToken
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public RestfulAntPageResult list()  {
        List<Permission> list = service.find();
        int count = service.getCount();
        return new RestfulAntPageResult<>(1,count,count, list);
    }


    @CheckToken
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public RestfulResult deletePermission(@PathVariable(value = "id") Long id) {
       service.delete(id);
        return RestfulResult.success();
    }

    @CheckToken
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public RestfulResult updatePermission(@PathVariable(value = "id") Long id, @Valid @RequestBody Permission body) {
        body.setActionEntity(JSONArray.toJSONString(body.getActionEntitySet()));
        service.update(body);
        return success(service.get(id));
    }
}
