package jim.demo.spring.cloud.client.hystrix;

import feign.hystrix.FallbackFactory;
import jim.demo.spring.cloud.client.AccountClient;
import jim.demo.spring.cloud.dto.AccountDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * @author jim
 * @date 2018/4/24
 */
@Component
public class AccountClientHystrix implements FallbackFactory<AccountClient> {

    private static final Logger LOG = LoggerFactory.getLogger(AccountClientHystrix.class);

    @Override
    public AccountClient create(Throwable throwable) {
        return new AccountClient() {
            @Override
            public Boolean payment(AccountDTO accountDTO) {
                LOG.error("AccountClient fallback reason:[{}]", throwable.getMessage());
                return false;
            }

            @Override
            public BigDecimal findByUserId(String userId) {
                LOG.error("AccountClient fallback reason:[{}]", throwable.getMessage());
                return null;
            }
        };
    }
}
