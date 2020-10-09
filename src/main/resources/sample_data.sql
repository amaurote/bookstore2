INSERT INTO bookstore.users (id, active, email, enabled, password, username)
VALUES (123456, true, 'hubert@test.sk', true, '$2a$10$TAw7f5I/2b1ksHY5Og43s.zw7bvNyrqWLI/GKIcwU7ft2ZDOZhhXa', 'hubert')

INSERT INTO bookstore.users_roles (user_id, role_id) VALUES ('123456', 2)

INSERT INTO bookstore.users (id, active, email, enabled, password, username)
VALUES (654321, true, 'jeremy_fisher@test.sk', true, '$2a$10$TAw7f5I/2b1ksHY5Og43s.zw7bvNyrqWLI/GKIcwU7ft2ZDOZhhXa', 'jeremy')

INSERT INTO bookstore.users_roles (user_id, role_id) VALUES ('654321', 2)