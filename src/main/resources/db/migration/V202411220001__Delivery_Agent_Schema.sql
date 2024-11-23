CREATE TABLE delivery_agents (
    id BIGINT AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    mobile_number VARCHAR(10) NOT NULL,
    vehicle_type VARCHAR(100) NOT NULL,
    restaurant_id BIGINT NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT true,
    is_working BOOLEAN NOT NULL DEFAULT false,
    is_deleted BOOLEAN NOT NULL DEFAULT false,
    is_archived BOOLEAN NOT NULL DEFAULT false,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`),
    KEY `idx_delivery_agents_restaurant_id` (`restaurant_id`)
);