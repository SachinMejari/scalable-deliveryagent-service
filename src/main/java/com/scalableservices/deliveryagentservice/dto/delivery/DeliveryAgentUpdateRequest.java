package com.scalableservices.deliveryagentservice.dto.delivery;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryAgentUpdateRequest {
    private Long id;
    private String vehicleType;
    private Long restaurantId;
    private Boolean isActive;
    private Boolean isWorking;
}
