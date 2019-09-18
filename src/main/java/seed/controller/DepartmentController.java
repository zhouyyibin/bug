package seed.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import seed.entity.Department;
import seed.entity.User;
import seed.service.DepartmentService;
import seed.tools.jwt.JwtUtil;
import seed.tools.restful.RestfulAntPageResult;
import seed.tools.restful.RestfulResult;
import seed.tools.jwt.CheckToken;

import javax.validation.Valid;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static seed.Application.DEPARTMENT_NAME_ALREADY_ERROR;
import static seed.tools.restful.RestfulResult.failure;
import static seed.tools.restful.RestfulResult.success;

@RequestMapping("/department")
@RestController
@CrossOrigin
public class DepartmentController {
    @Autowired
    private DepartmentService service;

    @CheckToken
    @RequestMapping(value = "", method = RequestMethod.POST)
    public RestfulResult saveDepartment(@Valid @RequestBody Department body,@RequestHeader("Authorization") String token) {
        Department department = service.getByNameAndParentId(body.getName(),body.getParentId());
        if(department!=null){
            return failure(DEPARTMENT_NAME_ALREADY_ERROR);
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
    public RestfulResult getDepartment(@PathVariable("id") long id) {
        return success(service.get(id));
    }

    @CheckToken
    @RequestMapping(value = "/list/{page}", method = RequestMethod.GET)
    public RestfulAntPageResult list(@PathVariable("page") int page,
                              @RequestParam(value = "size", required = false, defaultValue = "25") int size)  {
        List<Department> list = service.find(page,size);
        int count = service.getCount();
        return new RestfulAntPageResult<>(page,size,count, list);
    }

    @CheckToken
    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    public RestfulAntPageResult tree()  {
        List<Department> parents = service.findByParentId(0L);
        List<Department> list = service.findTree(parents);
        int count = service.getCount();
        return new RestfulAntPageResult<>(1,count,count, list);
    }

    @CheckToken
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public RestfulResult deleteDepartment(@PathVariable(value = "id") Long id) {
       service.delete(id);
        return RestfulResult.success();
    }

    @CheckToken
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public RestfulResult updateDepartment(@PathVariable(value = "id") Long id, @Valid @RequestBody Department body) {
        Department department = service.getByNameAndParentId(body.getName(),body.getParentId());
        if(department!=null&&!department.getId().equals(body.getName())){
            return failure(DEPARTMENT_NAME_ALREADY_ERROR);
        }
        service.update(body.getId(),body.getName(),body.getUserId(),body.getParentId());
        return success(service.get(id));
    }

    @CheckToken
    @RequestMapping(value = "/check/list", method = RequestMethod.GET)
    public RestfulAntPageResult checkList(@RequestHeader("Authorization") String token)  {
        List<Department> resultList = new ArrayList<>();
        int count = 0;
        User user = JwtUtil.getUserByTokent(token);
        if(user==null){
            return new RestfulAntPageResult<>(1,10000,count, resultList);
        }
        if(user.getSetData()==1){
            Department department = new Department();
            department.setId(0L);
            department.setName("全部");
            department.setCode(0+"");
            resultList.add(department);
            List<Department> temps = service.find(1,10000);
            temps.forEach(temp->{
                Department p1=service.get(temp.getParentId());
                if(p1!=null){
                    temp.setName(p1.getName()+">"+temp.getName());
                    Department p2=service.get(p1.getParentId());
                    if(p2!=null){
                        temp.setName(p2.getName()+">"+temp.getName());
                    }
                }
                resultList.add(temp);
            });

            count = service.getCount();
        }else{
            Department department =service.get(user.getDepartmentId());
            if(department!=null){
                Department p1 = service.get(department.getParentId());
                if(p1!=null){
                    department.setName(p1+">"+department.getName());
                    Department p2 = service.get(p1.getParentId());
                    department.setName(p2+">"+department.getName());
                }
                resultList.add(department);
            }
            count = 1;
        }

        Collections.sort(resultList,new Comparator<Department>() {
            @Override
            public int compare(Department o1, Department o2) {
                if(o1.getCode().compareTo(o2.getCode())>0){
                    return 1;
                }else if(o1.getCode().compareTo(o2.getCode())<0){
                    return -1;
                }else{
                    return 0;
                }
            }
        });
        return new RestfulAntPageResult<>(1,10000,count, resultList);

    }
}
