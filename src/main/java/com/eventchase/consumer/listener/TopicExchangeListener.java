package com.eventchase.consumer.listener;

import com.eventchase.consumer.Order;
import com.eventchase.consumer.OrderDetailsFactory;
import com.eventchase.consumer.repository.OrderDetailsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class TopicExchangeListener {
	private final OrderDetailsRepository orderDetailsRepository;
	private final OrderDetailsFactory orderDetailsFactory;

	@RabbitListener(queues = { "topic-queue" })
	public void receiveMessage(Order order, Message message) {
		log.debug("{}", message);
		log.info("Queue {} Received on Message {}", message.getMessageProperties().getConsumerQueue(), order);
		orderDetailsRepository.save(orderDetailsFactory.getOrderDetails(order));
	}
}
