package jim.demo.spring.cloud.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xiaoyu
 */
@Data
public class InventoryDTO implements Serializable {

    /**
     * 商品id
     */
    private String  productId;


    /**
     * 数量
     */
    private Integer count;

}
