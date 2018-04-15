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
public class MqMessage<T extends Serializable> extends MqMessageBase implements Serializable {

    /**
     * 消息主体。
     */
    @JSONField(name = "MessageBody")
    private T messageBody;
}
