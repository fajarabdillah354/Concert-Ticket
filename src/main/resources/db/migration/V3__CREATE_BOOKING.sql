CREATE TABLE booking (
    id VARCHAR(36) PRIMARY KEY,

    user_id VARCHAR(36) NOT NULL,
    concert_id VARCHAR(36) NOT NULL,
    category_id VARCHAR(36) NOT NULL,

    quantity INTEGER NOT NULL,
    total_price NUMERIC(19,2) NOT NULL,

    status VARCHAR(50) NOT NULL,


    expires_at DATETIME(6),
    created_at DATETIME(6) NOT NULL,

    CONSTRAINT fk_booking_concert
    FOREIGN KEY (concert_id) REFERENCES concert(id)
);

CREATE INDEX idx_booking_concert_id ON booking(concert_id);
CREATE INDEX idx_booking_user_id ON booking(user_id);