INSERT INTO concert (
    id,
    name,
    artist,
    venue,
    start_time,
    end_time,
    base_price,
    capacity,
    status,
    created_at
) VALUES (
    '11111111-1111-1111-1111-111111111111',
    'Coldplay Live Jakarta',
    'Coldplay',
    'Gelora Bung Karno',
    '2026-06-01 12:00:00',
    '2026-06-01 15:00:00',
    250000.00,
    100,
    'UPCOMING',
    NOW()
);