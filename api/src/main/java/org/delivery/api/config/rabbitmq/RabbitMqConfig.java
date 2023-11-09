package org.delivery.api.config.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    // exchange
    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange("delivery.exchange");
    }


    // queue
    @Bean
    public Queue queue(){
        return new Queue("delivery.queue");
    }


    // exchange <-> queue binding
    @Bean
    public Binding binding(DirectExchange directExchange, Queue queue){
        return BindingBuilder.bind(queue).to(directExchange).with("delivery.key");
    }


    @Bean  // 데이터 주 전송 장치
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter){
        var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter); // json 직렬화에 필요한 메세지 컨버터 설정

        return rabbitTemplate;
    }


    @Bean
    public MessageConverter messageConverter(ObjectMapper objectMapper){
        return new Jackson2JsonMessageConverter(objectMapper);
    }
}
