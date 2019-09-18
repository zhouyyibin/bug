package seed.tools.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import seed.entity.Bug;
import seed.entity.SystemClassification;
import seed.entity.User;
import seed.service.BugService;
import seed.service.SystemClassificationService;
import seed.service.UserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 发邮件工具类
 */
@Component
public class MailUtil {

    @Autowired
    private  IMailServiceImpl mailService;//注入发送邮件的各种实现方法

    @Value("${spring.mail.host}")
    String host;//smtp服务器
    @Value("${spring.mail.from}")
    String username;//smtp账号
    @Value("${spring.mail.password}")
    String password;//stmp密码
    @Value("${spring.mail.properties.mail.smtp.port}")
    String port;//smtp端口号
    @Autowired
    private UserService userService;

    @Autowired
    private BugService bugService;


    @Autowired
    private SystemClassificationService systemClassificationService;

    private List<String> stringToList(String strs){
        String str[] = strs.split(",");
        return Arrays.asList(str);
    }



    /**
     * bug提交时，给负责人及抄送人员发送提BUG邮件提醒
     */
    public  void sendMailToLeadingAndLParent(Bug bug){
       if("0".equals(getEmailConfig().getIsOpen())){
           return;
       }

        User leading= userService.get(bug.getLeadingId());
        if(leading==null){
            return;
        }

        List<String> sendMails = userService.stringIdsToListUser(bug.getSendMails()).stream().map(i->i.getAccount()).collect(Collectors.toList());
        try {
            mailService.sendSimpleMail(
                    leading.getAccount(),
                    bug.getTitle(),//“项目”"影响版本"“优先级”“负责人
                    bug.getCreatorName()
                            +"提了一个BUG《"+bug.getTitle()+"》，"
                            +"这个BUG所属项目是《"+bug.getProject()+"》，"
                            +"所属版本是《"+bug.getEdition()+"》，"
                            +"所属模块是《"+bug.getModel()+"》，"
                            +"负责人是《"+bug.getLeading().getName()+"》,"
                            +"请登陆BUG跟踪系统查看",
                    sendMails.stream().toArray(String[]::new));
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    /**
     * bug超时时，给当事人及当事人上级提发送提BUG邮件提醒
     */
    public  void sendMailToLeadingAndLParentTimeOut(Long bugId){
        if("0".equals(getEmailConfig().getIsOpen())){
            return;
        }

        Bug bug = bugService.get(bugId);
        if(bug == null||bug.getStatus()>=Bug.STATUS_FINISH){
            return;
        }

        User leading= userService.get(bug.getLeadingId());
        if(leading==null){
            return;
        }
        User lParent = userService.get(leading.getId());

        if(lParent !=null){
            try {
                mailService.sendSimpleMail(
                        leading.getAccount(),
                        bug.getTitle(),
                        bug.getCreatorName()
                                +"提了一个BUG《"+bug.getTitle()+"》，"
                                +"这个BUG所属项目是《"+bug.getProject()+"》，"
                                +"所属版本是《"+bug.getEdition()+"》，"
                                +"所属模块是《"+bug.getModel()+"》，"
                                +"负责人是《"+bug.getLeading().getName()+"》,"
                        +"已超过要求解决时间，请登陆系统查看");
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }

    }

    /**
     * bug被确认时，给BUG创建者发送提BUG邮件提醒
     */
    public  void sendMailToCreateByBugConfirm(Bug bug){
        if("0".equals(getEmailConfig().getIsOpen())){
            return;
        }

        if(bug == null){
            return;
        }

        User creator= userService.get(bug.getCreatorId());
        if(creator==null){
            return;
        }

        try {
            mailService.sendSimpleMail(creator.getAccount(),bug.getTitle(),"您提的一个BUG《"+bug.getTitle()+"》，"
                    +"这个BUG所属项目是《"+bug.getProject()+"》，"
                    +"所属版本是《"+bug.getEdition()+"》，"
                    +"所属模块是《"+bug.getModel()+"》，"
                    +"负责人是《"+bug.getLeading().getName()+"》,"
                    +"已被确认，请登陆系统查看");
        }catch (Exception ex){
            ex.printStackTrace();
        }

        User lParent = userService.get(creator.getParentId());

        if(lParent !=null){
            try {
                mailService.sendSimpleMail(creator.getAccount(),bug.getTitle(),bug.getCreatorName()+"提了一个BUG《"+bug.getTitle()+"》，"
                        +"这个BUG所属项目是《"+bug.getProject()+"》，"
                        +"所属版本是《"+bug.getEdition()+"》，"
                        +"所属模块是《"+bug.getModel()+"》，"
                        +"负责人是《"+bug.getLeading().getName()+"》,"
                        +"已被确认，请登陆系统查看");
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

    /**
     * bug被完成时，给BUG创建者发送提BUG邮件提醒
     */
    public  void sendMailToCreateByBugFinish(Bug bug){
        if("0".equals(getEmailConfig().getIsOpen())){
            return;
        }

        if(bug == null){
            return;
        }

        User creator= userService.get(bug.getCreatorId());
        if(creator==null){
            return;
        }

        try {
            mailService.sendSimpleMail(creator.getAccount(),bug.getTitle(),"您提的一个BUG《"+bug.getTitle()+"》，"
                    +"这个BUG所属项目是《"+bug.getProject()+"》，"
                    +"所属版本是《"+bug.getEdition()+"》，"
                    +"所属模块是《"+bug.getModel()+"》，"
                    +"负责人是《"+bug.getLeading().getName()+"》,"
                    +"已被完成，请登陆系统查看");
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    public void createUser(User user){
        try {
            mailService.sendSimpleMail(
                    user.getAccount(),
                    "账号创建成功",
                    "BUG系统已经成功为您创建了一个账号，账号名为："+user.getAccount(),"密码为："+user.getPassword()+"登陆系统后，请记得修改初始密码");
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public void resetPassword(User user){
        try {
            mailService.sendSimpleMail(
                    user.getAccount(),
                    "BUG系统密码重置成功",
                    "您的密码重置成功","初始密码为："+user.getPassword()+"登陆系统后，请记得修改初始密码");
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public EmailConfig getEmailConfig(){
        EmailConfig emailConfig = new EmailConfig();
        SystemClassification systemClassification = systemClassificationService.getByNameAndType("name",4+"");
        if(systemClassification==null){
            SystemClassification systemClassification1 = new SystemClassification();
            systemClassification1.setContent("艾鑫Bug系统");
            systemClassification1.setType("4");
            systemClassification1.setName("name");
            systemClassificationService.save(systemClassification1);
            emailConfig.setName(systemClassification1.getContent());
        }else{
            emailConfig.setName(systemClassification.getContent());
        }


        SystemClassification systemClassificationIsOpen = systemClassificationService.getByNameAndType("isOpen",4+"");
        if(systemClassificationIsOpen==null){
            SystemClassification systemClassification1 = new SystemClassification();
            systemClassification1.setContent("1");
            systemClassification1.setType("4");
            systemClassification1.setName("isOpen");
            systemClassificationService.save(systemClassification1);
            emailConfig.setIsOpen(Integer.parseInt(systemClassification1.getContent()));
        }else{
            emailConfig.setIsOpen(Integer.parseInt(systemClassificationIsOpen.getContent()));
        }

        emailConfig.setHost(host);
        emailConfig.setPort(port);
        emailConfig.setPassword(password);
        emailConfig.setUsername(username);

        return emailConfig;
    }

    public void setEmailConfig(String name,int isOpen){
        SystemClassification systemClassification = systemClassificationService.getByNameAndType("name",4+"");
        if(systemClassification!=null){
            systemClassification.setContent(name);
            systemClassificationService.updateContent(systemClassification);

        }else {
            SystemClassification systemClassification1 = new SystemClassification();
            systemClassification1.setContent(name);
            systemClassification1.setType("4");
            systemClassification1.setName("name");
            systemClassificationService.save(systemClassification1);
        }


        SystemClassification systemClassificationIsOpen = systemClassificationService.getByNameAndType("isOpen",4+"");
        if(systemClassificationIsOpen!=null){
            systemClassificationIsOpen.setContent(isOpen+"");
            systemClassificationService.updateContent(systemClassificationIsOpen);

        }else{
            SystemClassification systemClassification1 = new SystemClassification();
            systemClassification1.setContent(isOpen+"");
            systemClassification1.setType("4");
            systemClassification1.setName("isOpen");
            systemClassificationService.save(systemClassification1);
        }


    }
}
