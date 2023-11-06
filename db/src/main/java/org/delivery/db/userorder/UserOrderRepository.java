package org.delivery.db.userorder;

import org.delivery.db.userorder.enums.UserOrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserOrderRepository extends JpaRepository<UserOrderEntity, Long> {



    // 유저 모든 주문 조회 select * from user_order where user_id = ? and status = ? order by id desc;
    List<UserOrderEntity> findAllByUserIdAndStatusOrderByIdDesc(Long userId, UserOrderStatus status);

    // select * from user_order where user_id = ? and status in (?,?...) order by id desc;
    List<UserOrderEntity> findAllByUserIdAndStatusInOrderByIdDesc(Long userId, List<UserOrderStatus> statuses);



    // 특정 주문 조회 select * from user_order where id = ? and status = ? and user_id = ?;
    Optional<UserOrderEntity> findFirstByIdAndStatusAndUserId(Long Id, UserOrderStatus status, Long userId);


    Optional<UserOrderEntity> findFirstByIdAndUserId(Long id, Long userId);
}
