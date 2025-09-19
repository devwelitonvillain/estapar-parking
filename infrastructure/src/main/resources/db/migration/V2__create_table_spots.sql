CREATE TABLE spots (
    id BIGINT PRIMARY KEY,
    sector VARCHAR(10) NOT NULL,
    latitude DECIMAL(10,8) NOT NULL,
    longitude DECIMAL(11,8) NOT NULL,
    is_occupied BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (sector) REFERENCES garages(sector),
    INDEX idx_sector_occupied (sector, is_occupied),
    INDEX idx_coordinates (latitude, longitude)
);