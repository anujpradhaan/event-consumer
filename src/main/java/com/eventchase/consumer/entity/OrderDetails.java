package com.eventchase.consumer.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Data
@Builder
public class OrderDetails {

	@Id
	private String orderId;
	private String userEmail;
	private List<String> products;

}
