package seed.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import seed.entity.SystemLog;
import seed.service.SystemLogService;
import seed.tools.jwt.CheckToken;
import seed.tools.restful.RestfulAntPageResult;

import java.util.List;

@RequestMapping("/system/log")
@RestController
@CrossOrigin
public class SystemLogController {
    @Autowired
    private SystemLogService service;


    @CheckToken
    @RequestMapping(value = "/list/{page}", method = RequestMethod.GET)
    public RestfulAntPageResult list(@PathVariable("page") int page,
                                     @RequestParam(value = "size", required = false, defaultValue = "25") int size)  {
        List<SystemLog> list = service.find(page,size);
        int count = service.getCount();
        return new RestfulAntPageResult<>(1,count,count, list);
    }

}
