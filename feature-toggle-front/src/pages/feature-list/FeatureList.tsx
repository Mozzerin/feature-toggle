import React, {Component} from 'react';
import {Link} from "react-router-dom";
import {Button, ButtonGroup, Container, Table} from "reactstrap";
import AppNavbar from '../../component/AppNavbar';
import {FeatureToggle} from "../../types/types";

interface FeatureListState {
    featureToggles: FeatureToggle[];
    isLoading: boolean;
}

class FeatureList extends Component<{}, FeatureListState> {
    constructor(props: {}) {
        super(props);
        this.state = {
            featureToggles: [],
            isLoading: true
        };
        this.archive = this.archive.bind(this);
    }

    componentDidMount() {
        fetch('/api/v1/features?pageNo=0&pageSize=100')
            .then(response => response.json())
            .then(data => this.setState({featureToggles: data.feature_toggles, isLoading: false}));
    }

    async archive(technicalName: string) {
        await fetch(`/api/v1/operations/features/archive`, {
            method: 'PUT',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(new Array(technicalName))
        }).then(() => {
            let updatedToggles = this.state.featureToggles.filter(i => i.technical_name !== technicalName);
            this.setState({featureToggles: updatedToggles});
        });
    }

    render() {
        const {featureToggles, isLoading} = this.state;

        if (isLoading) {
            return <p>Loading...</p>;
        }

        const featureToggleList = featureToggles.map(featureToggle => (
            <tr key={featureToggle.technical_name}>
                <td>{featureToggle.display_name}</td>
                <td>{featureToggle.description}</td>
                <td>{featureToggle.expires_on}</td>
                <td>{featureToggle.inverted ? 'True' : 'False'}</td>
                <td>{featureToggle.version_id}</td>
                <td>{featureToggle.archived ? 'Yes' : 'No'}</td>
                <td>{featureToggle.customer_ids.toString()}</td>
                <td>
                    <ButtonGroup>
                        <Button size="sm" color="primary" tag={Link}
                                to={"/features/" + featureToggle.technical_name}>Edit</Button>
                        <Button size="sm" color="danger"
                                onClick={() => this.archive(featureToggle.technical_name)}>Archive</Button>
                    </ButtonGroup>
                </td>
            </tr>
        ));

        return (
            <div>
                <AppNavbar/>
                <Container fluid>
                    <div>
                        <Button color="success" tag={Link} to="/features/new">Add feature toggle</Button>
                    </div>
                    <h3>Feature toggles</h3>
                    <Table className="mt-4">
                        <thead>
                        <tr>
                            <th style={{width: "10%"}}>Display name</th>
                            <th style={{width: "10%"}}>Description</th>
                            <th style={{width: "10%"}}>Expiration time</th>
                            <th style={{width: "10%"}}>Inverted</th>
                            <th style={{width: "10%"}}>Release version</th>
                            <th style={{width: "10%"}}>Archived</th>
                            <th style={{width: "10%"}}>Customer ids</th>
                            <th style={{width: "30%"}}>Action</th>
                        </tr>
                        </thead>
                        <tbody>
                        {featureToggleList}
                        </tbody>
                    </Table>
                </Container>
            </div>
        );
    }
}

export default FeatureList;
