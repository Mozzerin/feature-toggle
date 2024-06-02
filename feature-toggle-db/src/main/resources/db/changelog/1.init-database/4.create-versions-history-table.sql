CREATE TABLE IF NOT EXISTS FeatureToggle
(
    ID          varchar(36) PRIMARY KEY DEFAULT UUID(),
    VersionName varchar(128) NOT NULL,
    Description varchar(256) NULL,
    CreatedAt   timestamp    NOT NULL   DEFAULT NOW(),
    INDEX (VersionName)
);
