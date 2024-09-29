ALTER TABLE user_table
    ADD COLUMN email character varying(100);

INSERT INTO user_table(uuid, username, password, email, user_role) values ('uuid-admin', 'admin', 'admin', 'admin@gmail.com', '0');