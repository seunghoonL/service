package org.delivery.api.domain.token.service;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.api.Api;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.api.domain.token.ifs.TokenHelper;
import org.delivery.api.domain.token.model.TokenDto;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class TokenService {


    private final TokenHelper tokenHelper;



    public TokenDto issueToken(Long userId){
        var data = new HashMap<String, Object>();
        data.put("userId", userId);
        return tokenHelper.issueAccessToken(data);

    }

    public TokenDto issueRefreshToken(Long userId){
        var data = new HashMap<String, Object>();
        data.put("userId", userId);
       return tokenHelper.issueRefreshToken(data);
    }


    public Long validation(String token){
        Map<String, Object> map = tokenHelper.validationTokenWithThrow(token);

        var userId = map.get("userId");

        Objects.requireNonNull(userId, () -> { throw  new ApiException(ErrorCode.NULL_POINT); });

        return Long.parseLong(userId.toString());
    }

}
