CREATE TABLE IF NOT EXISTS FeatureToggle
(
    ID            varchar(36)  NOT NULL DEFAULT UUID(),
    DisplayName   varchar(128) NULL,
    TechnicalName varchar(128) NOT NULL PRIMARY KEY,
    ExpiresOn     timestamp    NULL,
    Description   varchar(256) NULL,
    Inverted      bit          NOT NULL DEFAULT 0,
    Released      bit          NOT NULL DEFAULT 0,
    Archived      bit          NULL     DEFAULT 0,
    VersionID     varchar(36)  NULL,
    CreatedAt     timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP(),
    UpdatedAt     timestamp    NULL
);

CREATE INDEX IF NOT EXISTS ID_index on FeatureToggle (ID)
