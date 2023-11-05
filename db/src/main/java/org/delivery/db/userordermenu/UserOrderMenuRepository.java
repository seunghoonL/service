package org.delivery.db.userordermenu;

import org.delivery.db.userordermenu.enums.UserOrderMenuStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserOrderMenuRepository extends JpaRepository<UserOrderMenuEntity, Long> {

    // 사용자 주문에 담긴 메뉴 가져오기  select * from user_order_menu where user_order_id = ? and status = ? ;
    List<UserOrderMenuEntity> findAllByUserOrderIdAndStatus(Long id, UserOrderMenuStatus status);
}
