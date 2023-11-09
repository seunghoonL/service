package org.delivery.api.resolver;

import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotaion.UserSession;
import org.delivery.api.domain.user.business.UserBusiness;
import org.delivery.api.domain.user.model.User;
import org.delivery.api.domain.user.service.UserService;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class UserSessionResolver implements HandlerMethodArgumentResolver {

    private final UserService userService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // 지원하는 파라미터 체크, 어노테이션 체크
        var annotation = parameter.hasParameterAnnotation(UserSession.class);
        var parameterType = parameter.getParameterType().equals(User.class);

        return (annotation && parameterType);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        // supportParameter true 반환 시 여기 실행

        var userId = RequestContextHolder.getRequestAttributes()
                .getAttribute("userId", RequestAttributes.SCOPE_REQUEST);

        var userEntity = userService.getUserWithThrow(Long.parseLong(userId.toString()));

        // 사용자 정보 설정
        // 컨트롤러 매개변수 User로 반환
        return User.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .status(userEntity.getStatus())
                .address(userEntity.getAddress())
                .registerAt(userEntity.getRegisteredAt())
                .unregisterAt(userEntity.getUnregisteredAt())
                .lastLoginAt(userEntity.getLastLoginAt())
                .build();

    }
}
