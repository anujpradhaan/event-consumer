package com.eventchase.consumer.listener;

import com.eventchase.consumer.RequestDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = { "direct-queue" })
@Slf4j
@AllArgsConstructor
public class DirectExchangeListener {
	private final DirectExchange directExchange;

	@RabbitHandler
	public void receiveMessage(RequestDTO requestDTO) {
		log.info("Received Message {}", requestDTO);
	}
}
