package com.eventchase.consumer;

import com.eventchase.consumer.entity.OrderDetails;
import com.eventchase.consumer.repository.OrderDetailsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/order-details")
@Slf4j
@AllArgsConstructor
public class OrderDetailsController {

	private final OrderDetailsRepository orderDetailsRepository;

	@GetMapping("/all")
	public List<OrderDetails> getAllOrderDetails() {
		log.info("Fetching all order details");
		return orderDetailsRepository.findAll();
	}

	@GetMapping("/{orderId}")
	public OrderDetails getOrderDetailsByOrderId(@PathVariable String orderId) throws Exception {
		log.info("Fetching orderDetails for orderId={}", orderId);
		return orderDetailsRepository.findById(orderId)
				.orElseThrow(() -> new Exception("Not Found"));
	}

	@PostMapping
	public OrderDetails addOrderDetails(@RequestBody Order order) {
		log.info("Saving order details for order {}", order);
		OrderDetails orderDetails = OrderDetails.builder()
				.orderId(order.getOrderId())
				.userEmail(order.getRecipient().getBasicProfile().getEmail())
				.products(order.getProducts().stream().map(Order.Product::getName).collect(Collectors.toList()))
				.build();
		return orderDetailsRepository.save(orderDetails);
	}
}
