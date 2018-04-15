package rock.springboot.rabbitmq.sendmessage;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by lichuanjie on 2018/4/6.
 */
@Data
public class SendWeChatNotifyMessage implements Serializable {
    /**
     * 微信模版ID
     */
    private String weChatAppId;
    /**
     * 微信发送内容
     */
    private String templateData;
    /**
     * 接受用户ID
     */
    private Integer receiveUerId;
    /**
     * 微信发送模版ID
     */
    private String templateId;
    /**
     * 模版类型
     */
    private String templateType;
    /**
     * 跳转链接
     */
    private String linkUrl;
    /**
     * 样式
     */
    private String topColor;
}
