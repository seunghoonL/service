package org.delivery.storeadmin.domain.user.converter;

import lombok.RequiredArgsConstructor;
import org.delivery.db.store.StoreEntity;
import org.delivery.db.store.StoreRepository;
import org.delivery.db.store.enums.StoreStatus;
import org.delivery.db.storeuser.StoreUserEntity;
import org.delivery.storeadmin.domain.authrization.model.UserSession;
import org.delivery.storeadmin.domain.user.controller.model.StoreUserRegisterRequest;
import org.delivery.storeadmin.domain.user.controller.model.StoreUserResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreUserConverter {

    // TODO STORE SERVICE 생기면 이관 필요
    private final StoreRepository storeRepository;

    public StoreUserEntity toEntity(StoreUserRegisterRequest request, StoreEntity storeEntity){

        return StoreUserEntity.builder()
                .storeId(storeEntity.getId())
                .email(request.getEmail())
                .password(request.getPassword())
                .role(request.getRole())
                .build();
    }


    public StoreUserResponse toResponse(StoreUserEntity storeUserEntity, StoreEntity storeEntity){
        return StoreUserResponse.builder()
                .userResponse(StoreUserResponse.UserResponse.builder()
                        .id(storeUserEntity.getId())
                        .email(storeUserEntity.getEmail())
                        .status(storeUserEntity.getStatus())
                        .role(storeUserEntity.getRole())
                        .registeredAt(storeUserEntity.getRegisteredAt())
                        .unregisteredAt(storeUserEntity.getUnregisteredAt())
                        .lastLoginAt(storeUserEntity.getLastLoginAt())
                        .build())

                .storeResponse(StoreUserResponse.StoreResponse.builder()
                        .name(storeEntity.getName())
                        .storeId(storeEntity.getId())
                        .build())

                .build();
    }


    public StoreUserResponse toResponse(UserSession userSession){
        return StoreUserResponse.builder()
                .userResponse(StoreUserResponse.UserResponse.builder()
                        .id(userSession.getUserId())
                        .email(userSession.getEmail())
                        .status(userSession.getStatus())
                        .role(userSession.getRole())
                        .registeredAt(userSession.getRegisteredAt())
                        .unregisteredAt(userSession.getUnregisteredAt())
                        .lastLoginAt(userSession.getLastLoginAt())
                        .build())

                .storeResponse(StoreUserResponse.StoreResponse.builder()
                        .name(userSession.getStoreName())
                        .storeId(userSession.getStoreId())
                        .build())

                .build();


    }
}
