package rock.springboot.rabbitmq.sendmessage;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by lichuanjie on 2018/4/15.
 */
@Data
public class SendOrderStatisticsMessage implements Serializable {
    private Long orderId;
    private Date orderDate;
    private Integer userId;
}
