package org.delivery.api.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

@Slf4j
@Component
@WebFilter(urlPatterns = "/*")
public class LoggerFilter implements Filter {



    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        var req = new ContentCachingRequestWrapper((HttpServletRequest) request);
        var res = new ContentCachingResponseWrapper((HttpServletResponse) response);




        chain.doFilter(req, res);
        // 응답 때 filter 로직

        // request

        Enumeration<String> headerNames = req.getHeaderNames();
        StringBuilder headerValues = new StringBuilder();

        headerNames.asIterator().forEachRemaining(key -> {
            String headerValue = req.getHeader(key);

            headerValues.append(" [")
                    .append(key)
                    .append(" : ")
                    .append(headerValue)
                    .append("] ");
        });

        String requestBody = new String(req.getContentAsByteArray());
        String URI = req.getRequestURI();
        String method = req.getMethod();

        log.info(">>REQUEST>> URI : {}, method : {}, header {} , body : {} ", URI, method, headerValues, requestBody);

        // response

        StringBuilder responseHeaderValues = new StringBuilder();

        res.getHeaderNames().forEach(key -> {
            String headerValue = res.getHeader(key);

            responseHeaderValues.append(" [")
                    .append(key)
                    .append(" : ")
                    .append(headerValue)
                    .append("] ");
        });

        String responseBody = new String(res.getContentAsByteArray());


        log.info("<<RESPONSE<< URI : {}, method : {}, header : {} , body : {} ", URI, method, responseHeaderValues, responseBody );


        res.copyBodyToResponse(); // response 에서 body 를 한번 읽었기 때문에 body 다시 복사 후 반환
    }

}
