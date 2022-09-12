CREATE TABLE IF NOT EXISTS shops
(
    id            UUID                     DEFAULT gen_random_uuid() NOT NULL
        PRIMARY KEY,
    name          VARCHAR(255)                                       NOT NULL
        UNIQUE,
    zipcode       VARCHAR(7)                                         NOT NULL,
    prefecture    INTEGER                                            NOT NULL,
    locality      VARCHAR(255)                                       NOT NULL,
    street        VARCHAR(255)                                       NOT NULL,
    building      VARCHAR(255),
    has_parking   BOOLEAN                  DEFAULT FALSE             NOT NULL,
    non_smoking   BOOLEAN                  DEFAULT TRUE              NOT NULL,
    image_url     VARCHAR(255)                                       NOT NULL,
    registerer_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL
);

