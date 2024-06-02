CREATE TABLE IF NOT EXISTS FeatureToggleCustomer
(
    CustomerID    BIGINT       NOT NULL,
    TechnicalName varchar(128) NOT NULL
);

ALTER TABLE FeatureToggleCustomer
    ADD PRIMARY KEY (CustomerID, TechnicalName);

ALTER TABLE FeatureToggleCustomer
    ADD FOREIGN KEY (CustomerID)
        REFERENCES Customer (CustomerID);

ALTER TABLE FeatureToggleCustomer
    ADD FOREIGN KEY (TechnicalName)
        REFERENCES FeatureToggle (TechnicalName);
