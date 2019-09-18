package seed.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import seed.tools.email.EmailConfig;
import seed.tools.email.MailUtil;
import seed.tools.restful.RestfulResult;

import static seed.tools.restful.RestfulResult.success;

@RequestMapping("/system")
@RestController
@CrossOrigin
public class SystemController {
    EmailConfig emailConfig = new EmailConfig();

    @Autowired
    MailUtil mailUtil;

    @RequestMapping(value = "/mails", method = RequestMethod.GET)
    public RestfulResult mails(){
        return success(mailUtil.getEmailConfig());
    }

    @RequestMapping(value = "/mails", method = RequestMethod.PUT)
    public RestfulResult mailsEdit(@RequestBody EmailConfig body){
       mailUtil.setEmailConfig(body.getName(),body.getIsOpen());
        return success(mailUtil.getEmailConfig());
    }
}
