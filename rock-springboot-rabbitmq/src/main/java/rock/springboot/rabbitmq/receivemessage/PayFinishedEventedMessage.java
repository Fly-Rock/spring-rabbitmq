package rock.springboot.rabbitmq.receivemessage;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;


/**
 * Created by lichuanjie on 2018/4/5.
 */
@Data
@EqualsAndHashCode
public class PayFinishedEventedMessage implements Serializable {

    @JSONField(name = "OrderId")
    private Long orderId;

    @JSONField(name = "OrderDate")
    private String orderDate;

    @JSONField(name = "BillDate")
    private String billDate;

    @JSONField(name = "IsBill")
    private Boolean isBill;

    @JSONField(name = "DealFee")
    private BigDecimal dealFee;

    @JSONField(name = "HJUserId")
    private Long hjUserId;

    @JSONField(name = "OrderType")
    private Integer orderType;

    @JSONField(name = "SellerId")
    private Long sellerId;

    @JSONField(name = "ExtendBillStatus")
    private Integer extendBillStatus;

    @JSONField(name = "SolutionCode")
    private String solutionCode;

    @JSONField(name = "SalesPlatformId")
    private Integer salesPlatformId;

    @JSONField(name = "HJUserName")
    private String hjUserName;

    @JSONField(name = "ProductInfo")
    private List<ProductMessage> productInfo;

    @JSONField(name = "SwapSolutionCode")
    private String swapSolutionCode;

    @JSONField(name = "ExtParam")
    private String extParam;

    @JSONField(name="GrouponCode")
    private String grouponCode;
}
