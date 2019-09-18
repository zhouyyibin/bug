package seed.tools.aop;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import seed.entity.Bug;
import seed.service.BugService;
import seed.service.SystemLogService;
import seed.tools.email.MailUtil;
import seed.tools.restful.RestfulResult;

import java.util.concurrent.TimeUnit;


/**
 * aop切面类
 */
@Component
@Aspect
public class WebAspect {
    @Autowired
    MailUtil mailUtil;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private SystemLogService systemLogService;
    /**
     * 切入点
     * 匹配com.example.controller1包及其子包下的所有类的所有方法
     */
    @Pointcut("execution(* seed.controller..*.*(..))")
    public void executePackage(){
    }


    /**
     * 后置返回通知
     * 如果参数中的第一个参数为JoinPoint，则第二个参数为返回值的信息
     * 如果参数中的第一个参数不为JoinPoint，则第一个参数为returning中对应的参数
     * returning 只有目标方法返回值与通知方法相应参数类型时才能执行后置返回通知，否则不执行
     * @param joinPoint
     * @param restfulResult
     */
    @AfterReturning(value = "execution(* seed.controller..*.*(..))",returning = "restfulResult")
    public void afterReturningAdvice(JoinPoint joinPoint, RestfulResult restfulResult){
        Object[] objects=joinPoint.getArgs();
        String name = joinPoint.getSignature().getName();
        if(restfulResult.getCode()!=RestfulResult.SC_OK){
            return;
        }


        if("saveBug".equals(name)){//新增BUG，发邮件,设置超时
            Bug bug = (Bug) restfulResult.getData();
            if(bug==null){
                return;
            }
            mailUtil.sendMailToLeadingAndLParent(bug);
            stringRedisTemplate.opsForValue().set(bug.getId()+"",bug.getTitle() ,Long.parseLong(bug.getAllCycles()), TimeUnit.SECONDS);
        }

        if("updateStatusBug".equals(name)){//确认BUG，发邮件
            Bug bug = (Bug) restfulResult.getData();
            if(bug==null){
                return;
            }
            if(bug.getStatus()!=Bug.STATUS_CONFIRM){
                return;
            }
            mailUtil.sendMailToCreateByBugConfirm(bug);
        }

        //写系统日志
        systemLogService.save(objects,name,restfulResult);
    }


}
