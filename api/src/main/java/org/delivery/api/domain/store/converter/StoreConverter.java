package org.delivery.api.domain.store.converter;

import org.delivery.api.common.annotaion.Converter;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.api.domain.store.controller.model.StoreRegisterRequest;
import org.delivery.api.domain.store.controller.model.StoreResponse;
import org.delivery.db.store.StoreEntity;

import java.util.Optional;

@Converter
public class StoreConverter {


    public StoreEntity toEntity(StoreRegisterRequest request){

        return Optional.ofNullable(request).map(it -> {
            return StoreEntity.builder()
                    .name(request.getName())
                    .address(request.getAddress())
                    .category(request.getStoreCategory())
                    .minimumAmount(request.getMinimumAmount())
                    .minimumDeliveryAmount(request.getMinimumDeliveryAmount())
                    .thumbnailUrl(request.getThumbnailUrl())
                    .phoneNumber(request.getPhoneNumber())
                    .build();
        }).orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }



    public StoreResponse toResponse(StoreEntity entity){

        return Optional.ofNullable(entity).map(it -> {
        return StoreResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .address(entity.getAddress())
                .storeStatus(entity.getStatus())
                .storeCategory(entity.getCategory())
                .star(entity.getStar())
                .minimumAmount(entity.getMinimumAmount())
                .minimumDeliveryAmount(entity.getMinimumDeliveryAmount())
                .thumbnailUrl(entity.getThumbnailUrl())
                .phoneNumber(entity.getPhoneNumber())
                .build();
        }).orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }




}
