package seed.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import seed.entity.BugLog;
import seed.service.BugLogService;
import seed.tools.restful.RestfulAntPageResult;
import seed.tools.jwt.CheckToken;
import java.util.List;

@RequestMapping("/bug/log")
@RestController
@CrossOrigin
public class BugLogController {
    @Autowired
    private BugLogService service;


    @CheckToken
    @RequestMapping(value = "/list/{bug_id}", method = RequestMethod.GET)
    public RestfulAntPageResult list(@PathVariable("bug_id") Long bugId)  {
        List<BugLog> list = service.find(bugId);
        int count = service.getCount(bugId);
        return new RestfulAntPageResult<>(1,25,count, list);
    }

}
