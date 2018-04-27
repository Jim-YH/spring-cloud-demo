package jim.demo.spring.cloud.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author jim
 * @date 2018/4/24
 */
@Data
public class AccountDTO implements Serializable {

    /**
     * 用户id
     */
    private String userId;

    /**
     * 扣款金额
     */
    private BigDecimal amount;

}
