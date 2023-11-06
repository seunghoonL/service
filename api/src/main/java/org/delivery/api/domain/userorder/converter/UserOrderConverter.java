package org.delivery.api.domain.userorder.converter;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotaion.Converter;
import org.delivery.api.domain.user.model.User;
import org.delivery.api.domain.userorder.controller.model.UserOrderResponse;
import org.delivery.db.storemenu.StoreMenuEntity;
import org.delivery.db.userorder.UserOrderEntity;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
@Converter
public class UserOrderConverter {
    public UserOrderEntity toEntity(User user, List<StoreMenuEntity> entity){

        var totalAmount = entity.stream()
                .map(it -> it.getAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);


        var result  =  UserOrderEntity.builder()
                .userId(user.getId())
                .amount(totalAmount)
                .build();


        return result;
    }



    public UserOrderResponse toResponse(UserOrderEntity entity){
        return UserOrderResponse.builder()
                .id(entity.getId())
                .status(entity.getStatus())
                .amount(entity.getAmount())
                .orderedAt(entity.getOrderedAt())
                .acceptedAt(entity.getAcceptedAt())
                .cookingStartedAt(entity.getCookingStartedAt())
                .deliveryStartedAt(entity.getDeliveryStartedAt())
                .receivedAt(entity.getReceivedAt())
                .build();
    }


}
