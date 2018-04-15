import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import rock.springboot.rabbitmq.sendmessage.SendOrderDoneMessage;
import rock.springboot.rabbitmq.sendmessage.SendOrderStatisticsMessage;
import rock.springboot.rabbitmq.sendmessage.SendUserUpdateMessage;
import rock.springboot.rabbitmq.service.MessageQueueService;

import java.util.Date;

/**
 * Created by lichuanjie on 2018/4/15.
 */
@Component
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {BaseTestConfig.class, ActiveEnvironment.DEV.class})
public class RabbitMqSendTest {
    @Autowired
    private MessageQueueService messageQueueService;

    @Test
    public void sendFanoutUserMessage() {
        SendUserUpdateMessage message = new SendUserUpdateMessage();
        message.setUserId(230151038);
        message.setNickName("笨石头");
        message.setSex(1);
        message.setUserName("weixin");

        System.out.println("send start");
        long start = System.currentTimeMillis(); //获取开始时间

        for (int i = 0; i < 100; i++) {
            messageQueueService.publishUserUpdateMessage(message, "");
        }

        long end = System.currentTimeMillis(); //获取结束时间
        System.out.println("send end");
        System.out.println("send程序运行时间： " + (end - start) + "ms");
        System.out.println("send程序运行时间： " + (end - start) / 1000 + "s");
    }

    @Test
    public void sendOrderDoneNotifyMessage() {
        SendOrderDoneMessage message = new SendOrderDoneMessage();
        message.setOrderDate(new Date());
        message.setOrderId(100001L);
        message.setPrice("99.99");
        message.setProductName("笨石头讲设计模式【责任链模式】");
        for (int i = 0; i < 100; i++) {
            messageQueueService.publishOrderDoneMessage(message, "");
        }
    }
    @Test
    public void sendOrderStat(){
        SendOrderStatisticsMessage message = new SendOrderStatisticsMessage();
        message.setOrderDate(new Date());
        message.setOrderId(100001L);
        message.setUserId(23051038);
        System.out.println("send start");
        for (int i = 0; i < 10; i++) {
            messageQueueService.publishOrderStatisticsMessage(message, "");
        }
    }

    /**
     * 发送两个队列里面
     */
    @Test
    public void sendOrderTopicOrangeMessage(){
        SendOrderDoneMessage message = new SendOrderDoneMessage();
        message.setOrderDate(new Date());
        message.setOrderId(100001L);
        message.setPrice("99.99");
        message.setProductName("笨石头讲设计模式【责任链模式】");
        for (int i = 0; i < 10; i++) {
            messageQueueService.publishMessage(message,"quick.orange.rabbit", "");
        }
    }

    /**
     * topic 实现fanout exchange
     */
    @Test
    public void sendOrderTopicFanoutMessage(){
        SendOrderDoneMessage message = new SendOrderDoneMessage();
        message.setOrderDate(new Date());
        message.setOrderId(100001L);
        message.setPrice("99.99");
        message.setProductName("笨石头讲设计模式【责任链模式】");
        for (int i = 0; i < 10; i++) {
            messageQueueService.publishMessage(message,"", "");
        }
    }



    @Test
    @RabbitListener(queues="rock.direct.statisticsDay.order.q",containerFactory = "simpleRabbitListenerContainerFactory")
    public void consumerOrderStatDay(){
        System.out.println("success ");
    }
    @Test
    @RabbitListener(queues="rock.direct.statistics.order.q",containerFactory = "simpleRabbitListenerContainerFactory")
    public void consumerOrderStat(){
        System.out.println("success ");
    }
}
