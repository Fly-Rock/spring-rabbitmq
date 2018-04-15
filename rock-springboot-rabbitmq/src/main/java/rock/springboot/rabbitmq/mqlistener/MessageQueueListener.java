package rock.springboot.rabbitmq.mqlistener;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.stereotype.Component;
import rock.springboot.rabbitmq.sendmessage.SendWeChatNotifyMessage;


/**
 * Created by lichuanjie on 2018/4/5.
 */
@Slf4j
@Component
@RabbitListener(queues = "${spring.rabbitmq.weChatQueue:rock.wechat.q}", containerFactory = "simpleRabbitListenerContainerFactory")
public class MessageQueueListener  {
    /**
     * 接受微信消息
     */
    @RabbitHandler
    public void receiveWechatNotifyEventedMessage(SendWeChatNotifyMessage notifyMessage) {
        try {
            System.out.println(JSON.toJSON(notifyMessage));
        } catch (Exception exception) {
            log.error("消息接受失败receiveWechatNotifyEventedMessage:", exception);
        }
    }
}
