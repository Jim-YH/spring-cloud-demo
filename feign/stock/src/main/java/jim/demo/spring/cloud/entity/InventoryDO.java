package jim.demo.spring.cloud.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author jim
 */
@Data
public class InventoryDO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id")
    private String productId;

    /**
     * 总库存
     */
    @Column(name = "total_inventory")
    private Integer totalInventory;

    /**
     * 锁定库存
     */
    @Column(name = "lock_inventory")
    private Integer lockInventory;


}