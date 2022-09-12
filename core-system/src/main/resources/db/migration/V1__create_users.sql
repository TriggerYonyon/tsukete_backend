CREATE TABLE IF NOT EXISTS users
(
    id           UUID                     DEFAULT gen_random_uuid() NOT NULL
        PRIMARY KEY,
    first_name   VARCHAR(255)                                       NOT NULL,
    last_name    VARCHAR(255)                                       NOT NULL,
    nick_name    VARCHAR(255)                                       NOT NULL,
    email        VARCHAR(255)                                       NOT NULL
        UNIQUE,
    password     VARCHAR(255)                                       NOT NULL,
    registered_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL
);
