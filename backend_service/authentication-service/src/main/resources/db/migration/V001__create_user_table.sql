CREATE TABLE IF NOT EXISTS user_table
(
    id
    bigserial,
    uuid
    character
    varying
(
    100
),
    username character varying
(
    100
),
    password character varying
(
    100
),
    user_role character varying
(
    100
),
    CONSTRAINT users_pkey PRIMARY KEY
(
    id
)

    );