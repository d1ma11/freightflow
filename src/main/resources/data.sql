-- Initial data for FreightFlow

-- Cargo entries
INSERT INTO cargo (name, weight_kg, volume_cubic_meters, type) VALUES
('Электроника', 500.00, 2.500, 'GENERAL'),
('Стеклянные изделия', 300.00, 1.800, 'FRAGILE'),
('Мороженые продукты', 1200.00, 5.000, 'REFRIGERATED'),
('Химические вещества', 800.00, 3.200, 'DANGEROUS'),
('Мебель', 1500.00, 10.000, 'GENERAL');

-- Carrier entries
INSERT INTO carrier (name, contact_person, phone, transport_type, capacity_kg) VALUES
('ТрансЛогистик', 'Иван Петров', '+7-495-123-4567', 'TRUCK', 5000.00),
('Быстрая Доставка', 'Анна Сидорова', '+7-812-987-6543', 'VAN', 1500.00),
('ХолодТранс', 'Сергей Кузнецов', '+7-903-555-0101', 'REFRIGERATOR', 3000.00),
('КонтейнерСервис', 'Ольга Морозова', '+7-926-777-8899', 'CONTAINER', 20000.00);

-- Shipment entries
INSERT INTO shipment (cargo_id, carrier_id, origin, destination, scheduled_departure, status) VALUES
(1, 1, 'Москва', 'Санкт-Петербург', '2024-06-01 08:00:00', 'CREATED'),
(2, 2, 'Санкт-Петербург', 'Москва', '2024-06-02 10:00:00', 'IN_PROGRESS'),
(3, 3, 'Екатеринбург', 'Новосибирск', '2024-06-03 14:00:00', 'CREATED'),
(4, 4, 'Владивосток', 'Москва', '2024-06-05 09:00:00', 'CREATED');
