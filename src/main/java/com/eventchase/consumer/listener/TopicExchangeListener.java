package com.eventchase.consumer.listener;

import com.eventchase.consumer.Order;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class TopicExchangeListener {

	@RabbitListener(queues = { "topic-queue" })
	public void receiveMessage(Order order, Message message) {
		log.info("Received Message {} from queue {}", order, message.getMessageProperties().getConsumerQueue());
	}
}
