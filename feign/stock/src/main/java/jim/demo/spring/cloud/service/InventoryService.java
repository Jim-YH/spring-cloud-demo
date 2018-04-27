
package jim.demo.spring.cloud.service;

import com.hmily.tcc.annotation.Tcc;
import jim.demo.spring.cloud.dto.InventoryDTO;
import jim.demo.spring.cloud.entity.InventoryDO;

/**
 * @author xiaoyu
 */
public interface InventoryService {


    /**
     * 扣减库存操作
     * 这一个tcc接口
     *
     * @param inventoryDTO 库存DTO对象
     * @return true
     */
    @Tcc
    Boolean decrease(InventoryDTO inventoryDTO);


    /**
     * 获取商品库存信息
     * @param productId 商品id
     * @return InventoryDO
     */
    InventoryDO findByProductId(String productId);


    /**
     * mock 库存扣减try阶段异常
     *
     * @param inventoryDTO dto
     * @return true
     */
    @Tcc
    Boolean mockWithTryException(InventoryDTO inventoryDTO);


    /**
     * mock 库存扣减try阶段超时
     *
     * @param inventoryDTO dto
     * @return true
     */
    @Tcc
    Boolean mockWithTryTimeout(InventoryDTO inventoryDTO);


}
