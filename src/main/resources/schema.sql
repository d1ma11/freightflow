-- Schema initialization for FreightFlow

CREATE TABLE IF NOT EXISTS cargo (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    weight_kg DECIMAL(10, 2) NOT NULL,
    volume_cubic_meters DECIMAL(10, 3) NOT NULL,
    type VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS carrier (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    contact_person VARCHAR(255),
    phone VARCHAR(50),
    transport_type VARCHAR(50) NOT NULL,
    capacity_kg DECIMAL(10, 2) NOT NULL
);

CREATE TABLE IF NOT EXISTS shipment (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cargo_id BIGINT NOT NULL,
    carrier_id BIGINT NOT NULL,
    origin VARCHAR(255) NOT NULL,
    destination VARCHAR(255) NOT NULL,
    scheduled_departure TIMESTAMP NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (cargo_id) REFERENCES cargo(id),
    FOREIGN KEY (carrier_id) REFERENCES carrier(id)
);
