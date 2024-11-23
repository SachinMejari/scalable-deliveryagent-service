package com.scalableservices.deliveryagentservice.controller;

import com.scalableservices.deliveryagentservice.dto.common.ApiResponse;
import com.scalableservices.deliveryagentservice.dto.common.ErrorMessage;
import com.scalableservices.deliveryagentservice.dto.delivery.DeliveryAgentRequest;
import com.scalableservices.deliveryagentservice.dto.delivery.DeliveryAgentResponse;
import com.scalableservices.deliveryagentservice.dto.delivery.DeliveryAgentUpdateRequest;
import com.scalableservices.deliveryagentservice.exception.ServiceException;
import com.scalableservices.deliveryagentservice.service.DeliveryAgentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/delivery-agent")
public class DeliveryAgentController {
    @Autowired
    private DeliveryAgentService deliveryAgentService;

    @PostMapping("/register")
    public ApiResponse<DeliveryAgentResponse> addDeliveryAgent(@RequestBody DeliveryAgentRequest deliveryAgent) {
        try {
            DeliveryAgentResponse response = deliveryAgentService.addDeliveryAgent(deliveryAgent);
            return ApiResponse.<DeliveryAgentResponse>builder().status("success").data(response).build();
        } catch (ServiceException e) {
            log.error("ServiceException occured while adding delivery agent with mobile number: {}", deliveryAgent.getMobileNumber(), e);
            return ApiResponse.<DeliveryAgentResponse>builder().status("failed")
                    .error(ErrorMessage.builder().error("Error while create delivery agent master").description(e.getMessage()).build())
                    .build();
        }
        catch (Exception e) {
            log.error("Exception occured while adding delivery agent with mobile number: {}", deliveryAgent.getMobileNumber(), e);
            return ApiResponse.<DeliveryAgentResponse>builder().status("failed").error(
                    ErrorMessage.builder().error("Error while create delivery agent master").description(e.getMessage()).build()
            ).build();
        }
    }
    @PostMapping("/{id}/update")
    public ApiResponse<DeliveryAgentResponse> updateDeliveryAgent(@RequestBody DeliveryAgentUpdateRequest deliveryAgent, @PathVariable("id") Long id
            , @RequestHeader(value = "X-UserType", required = true) String userType) {
        try {
            if(!userType.equalsIgnoreCase("delivery_agent")) {
                throw new ServiceException(HttpStatus.UNAUTHORIZED, "Only delivery agents are allowed to update their details");
            }
            DeliveryAgentResponse response = deliveryAgentService.updateDeliveryAgent(deliveryAgent);
            return ApiResponse.<DeliveryAgentResponse>builder().status("success").data(response).build();
        } catch (ServiceException e) {
            log.error("ServiceException occured while updating delivery agent with id: {}", deliveryAgent.getId(), e);
            return ApiResponse.<DeliveryAgentResponse>builder().status("failed")
                    .error(ErrorMessage.builder().error("Error while updating delivery agent master").description(e.getMessage()).build())
                    .build();
        }
        catch (Exception e) {
            log.error("Exception occured while updating delivery agent with id: {}", deliveryAgent.getId(), e);
            return ApiResponse.<DeliveryAgentResponse>builder().status("failed").error(
                    ErrorMessage.builder().error("Error while updating delivery agent master").description(e.getMessage()).build()
            ).build();
        }
    }
}
