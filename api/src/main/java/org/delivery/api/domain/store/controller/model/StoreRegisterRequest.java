package org.delivery.api.domain.store.controller.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.delivery.db.store.enums.StoreCategory;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StoreRegisterRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String address;

    @NotBlank
    private StoreCategory storeCategory;

    @NotBlank
    private String thumbnailUrl;

    @Min(8000)
    private BigDecimal minimumAmount;

    @Min(10000)
    private BigDecimal minimumDeliveryAmount;

    @NotBlank
    private String phoneNumber;
}
