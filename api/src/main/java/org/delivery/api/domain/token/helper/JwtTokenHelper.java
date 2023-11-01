package org.delivery.api.domain.token.helper;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.error.TokenErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.api.domain.token.ifs.TokenHelper;
import org.delivery.api.domain.token.model.TokenDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.security.SignatureException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenHelper implements TokenHelper{

    @Value("${token.secret.key}")
    private String secretKey;

    @Value("${token.access-token.plus-hour}")
    private Long accessTokenPlusHour;

    @Value("${token.refresh-token.plus-hour}")
    private Long refreshTokenPlusHour;



    private TokenDto getTokenDto(Map<String, Object> data, Long tokenPlusHour) {
        LocalDateTime expiredLocalDateTime = LocalDateTime.now().plusHours(tokenPlusHour);

        Date expiredAt = Date.from(expiredLocalDateTime.atZone(ZoneId.systemDefault())
                .toInstant());

        SecretKey secretKey = Keys.hmacShaKeyFor(this.secretKey.getBytes());

        String token = Jwts.builder()
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .setClaims(data)
                .setExpiration(expiredAt)
                .compact();

        return TokenDto.builder()
                .token(token)
                .expiredAt(expiredLocalDateTime)
                .build();
    }

    @Override
    public TokenDto issueAccessToken(Map<String, Object> data) {
        return getTokenDto(data, accessTokenPlusHour);
    }

    @Override
    public TokenDto issueRefreshToken(Map<String, Object> data) {
        return getTokenDto(data, refreshTokenPlusHour);
    }

    @Override
    public Map<String, Object> validationTokenWithThrow(String token) {
        SecretKey secretKey = Keys.hmacShaKeyFor(this.secretKey.getBytes());

        // jwt 문자열을 읽어 객체로 변환
        JwtParser jwtParser = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build();


        try{
            Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);
            Claims claims = claimsJws.getBody();

            return new HashMap<String, Object>(claims);
        }catch (Exception e){


            if (e instanceof SignatureException){ // 유효 x
                throw new ApiException(TokenErrorCode.INVALID_TOKEN, e);
            }
            if (e instanceof ExpiredJwtException){ // 기간 만료
                throw new ApiException(TokenErrorCode.EXPIRED_TOKEN, e);
            }
            else {
                throw new ApiException(TokenErrorCode.TOKEN_EXCEPTION, e);
            }

        }
    }
}
