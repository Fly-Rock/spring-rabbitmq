package rock.springboot.service.product;

import com.alibaba.fastjson.JSON;
import com.aoup.service.facade.IProductService;
import com.oaup.support.model.bo.product.ProductDetailCompositiveBo;
import com.oaup.support.model.bo.product.ProductDetailTableBo;
import com.oaup.support.model.dto.request.product.ProductDetailReq;
import com.oaup.support.model.dto.response.product.ProductDetailResp;
import com.oaup.support.model.po.ProductDescription;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import rock.springboot.service.product.impl.ProductDescriptionBiz;

import java.util.List;

/**
 * Created by lichuanjie on 2018/4/22.
 */
@Service
@Slf4j
public class ProductService implements IProductService {
    @Autowired
    private ProductBiz productBiz;
    @Autowired
    private ProductDescriptionBiz descriptionBiz;

    public ProductDetailResp queryProductDetail(ProductDetailReq req) {
        ProductDetailResp resp = new ProductDetailResp();
        ProductDescription queryProduct = new ProductDescription();
        queryProduct.setProductId(10000);
        List<ProductDescription> productList = descriptionBiz.query(queryProduct);
        if (CollectionUtils.isEmpty(productList)) {
            return resp;
        }

        for(ProductDescription m : productList){
            if (m.getContentStyle().equals("detail_table")) {
                ProductDetailTableBo detailTableBo = JSON.parseObject(m.getContent(), ProductDetailTableBo.class);
            }
            if(m.getContentStyle().equals("detail_zonghe")){
                ProductDetailCompositiveBo list = JSON.parseObject(m.getContent(), ProductDetailCompositiveBo.class);
            }
        }




        return null;
    }
}

