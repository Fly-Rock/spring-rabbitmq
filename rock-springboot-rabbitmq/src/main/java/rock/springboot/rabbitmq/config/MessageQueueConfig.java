package rock.springboot.rabbitmq.config;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Created by lichuanjie on 2018/4/5.
 */
@Data
@EqualsAndHashCode
//@ConfigurationProperties(prefix = "spring.rabbitmq")
@ConfigurationProperties(locations = "classpath:application.properties",prefix = "spring.rabbitmq")
@Configuration
public class MessageQueueConfig {
    /**
     * 地址
     */
    private String host;

    /**
     * 端口
     */
    private String port;

    /**
     * 虚拟主机
     */
    private String virtualHost;

    /**
     * appid
     */
    private String appId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String passWord;

    /**
     * 交换机名称
     */
    private String exchangeName;


    /**
     * 接受消息queue name
     */
    private String orderQueue;
    /**
     * 接受消息queue name
     */
    private String weChatQueue;

    /**
     * 微信通知
     */
    private String routingKeyWeChatSend;

    private String routingKeyOrderSuccess;

}
