package com.scalableservices.deliveryagentservice.service;
import com.scalableservices.deliveryagentservice.dto.delivery.DeliveryAgentRequest;
import com.scalableservices.deliveryagentservice.dto.delivery.DeliveryAgentResponse;
import com.scalableservices.deliveryagentservice.dto.delivery.DeliveryAgentUpdateRequest;
import com.scalableservices.deliveryagentservice.enums.VehicleTypes;
import com.scalableservices.deliveryagentservice.exception.ServiceException;
import com.scalableservices.deliveryagentservice.model.DeliveryAgent;
import com.scalableservices.deliveryagentservice.repository.DeliveryAgentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DeliveryAgentService {

    @Autowired
    private DeliveryAgentRepository deliveryAgentRepository;

    public DeliveryAgentResponse addDeliveryAgent(DeliveryAgentRequest deliveryAgent) {
        try{
            DeliveryAgent existingAgent = deliveryAgentRepository.findByMobileNumber(deliveryAgent.getMobileNumber());
            if (existingAgent != null) {
                log.error("Delivery agent with mobile number {} already exists", deliveryAgent.getMobileNumber());
                throw new ServiceException(HttpStatus.BAD_REQUEST,"Delivery agent with mobile number " + deliveryAgent.getMobileNumber() + " already exists");
            }
            // Add delivery agent to database
            DeliveryAgent delAgent = DeliveryAgent.builder()
                    .name(deliveryAgent.getName())
                    .mobileNumber(deliveryAgent.getMobileNumber())
                    .vehicleType(VehicleTypes.valueOf(deliveryAgent.getVehicleType().toUpperCase()))
                    .restaurantId(deliveryAgent.getRestaurantId())
                    .isActive(true)
                    .isWorking(true)
                    .isDeleted(false)
                    .isArchived(false)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();
            deliveryAgentRepository.save(delAgent);
            log.info("Delivery agent added successfully with mobile number: {}", deliveryAgent.getMobileNumber());
            delAgent = deliveryAgentRepository.findByMobileNumber(deliveryAgent.getMobileNumber());
            if(delAgent == null) {
                log.error("Error while fetching delivery agent with mobile number: {}", deliveryAgent.getMobileNumber());
                throw new ServiceException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while fetching delivery agent with mobile number: " + deliveryAgent.getMobileNumber());
            }
            return DeliveryAgentResponse.builder()
                    .agentId(delAgent.getId())
                    .name(delAgent.getName())
                    .mobileNo(delAgent.getMobileNumber())
                    .vehicleType(deliveryAgent.getVehicleType())
                    .restaurantId(deliveryAgent.getRestaurantId())
                    .status("ACTIVE")
                    .isActive(true)
                    .isWorking(true)
                    .build();
        } catch (IllegalArgumentException e) {
            // Handle the case where the provided vehicleType is invalid
            throw new ServiceException(HttpStatus.BAD_REQUEST,"Invalid vehicle type: " + deliveryAgent.getVehicleType());
        }
        catch (ServiceException e) {
            log.error("Error while adding delivery agent: {}", e.getMessage());
            throw e;
        }
        catch (Exception e) {
            log.error("Error while adding delivery agent: {}", e.getMessage());
            throw e;
        }
    }

    public DeliveryAgentResponse updateDeliveryAgent(DeliveryAgentUpdateRequest deliveryAgent) {
        try {
            DeliveryAgent existingAgent = deliveryAgentRepository.findById(deliveryAgent.getId()).orElse(null);
            if (existingAgent == null) {
                log.error("Delivery agent with id {} not found", deliveryAgent.getId());
                throw new ServiceException(HttpStatus.BAD_REQUEST,"Delivery agent with id " + deliveryAgent.getId() + " not found");
            }
            existingAgent.setRestaurantId(deliveryAgent.getRestaurantId());
            existingAgent.setVehicleType(VehicleTypes.valueOf(deliveryAgent.getVehicleType().toUpperCase()));
            existingAgent.setIsActive(deliveryAgent.getIsActive());
            existingAgent.setIsWorking(deliveryAgent.getIsWorking());
            existingAgent.setUpdatedAt(LocalDateTime.now());
            deliveryAgentRepository.save(existingAgent);
            log.info("Delivery agent updated successfully with id: {}", deliveryAgent.getId());
            return DeliveryAgentResponse.builder()
                    .agentId(existingAgent.getId())
                    .name(existingAgent.getName())
                    .mobileNo(existingAgent.getMobileNumber())
                    .vehicleType(deliveryAgent.getVehicleType())
                    .restaurantId(existingAgent.getRestaurantId())
                    .status(existingAgent.getIsActive() ? "ACTIVE" : "INACTIVE")
                    .isActive(existingAgent.getIsActive())
                    .isWorking(existingAgent.getIsWorking())
                    .build();
        } catch (IllegalArgumentException e) {
            // Handle the case where the provided vehicleType is invalid
            throw new ServiceException(HttpStatus.BAD_REQUEST,"Invalid vehicle type: " + deliveryAgent.getVehicleType());
        }
        catch (ServiceException e) {
            log.error("Error while updating delivery agent: {}", e.getMessage());
            throw e;
        }
        catch (Exception e) {
            log.error("Error while updating delivery agent: {}", e.getMessage());
            throw e;
        }
    }
}
