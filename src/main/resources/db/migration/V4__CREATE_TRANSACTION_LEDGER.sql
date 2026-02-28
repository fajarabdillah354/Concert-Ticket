CREATE TABLE transaction_ledger (
    id VARCHAR(36) PRIMARY KEY,

    booking_id VARCHAR(36) NOT NULL,

    type VARCHAR(20) NOT NULL,
    amount NUMERIC(19,2) NOT NULL,

    status VARCHAR(50) NOT NULL,

    created_at DATETIME(6) NOT NULL,

    CONSTRAINT fk_transaction_booking
    FOREIGN KEY (booking_id) REFERENCES booking(id),

    CONSTRAINT chk_transaction_type
    CHECK (type IN ('PAYMENT', 'REFUND')),

    CONSTRAINT chk_transaction_amount_positive
    CHECK (amount > 0),

    CONSTRAINT chk_transaction_status
    CHECK (status IN ('SUCCESS', 'FAILED', 'PENDING'))
);

CREATE INDEX idx_transaction_ledger_booking_id
ON transaction_ledger(booking_id);