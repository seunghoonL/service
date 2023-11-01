package org.delivery.api.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class AuthorizationInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("Authorization Interceptor URL : {}", request.getRequestURI());
        log.info("request method : {}", request.getMethod());

        // get post options = pass
        if (HttpMethod.OPTIONS.matches(request.getMethod())){
            return true;
        }

        // js, html, png resource 요청 경우  pass
        if (handler instanceof ResourceHttpRequestHandler){
            return true;
        }

        // TODO header 검증

        return true;
    }
}
