package jim.demo.spring.cloud.mapper;

import jim.demo.spring.cloud.config.CommonMapper;
import jim.demo.spring.cloud.entity.AccountDO;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author jim
 */
public interface AccountMapper extends CommonMapper<AccountDO> {

    /**
     * 扣减账户余额
     * @param account 实体类
     * @return rows
     */
    @Update("update account set balance = #{balance}, freeze_amount=#{freezeAmount}, " +
            "update_time = #{updateTime} where user_id = #{userId} and balance > 0")
    int update(AccountDO account);

    /**
     * 确认扣减账户余额
     * @param account 实体类
     * @return rows
     */
    @Update("update account set freeze_amount = #{freezeAmount}, update_time = #{updateTime} " +
            "where user_id = #{userId} and freeze_amount >0 ")
    int confirm(AccountDO account);

    /**
     * 取消扣减账户余额
     * @param account 实体类
     * @return rows
     */
    @Update("update account set balance = #{balance}, " +
            "freeze_amount = #{freezeAmount}, update_time = #{updateTime} " +
            "where user_id = #{userId} and freeze_amount >0")
    int cancel(AccountDO account);

    /**
     * 根据userId获取用户账户信息
     * @param userId 用户id
     * @return AccountDO
     */
    @Select("select * from account where user_id = #{userId}")
    AccountDO findByUserId(String userId);

}