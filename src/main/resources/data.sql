INSERT INTO roles (name, description) VALUES
('ADMIN', 'Administrator role'),
('OWNER', 'Owner role'),
('EMPLOYEE', 'Employee role'),
('CUSTOMER', 'Customer role')
ON CONFLICT (name) DO NOTHING;

-- Insert users
-- 1 Admin (password: admin123)
INSERT INTO users (name, last_name, document_id, phone_number, birth_date, email, password, rol_id, restaurant_id) VALUES
('Admin', 'User', '1234567890', '+573001234567', '1990-01-01', 'admin@plazoleta.com', '$2a$10$1Ku0ywEoSKOdH2TE6jdRSenxvOCU9W7c2fIXyGj61B.TiYiuiD3Ya', 1, NULL)
ON CONFLICT (email) DO NOTHING;

-- 3 Owners (password: owner123)
INSERT INTO users (name, last_name, document_id, phone_number, birth_date, email, password, rol_id, restaurant_id) VALUES
('Owner', 'One', '2345678901', '+573012345678', '1991-02-02', 'owner1@plazoleta.com', '$2a$10$0.tZUldTduFoVyiExAyiiuJ5koOrugWdUezgQbVqg.lXnjKi1cehO', 2, NULL),
('Owner', 'Two', '3456789012', '+573023456789', '1992-03-03', 'owner2@plazoleta.com', '$2a$10$0.tZUldTduFoVyiExAyiiuJ5koOrugWdUezgQbVqg.lXnjKi1cehO', 2, NULL),
('Owner', 'Three', '4567890123', '+573034567890', '1993-04-04', 'owner3@plazoleta.com', '$2a$10$0.tZUldTduFoVyiExAyiiuJ5koOrugWdUezgQbVqg.lXnjKi1cehO', 2, NULL)
ON CONFLICT (email) DO NOTHING;

-- 4 Employees (password: employee123) - Assigned to different restaurants
INSERT INTO users (name, last_name, document_id, phone_number, birth_date, email, password, rol_id, restaurant_id) VALUES
('Employee', 'One', '5678901234', '+573109876543', '1994-05-05', 'employee1@plazoleta.com', '$2a$10$hVnaZfFOGYq/lxYZ8OKVy.6GL/tWmK3pQc0.HhUXcPbxog.z092oG', 3, 1),
('Employee', 'Two', '6789012345', '+573118765432', '1995-06-06', 'employee2@plazoleta.com', '$2a$10$hVnaZfFOGYq/lxYZ8OKVy.6GL/tWmK3pQc0.HhUXcPbxog.z092oG', 3, 1),
('Employee', 'Three', '7890123456', '+573127654321', '1996-07-07', 'employee3@plazoleta.com', '$2a$10$hVnaZfFOGYq/lxYZ8OKVy.6GL/tWmK3pQc0.HhUXcPbxog.z092oG', 3, 2),
('Employee', 'Four', '8901234567', '+573136543210', '1997-08-08', 'employee4@plazoleta.com', '$2a$10$hVnaZfFOGYq/lxYZ8OKVy.6GL/tWmK3pQc0.HhUXcPbxog.z092oG', 3, 2)
ON CONFLICT (email) DO NOTHING;

-- 4 Customers (password: customer123)
INSERT INTO users (name, last_name, document_id, phone_number, birth_date, email, password, rol_id, restaurant_id) VALUES
('Customer', 'One', '9012345678', '+573201234567', '1998-09-09', 'customer1@plazoleta.com', '$2a$10$ha.GTLJ1KANaxGnqF./QYOwhncRfqPKXiRRfqusWhmu5iEyvwl3Dq', 4, NULL),
('Customer', 'Two', '0123456789', '+573212345678', '1999-10-10', 'customer2@plazoleta.com', '$2a$10$ha.GTLJ1KANaxGnqF./QYOwhncRfqPKXiRRfqusWhmu5iEyvwl3Dq', 4, NULL),
('Customer', 'Three', '1122334455', '+573223456789', '2000-11-11', 'customer3@plazoleta.com', '$2a$10$ha.GTLJ1KANaxGnqF./QYOwhncRfqPKXiRRfqusWhmu5iEyvwl3Dq', 4, NULL),
('Customer', 'Four', '2233445566', '+573234567890', '2001-12-12', 'customer4@plazoleta.com', '$2a$10$ha.GTLJ1KANaxGnqF./QYOwhncRfqPKXiRRfqusWhmu5iEyvwl3Dq', 4, NULL)
ON CONFLICT (email) DO NOTHING;
