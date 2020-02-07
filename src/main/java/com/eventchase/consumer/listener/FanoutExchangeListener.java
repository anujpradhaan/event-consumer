package com.eventchase.consumer.listener;

import com.eventchase.consumer.Order;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component

@AllArgsConstructor
@Slf4j
public class FanoutExchangeListener {

	@RabbitListener(queues = { "fanout-queue" })
	public void receiveMessageFromQueue1(Order order, Message message) {
		log.info("Received Message {} from queue {}", order, message.getMessageProperties().getConsumerQueue());
	}
}
