package org.delivery.storeadmin.domain.userorder.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.delivery.common.message.model.UserOrderMessage;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserOrderConsumer {

    // 어떤 queue 로 부터 받을 지
    @RabbitListener(queues = "delivery.queue")
    public void orderConsumer(UserOrderMessage userOrderMessage){
        log.info("receive message queue : {} ", userOrderMessage);
    }

}
