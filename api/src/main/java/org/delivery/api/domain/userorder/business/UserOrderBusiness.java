package org.delivery.api.domain.userorder.business;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.delivery.api.common.annotaion.Business;
import org.delivery.api.domain.storemenu.service.StoreMenuService;
import org.delivery.api.domain.user.model.User;
import org.delivery.api.domain.userorder.controller.model.UserOrderRequest;
import org.delivery.api.domain.userorder.controller.model.UserOrderResponse;
import org.delivery.api.domain.userorder.converter.UserOrderConverter;
import org.delivery.api.domain.userorder.service.UserOrderService;
import org.delivery.api.domain.userordermenu.converter.UserOrderMenuConverter;
import org.delivery.api.domain.userordermenu.service.UserOrderMenuService;

import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Business
public class UserOrderBusiness {

    private final UserOrderService userOrderService;

    private final StoreMenuService storeMenuService;

    private final UserOrderConverter userOrderConverter;

    private final UserOrderMenuConverter userOrderMenuConverter;

    private final UserOrderMenuService userOrderMenuService;



    public UserOrderResponse userOrder(UserOrderRequest request, User user){
        // 주문 메뉴들 조회
        var storeMenuEntity = request.getStoreMenuIdList()
                .stream().map(it -> storeMenuService.getStoreMenuWithThrow(it))
                .collect(Collectors.toList());


        var entity = userOrderConverter.toEntity(user, storeMenuEntity);


        // 메뉴 주문
        var gainEntity = userOrderService.order(entity);



        // 주문한 메뉴  사용자 주문 메뉴 맵핑
        var userOrderMenuEntity = storeMenuEntity.stream()
                .map(it -> userOrderMenuConverter.toEntity(gainEntity, it))
                .collect(Collectors.toList());


        // 주문 메뉴 등록 남기기
        userOrderMenuEntity.forEach(it -> userOrderMenuService.order(it));

        log.info("gainEntity : {} ", gainEntity);
        return userOrderConverter.toResponse(gainEntity);
    }

}
