package org.delivery.api.domain.userorder.business;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.delivery.api.common.annotaion.Business;
import org.delivery.api.domain.store.converter.StoreConverter;
import org.delivery.api.domain.store.service.StoreService;
import org.delivery.api.domain.storemenu.converter.StoreMenuConverter;
import org.delivery.api.domain.storemenu.service.StoreMenuService;
import org.delivery.api.domain.user.model.User;
import org.delivery.api.domain.userorder.controller.model.UserOrderDetailResponse;
import org.delivery.api.domain.userorder.controller.model.UserOrderRequest;
import org.delivery.api.domain.userorder.controller.model.UserOrderResponse;
import org.delivery.api.domain.userorder.converter.UserOrderConverter;
import org.delivery.api.domain.userorder.producer.UserOrderProducer;
import org.delivery.api.domain.userorder.service.UserOrderService;
import org.delivery.api.domain.userordermenu.converter.UserOrderMenuConverter;
import org.delivery.api.domain.userordermenu.service.UserOrderMenuService;
import org.delivery.db.store.StoreEntity;
import org.delivery.db.userordermenu.UserOrderMenuEntity;

import java.util.List;
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

    private final StoreService storeService;

    private final StoreConverter storeConverter;

    private final StoreMenuConverter storeMenuConverter;

    private final UserOrderProducer userOrderProducer;


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


        // 비동기로 가맹점에 주문 알림 전송 producer -> exchange 로 전송
        userOrderProducer.sendOrder(gainEntity);

        return userOrderConverter.toResponse(gainEntity);
    }


    // 주문 조회
    public UserOrderDetailResponse search(Long orderId, User user) {
        var userOrderEntity = userOrderService.getUserOrderWithOutStatusThrow(orderId, user.getId());

        var userOrderMenuList = userOrderMenuService.getUserOrderMenu(userOrderEntity.getId());

        var storeMenuList = userOrderMenuList.stream()
                .map(uom -> {
                    var storeMenuEntity = storeMenuService.getStoreMenuWithThrow(uom.getStoreMenuId());
                    return storeMenuEntity;
                }).collect(Collectors.toList());

        // 사용자 주문한 스토어 TODO 리펙터링 필요
        var storeEntity = storeService.getStoreWithThrow(storeMenuList
                .stream()
                .findFirst()
                .get()
                .getStoreId());

        return UserOrderDetailResponse.builder()
                .userOrderResponse(userOrderConverter.toResponse(userOrderEntity))
                .storeResponse(storeConverter.toResponse(storeEntity))
                .storeMenuResponses(storeMenuConverter.toResponses(storeMenuList))
                .build();
    }





    // 배송 중인 주문 조회
    public List<UserOrderDetailResponse> current(User user) {
       var userOrders =  userOrderService.current(user.getId());

                // 주문 1건 씩 처리

        var result =  userOrders.stream().map(uo -> {
                    // 사용자 주문한 메뉴
                    var userOrderMenuList = userOrderMenuService.getUserOrderMenu(uo.getId());

                    var storeMenuList = userOrderMenuList.stream()
                            .map(uom -> {
                                var storeMenuEntity = storeMenuService.getStoreMenuWithThrow(uom.getStoreMenuId());
                                return storeMenuEntity;
                            }).collect(Collectors.toList());

                    // 사용자 주문한 스토어 TODO 리펙터링 필요
                    var storeEntity = storeService.getStoreWithThrow(storeMenuList
                            .stream()
                            .findFirst()
                            .get()
                            .getStoreId());

                    return UserOrderDetailResponse.builder()
                            .userOrderResponse(userOrderConverter.toResponse(uo))
                            .storeResponse(storeConverter.toResponse(storeEntity))
                            .storeMenuResponses(storeMenuConverter.toResponses(storeMenuList))
                            .build();

                }).collect(Collectors.toList());

        return result;
    }




    // 주문 기록 조회
    public List<UserOrderDetailResponse> history(User user) {
        var userOrders =  userOrderService.history(user.getId());

        // 주문 1건 씩 처리

        var result =  userOrders.stream().map(uo -> {
            // 사용자 주문한 메뉴
            var userOrderMenuList = userOrderMenuService.getUserOrderMenu(uo.getId());

            var storeMenuList = userOrderMenuList.stream()
                    .map(uom -> {
                        var storeMenuEntity = storeMenuService.getStoreMenuWithThrow(uom.getStoreMenuId());
                        return storeMenuEntity;
                    }).collect(Collectors.toList());

            // 사용자 주문한 스토어 TODO 리펙터링 필요
            var storeEntity = storeService.getStoreWithThrow(storeMenuList
                    .stream()
                    .findFirst()
                    .get()
                    .getStoreId());

            return UserOrderDetailResponse.builder()
                    .userOrderResponse(userOrderConverter.toResponse(uo))
                    .storeResponse(storeConverter.toResponse(storeEntity))
                    .storeMenuResponses(storeMenuConverter.toResponses(storeMenuList))
                    .build();

        }).collect(Collectors.toList());

        return result;
    }

}
