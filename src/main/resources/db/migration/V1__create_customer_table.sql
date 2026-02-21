CREATE TABLE customer
(
    id          BINARY(16) PRIMARY KEY,
    title       VARCHAR(50),
    name        VARCHAR(255) NOT NULL,
    dob         DATE,
    salary      DECIMAL(12, 2),
    address     VARCHAR(255),
    city        VARCHAR(100),
    province    VARCHAR(100),
    postal_code VARCHAR(20),
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);