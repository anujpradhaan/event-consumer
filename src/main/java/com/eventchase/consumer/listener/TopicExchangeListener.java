package com.eventchase.consumer.listener;

import com.eventchase.consumer.RequestDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = { "topic-queue" })
@Slf4j
@AllArgsConstructor
public class TopicExchangeListener {

	private final TopicExchange topicExchange;

	@RabbitHandler
	public void receiveMessage(RequestDTO requestDTO) {
		log.info("Received {}", requestDTO);
	}
}
