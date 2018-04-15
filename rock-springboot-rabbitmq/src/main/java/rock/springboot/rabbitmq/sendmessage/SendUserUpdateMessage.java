package rock.springboot.rabbitmq.sendmessage;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by lichuanjie on 2018/4/15.
 */
@Data
public class SendUserUpdateMessage implements Serializable {
    private Integer userId;
    private String nickName;
    private String userName;
    private Integer sex;
}
