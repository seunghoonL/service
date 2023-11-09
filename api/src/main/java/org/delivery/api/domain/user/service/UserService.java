package org.delivery.api.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.error.UserErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.db.user.UserEntity;
import org.delivery.db.user.UserRepository;
import org.delivery.db.user.enums.UserStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

// 도메인 로직 처리
@RequiredArgsConstructor
@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;


    public UserEntity register(UserEntity userEntity) {

        return Optional.ofNullable(userEntity)
                .map(entity -> {
                    entity.setStatus(UserStatus.REGISTERED);
                    entity.setRegisteredAt(LocalDateTime.now());

                    return userRepository.save(entity);
                }).orElseThrow(() ->  new ApiException(ErrorCode.NULL_POINT, "User Entity Null"));
    }


    public UserEntity getUserWithThrow(String email, String password){
        UserEntity findEntity = userRepository
                .findFirstByEmailAndPasswordAndStatusOrderByIdDesc(email, password, UserStatus.REGISTERED)
                .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND, "User is Not Found"));

        findEntity.setLastLoginAt(LocalDateTime.now());
        return findEntity;
    }

    public UserEntity getUserWithThrow(Long userId) {
        UserEntity findEntity = userRepository.findFirstByIdAndStatusOrderByIdDesc(userId, UserStatus.REGISTERED)
                .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND, "User in Not Found"));

        return findEntity;
    }

}
