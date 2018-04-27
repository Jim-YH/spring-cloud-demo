package jim.demo.spring.cloud.client.hystrix;

import feign.hystrix.FallbackFactory;
import jim.demo.spring.cloud.client.AccountClient;
import jim.demo.spring.cloud.client.InventoryClient;
import jim.demo.spring.cloud.dto.AccountDTO;
import jim.demo.spring.cloud.dto.InventoryDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @author jim
 * @date 2018/4/24
 */
@Component
public class InventoryClientHystrix implements FallbackFactory<InventoryClient> {

    private static final Logger LOG = LoggerFactory.getLogger(InventoryClientHystrix.class);

    @Override
    public InventoryClient create(Throwable throwable) {
        return new InventoryClient() {
            @Override
            public Boolean decrease(InventoryDTO inventoryDTO) {
                LOG.error("InventoryClient fallback reason:[{}]", throwable.getMessage());
                return false;
            }

            @Override
            public Integer findByProductId(String productId) {
                LOG.error("InventoryClient fallback reason:[{}]", throwable.getMessage());
                return 0;
            }

            @Override
            public Boolean mockWithTryException(InventoryDTO inventoryDTO) {
                LOG.error("InventoryClient fallback reason:[{}]", throwable.getMessage());
                return false;
            }

            @Override
            public Boolean mockWithTryTimeout(InventoryDTO inventoryDTO) {
                LOG.error("InventoryClient fallback reason:[{}]", throwable.getMessage());
                return false;
            }
        };
    }
}
