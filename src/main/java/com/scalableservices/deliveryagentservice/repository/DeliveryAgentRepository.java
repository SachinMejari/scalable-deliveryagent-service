package com.scalableservices.deliveryagentservice.repository;

import com.scalableservices.deliveryagentservice.model.DeliveryAgent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryAgentRepository extends JpaRepository<DeliveryAgent, Long> {
    //@Query("SELECT d FROM DeliveryAgent d WHERE d.mobileNumber = :mobileNo")
    DeliveryAgent findByMobileNumber(String mobileNo);
}
