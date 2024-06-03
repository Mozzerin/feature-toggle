CREATE TABLE IF NOT EXISTS FeatureToggleRelease
(
    ID          varchar(36)  NOT NULL DEFAULT UUID() PRIMARY KEY,
    VersionName varchar(128) NOT NULL,
    Description varchar(256) NULL,
    CreatedAt   timestamp    NOT NULL DEFAULT CURRENT_TIMESTAMP()
);

CREATE INDEX IF NOT EXISTS Version_index on FeatureToggleRelease (VersionName);

ALTER TABLE FeatureToggle
    ADD FOREIGN KEY (VersionID)
        REFERENCES FeatureToggleRelease (VersionName);
