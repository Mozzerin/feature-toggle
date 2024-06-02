CREATE TABLE IF NOT EXISTS Customer
(
    ID         varchar(36) NOT NULL DEFAULT UUID(),
    CustomerId BIGINT      NOT NULL UNIQUE PRIMARY KEY,
    CreatedAt  timestamp   NOT NULL DEFAULT CURRENT_TIMESTAMP()
);

CREATE INDEX IF NOT EXISTS ID_index on Customer (ID)
