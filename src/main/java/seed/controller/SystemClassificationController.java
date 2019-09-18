package seed.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import seed.entity.SystemClassification;
import seed.entity.User;
import seed.service.SystemClassificationService;
import seed.tools.jwt.CheckToken;
import seed.tools.jwt.JwtUtil;
import seed.tools.restful.RestfulAntPageResult;
import seed.tools.restful.RestfulResult;

import javax.validation.Valid;
import java.util.List;

import static seed.Application.SYSTEMCLASSIFICATION_NAME_ALREADY_ERROR;
import static seed.tools.restful.RestfulResult.failure;
import static seed.tools.restful.RestfulResult.success;

@RequestMapping("/system/classification")
@RestController
@CrossOrigin
public class SystemClassificationController {
    @Autowired
    private SystemClassificationService service;

    @CheckToken
    @RequestMapping(value = "", method = RequestMethod.POST)
    public RestfulResult saveSystemClassification(@Valid @RequestBody SystemClassification body,@RequestHeader("Authorization") String token) {
        SystemClassification data = service.getByNameAndType(body.getName(),body.getType());
        if(data!=null){
            return failure(SYSTEMCLASSIFICATION_NAME_ALREADY_ERROR);
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
    @RequestMapping(value = "/list/{type}", method = RequestMethod.GET)
    public RestfulAntPageResult list(@PathVariable("type") String type)  {
        List<SystemClassification> list = service.findByType(type);
        int count = service.getCountByType(type);
        return new RestfulAntPageResult<>(1,25,count, list);
    }


    @CheckToken
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public RestfulResult deleteSystemClassification(@PathVariable(value = "id") Long id) {
       service.delete(id);
        return RestfulResult.success();
    }

}
