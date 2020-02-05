package com.eventchase.consumer.listener;

import com.eventchase.consumer.RequestDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = { "fanout-queue" })
@AllArgsConstructor
@Slf4j
public class FanoutExchangeListener {
	private final FanoutExchange fanoutExchange;

	@RabbitHandler
	public void receiveMessage(RequestDTO requestDTO) {
		log.info("Received {}", requestDTO);
	}
}
