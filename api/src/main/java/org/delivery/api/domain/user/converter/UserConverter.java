package org.delivery.api.domain.user.converter;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotaion.Converter;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.api.domain.user.controller.model.UserRegisterRequest;
import org.delivery.api.domain.user.controller.model.UserResponse;
import org.delivery.api.domain.user.model.User;
import org.delivery.db.user.UserEntity;

import java.util.Optional;

@RequiredArgsConstructor
@Converter
public class UserConverter {


    public UserEntity toEntity(UserRegisterRequest request){


        return Optional.ofNullable(request)
                .map(it -> UserEntity.builder()
                            .name(request.getName())
                            .email(request.getEmail())
                            .address(request.getAddress())
                            .password(request.getPassword())
                            .build()).orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "UserRequest Null"));
    }


    public UserResponse toResponse(UserEntity userEntity) {

        return Optional.ofNullable(userEntity)
                .map(entity ->
                   UserResponse.builder()
                            .id(entity.getId())
                            .name(entity.getName())
                            .status(entity.getStatus())
                            .email(entity.getEmail())
                            .address(entity.getAddress())
                            .registerAt(entity.getRegisterAt())
                            .unregisterAt(entity.getUnregisterAt())
                            .lastLoginAt(entity.getLastLoginAt())
                            .build()).orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "UserEntity Null"));
    }


    public UserResponse toUserResponseFromUser(User user){

        return Optional.ofNullable(user).map(it ->
                UserResponse.builder()
                        .id(it.getId())
                        .name(it.getName())
                        .status(it.getStatus())
                        .email(it.getEmail())
                        .address(it.getAddress())
                        .registerAt(it.getRegisterAt())
                        .unregisterAt(it.getUnregisterAt())
                        .lastLoginAt(it.getLastLoginAt())
                        .build()).orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT, "UserEntity Null"));
    }
}
