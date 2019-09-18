package seed.tools.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import seed.entity.SystemClassification;
import seed.service.SystemClassificationService;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Component
public class IMailServiceImpl implements IMailService {
    private JavaMailSender mailSender;
   // JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
    @Autowired
    MailUtil mailUtil;

    @Autowired
    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    @Value("${spring.mail.from}")
    private String from;

    /**
     * 发送文本邮件
     * @param to
     * @param subject
     * @param content
     */
    @Override
    public void sendSimpleMail(String to, String subject, String content) {

        SimpleMailMessage message = new SimpleMailMessage();
        try {
            InternetAddress ia = new InternetAddress(from, mailUtil.getEmailConfig().getName(), "UTF-8");
            message.setFrom(ia.toString());
            message.setTo(to);
            message.setCc();
            message.setSubject(subject);
            message.setText(content);
//            mailSender.setHost("smtp.163.com");
//            mailSender.setUsername("15820587404@163.com");
//            mailSender.setPort(994);
//            mailSender.setDefaultEncoding("utf-8");
//            mailSender.setPassword("135790aq");
//            mailSender.setProtocol("smtp");
            mailSender.send(message);

        } catch (UnsupportedEncodingException e) {
            message.setFrom("");
        }

    }

    @Override
    public void sendSimpleMail(String to, String subject, String content, String... cc) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            InternetAddress ia = new InternetAddress(from, mailUtil.getEmailConfig().getName(), "UTF-8");
            message.setFrom(ia.toString());
            message.setTo(to);
            message.setCc(cc);
            message.setSubject(subject);
            message.setText(content);
//            mailSender.setHost("smtp.163.com");
//            mailSender.setUsername("15820587404@163.com");
//            mailSender.setPort(994);
//            mailSender.setDefaultEncoding("utf-8");
//            mailSender.setPassword("135790aq");
//            mailSender.setProtocol("smtp");
            mailSender.send(message);
        }catch (UnsupportedEncodingException e){

        }

    }

    /**
     * 发送HTML邮件
     * @param to
     * @param subject
     * @param content
     */
    @Override
    public void sendHtmlMail(String to, String subject, String content) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true);
//        mailSender.setHost("smtp.163.com");
//        mailSender.setUsername("15820587404@163.com");
//        mailSender.setPort(994);
//        mailSender.setDefaultEncoding("utf-8");
//        mailSender.setPassword("135790aq");
//        mailSender.setProtocol("smtp");
        mailSender.send(message);
    }

    /**
     * 发送带附件的邮件
     * @param to
     * @param subject
     * @param content
     * @param filePath
     */
    public void sendAttachmentsMail(String to, String subject, String content, String filePath) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true);

        FileSystemResource file = new FileSystemResource(new File(filePath));
        String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
        helper.addAttachment(fileName, file);
//        mailSender.setHost("smtp.163.com");
//        mailSender.setUsername("15820587404@163.com");
//        mailSender.setPort(994);
//        mailSender.setDefaultEncoding("utf-8");
//        mailSender.setPassword("135790aq");
//        mailSender.setProtocol("smtp");
        mailSender.send(message);
    }

    /**
     * 发送正文中有静态资源的邮件
     * @param to
     * @param subject
     * @param content
     * @param rscPath
     * @param rscId
     */
    public void sendResourceMail(String to, String subject, String content, String rscPath, String rscId) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content, true);

        FileSystemResource res = new FileSystemResource(new File(rscPath));
        helper.addInline(rscId, res);
//        mailSender.setHost("smtp.163.com");
//        mailSender.setUsername("15820587404@163.com");
//        mailSender.setPort(994);
//        mailSender.setDefaultEncoding("utf-8");
//        mailSender.setPassword("135790aq");
//        mailSender.setProtocol("smtp");
        mailSender.send(message);
    }
}
