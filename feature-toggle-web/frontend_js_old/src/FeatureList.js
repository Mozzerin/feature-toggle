import {Component} from 'react';
import {Link} from "react-router-dom";
import {Button, ButtonGroup, Container, Table} from "reactstrap";
import AppNavbar from "./AppNavbar";

class FeatureList extends Component {

    constructor(props) {
        super(props);
        this.state = {featureTogglesPage: {}};
        this.state = {featureToggles: []};
        this.archive = this.archive.bind(this);
    }

    componentDidMount() {
        fetch('/api/v1/features?pageNo=0&pageSize=100')
            .then(response => response.json())
            .then(data => this.setState({featureToggles: data.feature_toggles}));
    }

    async archive(technicalName) {
        await fetch(`/api/v1/operations/features/archive`, {
            method: 'PUT',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: [
                JSON.stringify({technicalName})
            ]
        }).then(() => {
            let updatedToggles = [...this.state.featureToggles].filter(i => i.technicalName !== technicalName);
            this.setState({featureToggles: updatedToggles});
        });
    }

    render() {
        const {featureToggles, isLoading} = this.state;

        if (isLoading) {
            return <p>Loading...</p>;
        }

        const featureToggleList = featureToggles.map(featureToggle => {
            return <tr key={featureToggle.technical_name}>
                <td>{featureToggle.display_name}</td>
                <td>{featureToggle.description}</td>
                <td>{featureToggle.expires_on}</td>
                <td>{featureToggle.inverted}</td>
                <td>{featureToggle.version_id}</td>
                <td>{featureToggle.archived}</td>
                <td>{featureToggle.customer_ids}</td>
                <td>
                    <ButtonGroup>
                        <Button size="sm" color="primary" tag={Link} to={"/clients/" + featureToggle.technical_name}>Edit</Button>
                        <Button size="sm" color="danger" onClick={() => this.archive(featureToggle.technical_name)}>Archive</Button>
                    </ButtonGroup>
                </td>
            </tr>
        });

        return (
            <div>
                <AppNavbar/>
                <Container fluid>
                    <div className="float-right">
                        <Button color="success" tag={Link} to="/clients/new">Add feature toggle</Button>
                    </div>
                    <h3>Clients</h3>
                    <Table className="mt-4">
                        <thead>
                        <tr>
                            <th width="10%">Display name</th>
                            <th width="10%">Description</th>
                            <th width="10%">Expiration time</th>
                            <th width="10%">Inverted</th>
                            <th width="10%">Release version</th>
                            <th width="10%">Archived</th>
                            <th width="10%">Customer ids</th>
                            <th width="30%">Action</th>
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
