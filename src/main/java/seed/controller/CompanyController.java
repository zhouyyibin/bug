package seed.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import seed.entity.Company;
import seed.entity.User;
import seed.service.CompanyService;
import seed.tools.jwt.JwtUtil;
import seed.tools.restful.RestfulAntPageResult;
import seed.tools.restful.RestfulResult;
import seed.tools.jwt.CheckToken;

import javax.validation.Valid;
import java.util.List;

import static seed.Application.COMPANY_NAME_ALREADY_ERROR;
import static seed.tools.restful.RestfulResult.failure;
import static seed.tools.restful.RestfulResult.success;

@RequestMapping("/company")
@RestController
@CrossOrigin
public class CompanyController {
    @Autowired
    private CompanyService service;

    @CheckToken
    @RequestMapping(value = "", method = RequestMethod.POST)
    public RestfulResult saveCompany(@Valid @RequestBody Company body,@RequestHeader("Authorization") String token) {
        Company company = service.getByName(body.getName());
        if(company!=null){
            return failure(COMPANY_NAME_ALREADY_ERROR);
        }
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
    public RestfulResult getCompany(@PathVariable("id") long id) {
        return success(service.get(id));
    }

    @CheckToken
    @RequestMapping(value = "/list/{page}", method = RequestMethod.GET)
    public RestfulAntPageResult list(@PathVariable("page") int page,
                              @RequestParam(value = "size", required = false, defaultValue = "25") int size)  {
        List<Company> list = service.find(page,size);
        int count = service.getCount();
        return new RestfulAntPageResult<>(page,size,count, list);
    }

    @CheckToken
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public RestfulResult deleteCompany(@PathVariable(value = "id") Long id) {
       service.delete(id);
        return RestfulResult.success();
    }

    @CheckToken
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public RestfulResult updateCompany(@PathVariable(value = "id") Long id, @Valid @RequestBody Company body) {
        Company company = service.getByName(body.getName());
        if(company!=null&&!company.getId().equals(body.getId())){
            return failure(COMPANY_NAME_ALREADY_ERROR);
        }
        service.update(body);
        return success(service.get(id));
    }
}
