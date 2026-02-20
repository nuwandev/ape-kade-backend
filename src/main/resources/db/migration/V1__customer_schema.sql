CREATE TABLE customer
(
    id          VARCHAR(255) PRIMARY KEY,
    title       VARCHAR(50),
    name        VARCHAR(255) NOT NULL,
    dob         DATE,
    salary      DOUBLE,
    address     VARCHAR(255),
    city        VARCHAR(100),
    province    VARCHAR(100),
    postal_code VARCHAR(20),
    created_at  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
