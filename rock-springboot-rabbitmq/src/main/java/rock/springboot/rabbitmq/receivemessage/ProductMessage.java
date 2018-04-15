package rock.springboot.rabbitmq.receivemessage;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;


/**
 * Created by lichuanjie on 2018/4/5.
 */
@Data
@EqualsAndHashCode
public class ProductMessage implements Serializable {

    @JSONField(name = "ProductID")
    private Long productId;

    @JSONField(name = "ReferenceProductID")
    private Long referenceProductId;

    @JSONField(name = "ProductName")
    private String productName;

}