CREATE TABLE IF NOT EXISTS Customer
(
    ID         varchar(36) PRIMARY KEY DEFAULT UUID(),
    CustomerId BIGINT    NOT NULL,
    CreatedAt  timestamp NOT NULL      DEFAULT NOW(),
    INDEX (CustomerId)
);
