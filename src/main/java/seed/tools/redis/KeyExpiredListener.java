package seed.tools.redis;

import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import seed.entity.Bug;
import seed.mapper.BugMapper;
import seed.mapper.UserMapper;
import seed.tools.email.MailUtil;

public class KeyExpiredListener extends KeyExpirationEventMessageListener {

    @Autowired
    MailUtil mailUtil;

    @Autowired
    BugMapper bugMapper;

    public KeyExpiredListener(RedisMessageListenerContainer listenerContainer) {
        super(listenerContainer);
    }


    @Override
    public void onMessage(Message message, byte[] pattern) {
        //过期的key
        String key = new String(message.getBody(),StandardCharsets.UTF_8);

        //发邮件通知
        try {
            Bug bug = new Bug();
            bug.setStatus(Bug.STATUS_TIMEOUT);
            bug.setModifierId(0L);
            bug.setModifierName("系统");
            bugMapper.update(bug);
            mailUtil.sendMailToLeadingAndLParentTimeOut(Long.parseLong(key));
        }catch (Exception e){

        }
    }
}
