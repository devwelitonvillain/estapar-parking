CREATE TABLE parking_sessions (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    vehicle_id BIGINT NOT NULL,
    spot_id BIGINT,
    sector VARCHAR(10) NULL,
    entry_time TIMESTAMP NOT NULL,
    parked_time TIMESTAMP NULL,
    exit_time TIMESTAMP NULL,
    dynamic_price_rate DECIMAL(10,2) NOT NULL,
    final_amount DECIMAL(10,2) NULL,
    status ENUM('ENTERED', 'PARKED', 'COMPLETED') DEFAULT 'ENTERED',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (vehicle_id) REFERENCES vehicles(id),
    FOREIGN KEY (spot_id) REFERENCES spots(id),
    FOREIGN KEY (sector) REFERENCES garages(sector),

    INDEX idx_vehicle_status (vehicle_id, status),
    INDEX idx_sector_entry_time (sector, entry_time),
    INDEX idx_status (status),
    INDEX idx_entry_time (entry_time),
    INDEX idx_exit_time (exit_time)
);