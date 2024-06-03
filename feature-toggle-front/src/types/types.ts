export interface FeatureToggle {
    technical_name: string;
    display_name: string;
    description: string;
    expires_on: string;
    inverted: boolean;
    version_id: string;
    archived: boolean;
    customer_ids: string[];
}
