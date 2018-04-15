package rock.springboot.rabbitmq.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rock.springboot.rabbitmq.basemessage.FastJsonMessageConverter;

/**
 * Created by lichuanjie on 2018/4/5.
 */
@Configuration
@EnableRabbit
@AutoConfigureAfter(MessageQueueConfig.class)
public class RabbitMQConfig {
    @Autowired
    private MessageQueueConfig messageQueueConfig;

    private Log log = LogFactory.getLog(RabbitMQConfig.class);
    private final int ChannelCacheSize = 50;
    private final int ChannelCheckoutTimeout = 1000 * 30;

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(this.getPublishConnectionFactory());
        template.setMessageConverter(new FastJsonMessageConverter());

        /**设置callback*/
        template.setConfirmCallback((correlationData, ack, cause) -> {

            if (!ack) {
                log.info(String.format("消息发送callback,发送消息失败。%s",
                        cause + correlationData.toString()));
            }else {
                log.info("info ack success");
            }
        });

        return template;
    }

    @Bean(name = "simpleRabbitListenerContainerFactory")
    public SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory() {

        SimpleRabbitListenerContainerFactory containerFactory = new SimpleRabbitListenerContainerFactory();
        containerFactory.setConnectionFactory(this.getConsumerConnectionFactory());
        containerFactory.setConcurrentConsumers(3);
        containerFactory.setMaxConcurrentConsumers(3);
        containerFactory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        containerFactory.setMessageConverter(new FastJsonMessageConverter());

        return containerFactory;
    }

    private ConnectionFactory getConsumerConnectionFactory() {

        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setAddresses(this.messageQueueConfig.getHost());
        factory.setPort(Integer.valueOf(this.messageQueueConfig.getPort()));
        factory.setUsername(this.messageQueueConfig.getUserName());
        factory.setPassword(this.messageQueueConfig.getPassWord());
        factory.setVirtualHost(this.messageQueueConfig.getVirtualHost());
        factory.setChannelCacheSize(this.ChannelCacheSize);
        factory.setChannelCheckoutTimeout(this.ChannelCheckoutTimeout);
        factory.setCloseExceptionLogger((log1, s, throwable)
                -> log1.warn(String.format("rabbitmq channel close：%s", s), throwable));

        return factory;
    }

    private ConnectionFactory getPublishConnectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory();

        factory.setAddresses(this.messageQueueConfig.getHost());
        factory.setPort(Integer.valueOf(this.messageQueueConfig.getPort()));
        factory.setUsername(this.messageQueueConfig.getUserName());
        factory.setPassword(this.messageQueueConfig.getPassWord());
        factory.setVirtualHost(this.messageQueueConfig.getVirtualHost());
        factory.setPublisherConfirms(true);
        factory.setChannelCacheSize(this.ChannelCacheSize);
        factory.setChannelCheckoutTimeout(this.ChannelCheckoutTimeout);
        factory.setCloseExceptionLogger((log1, s, throwable) ->
                log1.warn(String.format("rabbitmq channel close：%s", s), throwable));

        return factory;
    }
}
