package rock.springboot.rabbitmq.basemessage;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageProperties;
import rock.springboot.rabbitmq.receivemessage.PayFinishedEventedMessage;
import rock.springboot.rabbitmq.sendmessage.SendOrderDoneMessage;
import rock.springboot.rabbitmq.sendmessage.SendOrderStatisticsMessage;
import rock.springboot.rabbitmq.sendmessage.SendUserUpdateMessage;
import rock.springboot.rabbitmq.sendmessage.SendWeChatNotifyMessage;

/**
 * Created by lichuanjie on 2018/4/5.
 */
@Slf4j
public class JsonMessageBodyConverterImpl implements JsonMessageBodyConverter {
    private static final String ON_WECHAT_SEND_EVENTED = "rock.weChatSendEvented";


    private static final String ON_ORDER_DONE_EVENTED = "rock.orderDoneEvented";

    private static final String ON_ORDER_STATISTICS_EVENTED = "rock.orderStatisticsEvented";

    @Override
    public Object fromMessage(byte[] messageBody, String encoding, MessageProperties properties) {
        String jsonString = new String(messageBody);
        if (properties.getReceivedRoutingKey().equals(ON_WECHAT_SEND_EVENTED)) {
            MqMessage result = JSON.parseObject(jsonString, MqMessage.class);
            return JSON.parseObject(result.getMessageBody().toString(),
                    SendWeChatNotifyMessage.class);
        }
        if (properties.getReceivedRoutingKey().equals(ON_ORDER_DONE_EVENTED)) {
            MqMessage result = JSON.parseObject(jsonString, MqMessage.class);
            return JSON.parseObject(result.getMessageBody().toString(),
                    SendOrderDoneMessage.class);
        }
        if (properties.getReceivedRoutingKey().equals("")) {
            MqMessage result = JSON.parseObject(jsonString, MqMessage.class);
            return JSON.parseObject(result.getMessageBody().toString(),
                    SendUserUpdateMessage.class);
        }
        if (properties.getReceivedRoutingKey().equals(ON_ORDER_STATISTICS_EVENTED)) {
            MqMessage result = JSON.parseObject(jsonString, MqMessage.class);
            return JSON.parseObject(result.getMessageBody().toString(),
                    SendOrderStatisticsMessage.class);
        }
        return null;
    }
}
