CREATE TABLE IF NOT EXISTS seats
(
    id                      UUID                     DEFAULT gen_random_uuid() NOT NULL
        PRIMARY KEY,
    shop_id                 UUID                                               NOT NULL
        CONSTRAINT seats_shops_id_fk
            REFERENCES shops,
    type                    INTEGER                                            NOT NULL,
    capacity                INTEGER                                            NOT NULL,
    has_outlet              BOOLEAN                  DEFAULT FALSE             NOT NULL,
    is_near_air_conditioner BOOLEAN                  DEFAULT FALSE             NOT NULL,
    is_used                 BOOLEAN                  DEFAULT FALSE             NOT NULL,
    registered_at           TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL
);

