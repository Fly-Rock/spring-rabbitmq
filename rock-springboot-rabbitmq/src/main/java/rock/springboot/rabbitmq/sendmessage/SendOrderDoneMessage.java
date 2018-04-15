package rock.springboot.rabbitmq.sendmessage;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by lichuanjie on 2018/4/15.
 */
@Data
public class SendOrderDoneMessage implements Serializable {
    private Long orderId;
    private String productName;
    private String price;
    private Date orderDate;
}
