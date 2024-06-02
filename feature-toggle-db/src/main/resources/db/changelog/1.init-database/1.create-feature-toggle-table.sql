CREATE TABLE IF NOT EXISTS FeatureToggle
(
    ID            varchar(36) PRIMARY KEY      DEFAULT UUID(),
    DisplayName   varchar(128)        NULL,
    TechnicalName varchar(128) UNIQUE NOT NULL,
    ExpiresOn     timestamp           NULL,
    Description   varchar(256)        NULL,
    Inverted      bit                 NOT NULL default false,
    Released      bit                 NOT NULL DEFAULT false,
    CreatedAt     timestamp           NOT NULL DEFAULT NOW(),
    UpdatedAt     timestamp           NULL,
    INDEX (TechnicalName)
);
