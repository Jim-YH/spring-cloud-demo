package jim.demo.spring.cloud.client;

import com.hmily.tcc.annotation.Tcc;
import jim.demo.spring.cloud.client.hystrix.AccountClientHystrix;
import jim.demo.spring.cloud.config.FeignConfiguration;
import jim.demo.spring.cloud.dto.AccountDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

/**
 * @author jim
 * @date 2018/4/24
 */
@FeignClient(value = "spring-cloud-feign-account", configuration = FeignConfiguration.class, fallbackFactory = AccountClientHystrix.class)
public interface AccountClient {

    /**
     * 用户账户付款
     * @param accountDTO 实体类
     * @return true 成功
     */
    @PostMapping("/account/payment")
    @Tcc
    Boolean payment(@RequestBody AccountDTO accountDTO);

    /**
     * 获取用户账户信息
     * @param userId 用户id
     * @return AccountDO
     */
    @PostMapping("/account/findByUserId")
    BigDecimal findByUserId(@RequestParam("userId") String userId);

}
