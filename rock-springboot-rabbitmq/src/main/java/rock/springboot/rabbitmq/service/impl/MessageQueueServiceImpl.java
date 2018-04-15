package rock.springboot.rabbitmq.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rock.springboot.rabbitmq.basemessage.MessageLog;
import rock.springboot.rabbitmq.basemessage.MqMessage;
import rock.springboot.rabbitmq.basemessage.MqMessageBase;
import rock.springboot.rabbitmq.basemessage.MqMessageHeader;
import rock.springboot.rabbitmq.config.MessageQueueConfig;
import rock.springboot.rabbitmq.config.RabbitMQConfig;
import rock.springboot.rabbitmq.sendmessage.SendOrderDoneMessage;
import rock.springboot.rabbitmq.sendmessage.SendOrderStatisticsMessage;
import rock.springboot.rabbitmq.sendmessage.SendUserUpdateMessage;
import rock.springboot.rabbitmq.sendmessage.SendWeChatNotifyMessage;
import rock.springboot.rabbitmq.service.MessageQueueService;


import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Created by lichuanjie on 2018/4/6.
 */
@Slf4j
@Service
public class MessageQueueServiceImpl implements MessageQueueService {
    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSS'+08:00'";
    @Autowired
    private RabbitTemplate template;

    @Autowired
    private MessageQueueConfig messageQueueConfig;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void publishWeChatNotifyMessage(SendWeChatNotifyMessage messageBody, String messageTypeName) {
        MqMessage<SendWeChatNotifyMessage> message = new MqMessage<>();
        message.setMessageBody(messageBody);//设置消息body
        message.setMessageHeader(this.getMessageHeader(messageTypeName));//设置消息header
        this.publishMessage(message, messageQueueConfig.getRoutingKeyWeChatSend(),
                SendWeChatNotifyMessage.class.toString());
    }

    @Override
    public void publishOrderDoneMessage(SendOrderDoneMessage messageBody, String messageTypeName) {
        MqMessage<SendOrderDoneMessage> message = new MqMessage<>();
        message.setMessageBody(messageBody);//设置消息body
        message.setMessageHeader(this.getMessageHeader(messageTypeName));//设置消息header
        this.publishMessage(message, messageQueueConfig.getRoutingKeyOrderSuccess(),
                SendOrderDoneMessage.class.toString());
    }

    @Override
    public void publishUserUpdateMessage(SendUserUpdateMessage messageBody, String messageTypeName) {
        MqMessage<SendUserUpdateMessage> message = new MqMessage<>();
        message.setMessageBody(messageBody);//设置消息body
        message.setMessageHeader(this.getMessageHeader(messageTypeName));//设置消息header
        this.publishUserMessage(message, "",
                SendOrderDoneMessage.class.toString());
    }

    @Override
    public void publishOrderStatisticsMessage(SendOrderStatisticsMessage messageBody, String messageTypeName) {
        MqMessage<SendOrderStatisticsMessage> message = new MqMessage<>();
        message.setMessageBody(messageBody);//设置消息body
        message.setMessageHeader(this.getMessageHeader(messageTypeName));//设置消息header
        this.publishDirectMessage(message,"rock.orderStatisticsEvented",
                SendOrderStatisticsMessage.class.toString());
    }

    @Override
    public void publishMessage(SendOrderDoneMessage messageBody, String routingKey, String messageTypeName) {
        MqMessage<SendOrderDoneMessage> message = new MqMessage<>();
        message.setMessageBody(messageBody);//设置消息body
        message.setMessageHeader(this.getMessageHeader(messageTypeName));//设置消息header
        this.publishMessage(message,routingKey,
                SendOrderStatisticsMessage.class.toString());
    }

    private void publishBaseMessage(MqMessageBase message, String exchange, String routingKey, String produceId) {

        /**持久化*/
        Boolean persistenceStatus = this.persistence(produceId, message);
        if (!persistenceStatus) {
            log.error(String.format("消息持久化失败, message:%1s",
                    JSON.toJSONString(message, SerializerFeature.WriteDateUseDateFormat)));
        }

        /**发送消息*/
        template.convertAndSend(exchange, routingKey, message, amqpMessage -> {
            // clear
            amqpMessage.getMessageProperties().getHeaders().clear();

            amqpMessage.getMessageProperties().setHeader("MachineName", System.getenv("COMPUTERNAME"));
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            amqpMessage.getMessageProperties().setHeader("CreatedDateTime", dateFormat.format(new Date()));

            amqpMessage.getMessageProperties().setHeader("FailedCount", "0");
            amqpMessage.getMessageProperties().setAppId(messageQueueConfig.getAppId());
            amqpMessage.getMessageProperties().setType("YesHJ.Framework.MessagingAvailability.MessageDeclare.Message");

            amqpMessage.getMessageProperties().setMessageId(UUID.randomUUID().toString());
            amqpMessage.getMessageProperties().setContentEncoding("utf-8");
            amqpMessage.getMessageProperties().setContentType(MessageProperties.CONTENT_TYPE_JSON);
            amqpMessage.getMessageProperties().setPriority(null);
            return amqpMessage;
        });

        log.info(String.format("mq basemessage:\n%1s",
                JSON.toJSONString(message, SerializerFeature.WriteDateUseDateFormat)));
    }
    private void publishUserMessage(MqMessageBase message, String routingKey, String produceId) {
        publishBaseMessage(message, "rock.user.fanout.ex", routingKey, produceId);
    }
    private void publishDirectMessage(MqMessageBase message, String routingKey, String produceId) {
        publishBaseMessage(message, "amq.direct", routingKey, produceId);
    }

    private void publishMessage(MqMessageBase message, String routingKey, String produceId) {
        publishBaseMessage(message, messageQueueConfig.getExchangeName(), routingKey, produceId);
    }

    private MqMessageHeader getMessageHeader(String messageTypeFullName) {
        MqMessageHeader messageHeader = new MqMessageHeader();
        messageHeader.setMessageId(UUID.randomUUID().toString());//消息ID
        messageHeader.setMessageTypeFullName(messageTypeFullName);//消息发送时间

        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        messageHeader.setSendDateTime(dateFormat.format(new Date()));//消息名称
        messageHeader.setSendMachineIp(this.getCurrentMachineIp());//发送机器IP

        return messageHeader;
    }

    private String getCurrentMachineIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.error("获取本机IP报错。", e);
        }

        return null;
    }

    private Boolean persistence(String produceId, MqMessageBase message) {
        MessageLog logPo = new MessageLog();

        logPo.setMessageCotent(JSON.toJSONString(message, SerializerFeature.WriteDateUseDateFormat));
        logPo.setMessageId(message.getMessageHeader().getMessageId());
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);

        try {
            logPo.setSendDateTime(dateFormat.parse(message.getMessageHeader().getSendDateTime()));
        } catch (ParseException e) {
            log.error("SimpleDateFormat.parse", e);
        }

        logPo.setProduceId(produceId);
        logPo.setSendMachineIp(message.getMessageHeader().getSendMachineIp());
        return true;
        //return this.messageLogDao.insert(logPo) > 0;
    }
}
