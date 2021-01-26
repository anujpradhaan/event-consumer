package com.eventchase.consumer;

import com.eventchase.consumer.entity.OrderDetails;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@Data
public class OrderDetailsFactory {
	public OrderDetails getOrderDetails(Order order) {
		return OrderDetails.builder()
				.orderId(order.getOrderId())
				.userEmail(order.getRecipient().getBasicProfile().getEmail())
				.products(order.getProducts().stream().map(Order.Product::getName).collect(Collectors.toList()))
				.build();
	}
}
