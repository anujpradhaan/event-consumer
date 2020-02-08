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

	@RabbitListener(queues = { "fanout-queue1" })
	public void receiveMessageFromQueue1(Order order, Message message) {
		log.info("Queue {} Received on Message {}", message.getMessageProperties().getConsumerQueue(), order);
	}

	@RabbitListener(queues = { "fanout-queue2" })
	public void receiveMessageFromQueue2(Order order, Message message) {
		log.info("Queue {} Received on Message {}", message.getMessageProperties().getConsumerQueue(), order);
	}

	@RabbitListener(queues = { "fanout-queue3" })
	public void receiveMessageFromQueue3(Order order, Message message) {
		log.info("Queue {} Received on Message {}", message.getMessageProperties().getConsumerQueue(), order);
	}
}
