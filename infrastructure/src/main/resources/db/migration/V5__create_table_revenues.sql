CREATE TABLE revenues (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    parking_session_id BIGINT NOT NULL UNIQUE,
    sector VARCHAR(10) NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    currency VARCHAR(3) DEFAULT 'BRL',
    transaction_date DATE NOT NULL,
    transaction_timestamp TIMESTAMP NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (parking_session_id) REFERENCES parking_sessions(id),
    FOREIGN KEY (sector) REFERENCES garages(sector),

    INDEX idx_sector_date (sector, transaction_date),
    INDEX idx_transaction_date (transaction_date),
    INDEX idx_transaction_timestamp (transaction_timestamp)
);