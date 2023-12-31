package org.delivery.api.domain.token.converter;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotaion.Converter;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.error.TokenErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.api.domain.controller.model.TokenResponse;
import org.delivery.api.domain.token.model.TokenDto;

import java.util.Objects;

@Converter
@RequiredArgsConstructor
public class TokenConverter {


    public TokenResponse toResponse(TokenDto accessToken, TokenDto refreshToken){

        Objects.requireNonNull(accessToken, () -> {throw new ApiException(ErrorCode.NULL_POINT); });
        Objects.requireNonNull(refreshToken, () -> { throw new ApiException(ErrorCode.NULL_POINT); });

        return TokenResponse.builder()
                .accessToken(accessToken.getToken())
                .accessExpiredAt(accessToken.getExpiredAt())
                .refreshToken(refreshToken.getToken())
                .refreshExpiredAt(refreshToken.getExpiredAt())
                .build();
    }
}
