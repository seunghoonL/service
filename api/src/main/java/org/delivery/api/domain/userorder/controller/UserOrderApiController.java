package org.delivery.api.domain.userorder.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.delivery.api.common.annotaion.UserSession;
import org.delivery.api.common.api.Api;
import org.delivery.api.domain.user.model.User;
import org.delivery.api.domain.userorder.business.UserOrderBusiness;
import org.delivery.api.domain.userorder.controller.model.UserOrderDetailResponse;
import org.delivery.api.domain.userorder.controller.model.UserOrderRequest;
import org.delivery.api.domain.userorder.controller.model.UserOrderResponse;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/user-order")
@RequiredArgsConstructor
public class UserOrderApiController {


    private final UserOrderBusiness userOrderBusiness;


    // 주문

    @PostMapping("")
    public Api<UserOrderResponse> userOrder(@Valid @RequestBody Api<UserOrderRequest> request,
                                            @Parameter(hidden = true) @UserSession User user){

        var response = userOrderBusiness.userOrder(request.getBody(), user);
        return Api.ok(response);
    }


    //  현재 진행 중 주문
    @GetMapping("/current")
    public Api<List<UserOrderDetailResponse>> current(@UserSession @Parameter(hidden = true) User user){
        var response =  userOrderBusiness.current(user);
        return Api.ok(response);
    }


    // 과거 주문 내역
    @GetMapping("/history")
    public Api<List<UserOrderDetailResponse>> history(@UserSession @Parameter(hidden = true) User user){
        var response =  userOrderBusiness.history(user);
        return Api.ok(response);
    }


    // 주문 1건 내역
    @GetMapping("/search/{orderId}")
    public Api<UserOrderDetailResponse> search(@PathVariable Long orderId,
                                               @UserSession @Parameter(hidden = true) User user){
        var response =  userOrderBusiness.search(orderId, user);
        return Api.ok(response);
    }
}
