package com.eventchase.consumer;

import lombok.Data;

import java.util.List;

@Data
public class Order {
	private String orderId;
	private List<Product> products;
	private Recipient recipient;

	@Data
	private static class Product {
		private String name;
		private Double price;
	}

	@Data
	private static class Recipient {
		private BasicUserProfile basicProfile;
		private List<Address> addresses;

		@Data
		private static class BasicUserProfile {
			private String name;
			private String phoneNumber;
			private String email;
		}

		@Data
		private static class Address {
			private AddressType addressType;
			private String line1;
			private String line2;
			private String city;
			private String state;
			private String country;

			private enum AddressType {
				BILLING, DELIVERY
			}
		}
	}

}
