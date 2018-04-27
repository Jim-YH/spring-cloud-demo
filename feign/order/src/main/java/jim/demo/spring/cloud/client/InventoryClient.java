package jim.demo.spring.cloud.client;

import com.hmily.tcc.annotation.Tcc;
import jim.demo.spring.cloud.client.hystrix.AccountClientHystrix;
import jim.demo.spring.cloud.client.hystrix.InventoryClientHystrix;
import jim.demo.spring.cloud.config.FeignConfiguration;
import jim.demo.spring.cloud.dto.InventoryDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author jim
 * @date 2018/4/24
 */
@FeignClient(value = "spring-cloud-feign-inventory", configuration = FeignConfiguration.class, fallbackFactory = InventoryClientHystrix.class)
public interface InventoryClient {

    /**
     * 库存扣减
     * @param inventoryDTO 实体对象
     * @return true 成功
     */
    @Tcc
    @RequestMapping("/inventory/decrease")
    Boolean decrease(@RequestBody InventoryDTO inventoryDTO);

    /**
     * 获取商品库存
     * @param productId 商品id
     * @return InventoryDO
     */
    @RequestMapping("/inventory/findByProductId")
    Integer findByProductId(@RequestParam("productId") String productId);

    /**
     * 模拟库存扣减异常
     * @param inventoryDTO 实体对象
     * @return true 成功
     */
    @Tcc
    @RequestMapping("/inventory/mockWithTryException")
    Boolean mockWithTryException(@RequestBody InventoryDTO inventoryDTO);

    /**
     * 模拟库存扣减超时
     * @param inventoryDTO 实体对象
     * @return true 成功
     */
    @Tcc
    @RequestMapping("/inventory/mockWithTryTimeout")
    Boolean mockWithTryTimeout(@RequestBody InventoryDTO inventoryDTO);

}
