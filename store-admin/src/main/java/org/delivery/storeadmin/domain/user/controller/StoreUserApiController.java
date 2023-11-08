package org.delivery.storeadmin.domain.user.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.delivery.storeadmin.domain.authrization.model.UserSession;
import org.delivery.storeadmin.domain.user.controller.model.StoreUserResponse;
import org.delivery.storeadmin.domain.user.converter.StoreUserConverter;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/store-user")
@RequiredArgsConstructor
public class StoreUserApiController {

    private final StoreUserConverter storeUserConverter;


    @GetMapping("/me")
    public StoreUserResponse me(@Parameter(hidden = true) @AuthenticationPrincipal UserSession userSession){

        return storeUserConverter.toResponse(userSession);
    }
}
