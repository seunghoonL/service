package org.delivery.api.domain.token.business;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotaion.Business;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.api.domain.controller.model.TokenResponse;
import org.delivery.api.domain.token.converter.TokenConverter;
import org.delivery.api.domain.token.service.TokenService;
import org.delivery.db.user.UserEntity;

import java.util.Optional;

@Business
@RequiredArgsConstructor
public class TokenBusiness {

    private final TokenService tokenService;

    private final TokenConverter tokenConverter;

    /* 1. userId 추출
     * 2. access refresh token 발행
     * 3. token response 변환
     */

    public TokenResponse issueToken(UserEntity userEntity){
            return  Optional.ofNullable(userEntity.getId()).map(id -> {
                var accessTokenDto = tokenService.issueToken(id);
                var refreshTokenDto = tokenService.issueRefreshToken(id);

                return tokenConverter.toResponse(accessTokenDto, refreshTokenDto);
            }).orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }

    public Long validationAccessToken(String accessToken){
        var userId = tokenService.validation(accessToken);

        return userId;
    }
}
