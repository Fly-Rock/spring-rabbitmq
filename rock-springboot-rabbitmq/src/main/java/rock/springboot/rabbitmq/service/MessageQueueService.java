package rock.springboot.rabbitmq.service;


import rock.springboot.rabbitmq.sendmessage.SendOrderDoneMessage;
import rock.springboot.rabbitmq.sendmessage.SendOrderStatisticsMessage;
import rock.springboot.rabbitmq.sendmessage.SendUserUpdateMessage;
import rock.springboot.rabbitmq.sendmessage.SendWeChatNotifyMessage;

/**
 * Created by lichuanjie on 2018/4/6.
 */
public interface MessageQueueService {

    void publishWeChatNotifyMessage(SendWeChatNotifyMessage messageBody, String messageTypeName);

    void publishOrderDoneMessage(SendOrderDoneMessage messageBody, String messageTypeName);

    void publishUserUpdateMessage(SendUserUpdateMessage messageBody, String messageTypeName);

    void publishOrderStatisticsMessage(SendOrderStatisticsMessage messageBody, String messageTypeName);

    void publishMessage(SendOrderDoneMessage messageBody,String routingKey, String messageTypeName);
}
