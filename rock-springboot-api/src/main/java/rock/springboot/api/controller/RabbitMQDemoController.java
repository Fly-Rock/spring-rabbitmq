package rock.springboot.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import rock.springboot.rabbitmq.sendmessage.SendWeChatNotifyMessage;
import rock.springboot.rabbitmq.service.MessageQueueService;


/**
 * Created by lichuanjie on 2018/4/7.
 */
@RestController
@RequestMapping(value = {"/notify/v1.1/"})
public class RabbitMQDemoController {
    @Autowired
    private MessageQueueService messageQueueService;

    @RequestMapping(value = "send", method = RequestMethod.GET)
    public String send() {
        SendWeChatNotifyMessage message = new SendWeChatNotifyMessage();
        message.setLinkUrl("");
        message.setReceiveUerId(23051038);
        message.setTemplateData("content data");
        message.setTemplateType("0");
        message.setTopColor("#fff000");
        message.setTemplateId("122222");
        message.setWeChatAppId("12222222222");
        System.out.println("send start");
        long start = System.currentTimeMillis(); //获取开始时间

        for (int i = 0; i < 1; i++) {
            messageQueueService.publishWeChatNotifyMessage(message, "");
        }

        long end = System.currentTimeMillis(); //获取结束时间
        System.out.println("send end");
        System.out.println("send程序运行时间： " + (end - start) + "ms");
        System.out.println("send程序运行时间： " + (end - start) / 1000 + "s");
        return (end - start) + "ms";
    }

}
