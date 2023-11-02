package org.delivery.api.domain.user.business;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotaion.Business;
import org.delivery.api.common.api.Api;
import org.delivery.api.domain.controller.model.TokenResponse;
import org.delivery.api.domain.token.business.TokenBusiness;
import org.delivery.api.domain.user.controller.model.UserLoginRequest;
import org.delivery.api.domain.user.controller.model.UserRegisterRequest;
import org.delivery.api.domain.user.controller.model.UserResponse;
import org.delivery.api.domain.user.converter.UserConverter;
import org.delivery.api.domain.user.model.User;
import org.delivery.api.domain.user.service.UserService;

import java.util.Optional;

/*
 *  실직적인 비지니스 서비스
 */
@RequiredArgsConstructor
@Business
public class UserBusiness {

    private final UserService userService;

    private final UserConverter userConverter;

    private final TokenBusiness tokenBusiness;


    // 가입 처리
    public UserResponse register(UserRegisterRequest request) {

        var entity = userConverter.toEntity(request);

        var newEntity = userService.register(entity);

        var response = userConverter.toResponse(newEntity);

        return response;
    }



    /*
         1. email, password 체크
         2. user 로그인 체크
         3. jwt 토큰 생성
         4. 토큰 반환
     */
    public TokenResponse login(UserLoginRequest request) {
        var gainEntity = userService.getUserWithThrow(request.getEmail(), request.getPassword());
        // 토큰 생성
        var tokenResponse = tokenBusiness.issueToken(gainEntity);

        return tokenResponse;
    }



    public UserResponse me(User user) {
        var userResponse = userConverter.toUserResponseFromUser(user);

        return userResponse;
    }
}
