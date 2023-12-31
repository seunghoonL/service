package org.delivery.api.domain.userorder.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.db.userorder.UserOrderEntity;
import org.delivery.db.userorder.UserOrderRepository;
import org.delivery.db.userorder.enums.UserOrderStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class UserOrderService {

    private final UserOrderRepository userOrderRepository;



    // 주문 생성

    public UserOrderEntity order(UserOrderEntity entity){
        return Optional.ofNullable(entity)
                .map(it -> {
                    it.setStatus(UserOrderStatus.ORDER);
                    it.setOrderedAt(LocalDateTime.now());

                    log.info("it : {}", it);
                    return userOrderRepository.save(it);
                }).orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }

    // 상태 변경
    public UserOrderEntity setStatus(UserOrderEntity entity, UserOrderStatus status){
        entity.setStatus(status);
        return userOrderRepository.save(entity);
    }

    // 주문 확인

    public UserOrderEntity accept(UserOrderEntity entity){
        entity.setAcceptedAt(LocalDateTime.now());
        return setStatus(entity, UserOrderStatus.ACCEPT);
    }

    // 조리 시작

    public UserOrderEntity cooking(UserOrderEntity entity){
        entity.setCookingStartedAt(LocalDateTime.now());
        return setStatus(entity, UserOrderStatus.COOKING);
    }

    // 배달 시작

    public UserOrderEntity delivery(UserOrderEntity entity){
        entity.setDeliveryStartedAt(LocalDateTime.now());
        return setStatus(entity, UserOrderStatus.DELIVERY);
    }

    // 배달 완료

    public UserOrderEntity receive(UserOrderEntity entity){
        entity.setReceivedAt(LocalDateTime.now());
        return setStatus(entity, UserOrderStatus.RECEIVE);
    }


    public UserOrderEntity getUserOrderWithOutStatusThrow(Long id, Long userId){
        return userOrderRepository.findFirstByIdAndUserId(id, userId)
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }



    public UserOrderEntity getUserWithThrow(Long id, Long userId){
        return userOrderRepository.findFirstByIdAndStatusAndUserId(id, UserOrderStatus.REGISTERED, userId)
                .orElseThrow(() -> new ApiException(ErrorCode.NULL_POINT));
    }



    public List<UserOrderEntity> getAllUserOrderList(Long userId){
        return userOrderRepository.findAllByUserIdAndStatusOrderByIdDesc(userId, UserOrderStatus.REGISTERED);
    }



    public List<UserOrderEntity> getAllUserOrderListByStatues(Long userId, List<UserOrderStatus> statuses){
        return userOrderRepository.findAllByUserIdAndStatusInOrderByIdDesc(userId, statuses);
    }


    // 현재 진행 중인 내역

    public List<UserOrderEntity> current(Long id){
        return userOrderRepository.findAllByUserIdAndStatusInOrderByIdDesc(id,
                List.of(UserOrderStatus.ORDER,
                        UserOrderStatus.ACCEPT,
                        UserOrderStatus.COOKING,
                        UserOrderStatus.DELIVERY));
    }


    // 과거 내역
    public List<UserOrderEntity> history(Long id){
        return userOrderRepository.findAllByUserIdAndStatusOrderByIdDesc(id, UserOrderStatus.RECEIVE);
    }


}
