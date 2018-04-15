package rock.springboot.rabbitmq.basemessage;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;
import java.util.Map;


/**
 * Created by lichuanjie on 2018/4/5.
 */
@Data
@EqualsAndHashCode
public class MqMessageHeader implements Serializable {

    /**
     * 消息ID。取当前机器的guid.
     */
    @JSONField(name = "MessageId")
    private String messageId;

    /**
     * 发送方IP。
     */
    @JSONField(name = "SendMachineIp")
    private String sendMachineIp;

    /**
     * 用户消息类型全名。
     */
    @JSONField(name = "MessageTypeFullname")
    private String messageTypeFullName;

    /**
     * 消息发送时间。取当前发送机器的本地时间。
     */
    @JSONField(name = "SendDateTime")
    private String sendDateTime;

    /**
     * 用户自定义部分头信息。
     */
    @JSONField(name = "CustomerHeader")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Map<String, String>> customerHeader;
}
