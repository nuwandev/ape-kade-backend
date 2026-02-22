CREATE TABLE item
(
    id            BINARY(16) PRIMARY KEY,
    sku           VARCHAR(50)    NOT NULL,
    name          VARCHAR(200)   NOT NULL,
    description   TEXT,
    price         DECIMAL(12, 2) NOT NULL DEFAULT 0.00,
    current_stock INT            NOT NULL DEFAULT 0,
    alert_level   INT            NOT NULL DEFAULT 5,
    category_id   BINARY(16)     NOT NULL,
    created_at    TIMESTAMP               DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP               DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_item_sku (sku),
    CONSTRAINT fk_item_category FOREIGN KEY (category_id) REFERENCES category (id)
) ENGINE = InnoDB;

-- Indexes for the Search Bar (Search by name or SKU)
CREATE INDEX idx_item_search ON item (name, sku);