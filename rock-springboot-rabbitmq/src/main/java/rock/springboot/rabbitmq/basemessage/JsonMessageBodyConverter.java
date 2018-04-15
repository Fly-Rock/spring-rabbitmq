package rock.springboot.rabbitmq.basemessage;

import org.springframework.amqp.core.MessageProperties;

/**
 * Created by lichuanjie on 2018/4/5.
 */
public interface JsonMessageBodyConverter {

    Object fromMessage(byte[] messageBody, String encoding, MessageProperties properties);
}
