-- init products
INSERT INTO product (name, price, stock) VALUES
                    ('prodA', 999.99, 10),
                    ('prodC', 899.99, 15),
                    ('prodE', 799.99, 8),
                    ('prodG', 699.99, 12),
                    ('prodK', 599.99, 20);
-- init users
INSERT INTO app_users (username, password) VALUES
                                               ('admin', 'admin'),
                                               ('user', 'user');