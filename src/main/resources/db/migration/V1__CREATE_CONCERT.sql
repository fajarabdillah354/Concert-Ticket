CREATE TABLE concert (


    id VARCHAR(36) PRIMARY KEY,

    name VARCHAR(255) NOT NULL,
    artist VARCHAR(255) NOT NULL,
    venue VARCHAR(255) NOT NULL,

    start_time DATETIME(6) NOT NULL,
    end_time DATETIME(6) NOT NULL,

    base_price NUMERIC(19,2) NOT NULL,
    capacity INTEGER NOT NULL,

    status VARCHAR(50) NOT NULL,
    created_at DATETIME(6) NOT NULL
);