CREATE TABLE ticket_inventory (
    id VARCHAR(36) PRIMARY KEY,

    concert_id VARCHAR(36) NOT NULL,
    category_id VARCHAR(36) NOT NULL,

    total_stock INT NOT NULL,
    available_stock INT NOT NULL,

    version BIGINT NOT NULL DEFAULT 0,

    CONSTRAINT chk_stock_positive CHECK (total_stock >= 0),
    CONSTRAINT chk_available_stock_positive CHECK (available_stock >= 0),
    CONSTRAINT chk_available_not_exceed_total CHECK (available_stock <= total_stock),

    UNIQUE KEY uk_concert_category (concert_id, category_id)
);