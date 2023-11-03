package org.delivery.db.store;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.delivery.db.BaseEntity;
import org.delivery.db.store.enums.StoreCategory;
import org.delivery.db.store.enums.StoreStatus;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@Table(name = "store")
public class StoreEntity extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 150, nullable = false)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(length = 50, nullable = false)
    private StoreStatus status;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private StoreCategory category;

    private double star;

    @Column(length = 200, nullable = false)
    private String thumbnailUrl;

    @Column(precision = 11, scale = 4, nullable = false)
    private BigDecimal minimumAmount;

    @Column(precision = 11, scale = 4, nullable = false)
    private BigDecimal minimumDeliveryAmount;

    @Column(length = 20)
    private String phoneNumber;

}
