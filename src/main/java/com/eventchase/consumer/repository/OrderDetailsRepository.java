package com.eventchase.consumer.repository;

import com.eventchase.consumer.entity.OrderDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailsRepository extends MongoRepository<OrderDetails, String> {
}
