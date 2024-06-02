CREATE TABLE IF NOT EXISTS FeatureToggleCustomer
(
    ID            varchar(36) PRIMARY KEY DEFAULT UUID(),
    CustomerID    varchar(36) NOT NULL,
    TechnicalName varchar(36) NOT NULL,
    FOREIGN KEY (CustomerID) REFERENCES Customer (CustomerID),
    FOREIGN KEY (TechnicalName) REFERENCES FeatureToggle (TechnicalName)
);
