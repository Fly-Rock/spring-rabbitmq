package rock.springboot.rabbitmq.basemessage;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;


/**
 * Created by lichuanjie on 2018/4/5.
 */
@Data
@EqualsAndHashCode
public abstract class MqMessageBase implements Serializable {

    /**
     * 获取或设置消息头
     */
    @JSONField(name = "MessageHeader")
    private MqMessageHeader messageHeader;
}