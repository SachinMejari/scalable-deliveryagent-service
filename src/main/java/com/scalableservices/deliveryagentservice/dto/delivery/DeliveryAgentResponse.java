package com.scalableservices.deliveryagentservice.dto.delivery;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryAgentResponse {
    private Long agentId;
    private String name;
    private String mobileNo;
    private String vehicleType;
    private Long restaurantId;
    private String status;
    private Boolean isActive;
    private Boolean isWorking;
}
