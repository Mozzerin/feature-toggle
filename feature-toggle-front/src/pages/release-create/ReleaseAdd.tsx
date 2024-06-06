import React, {ChangeEvent, FormEvent, Component} from 'react';
import {Link, NavigateFunction, Params} from 'react-router-dom';
import {Button, Container, Form, FormGroup, Input, Label} from 'reactstrap';
import AppNavbar from '../../component/AppNavbar';
import {Release} from '../../types/types';
import {ROLE_HEADER} from '../../types/conts';
import withRouter from '../../component/WithRouter';

interface Props {
    navigate: NavigateFunction;
    params: Params;
}

interface State {
    item: Release;
}

class ReleasesAdd extends Component<Props, State> {
    constructor(props: Props) {
        super(props);

        this.state = {
            item: {
                version_id: '',
                description: '',
                feature_toggle_names: []
            },
        };

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleChange(event: ChangeEvent<HTMLInputElement>) {
        const {name, value} = event.target;
        if (name === 'feature_toggle_names') {
            const feature_toggle_names = value.split(",");
            this.setState(prevState => ({
                item: {...prevState.item, [name]: feature_toggle_names}
            }));
        } else {
            this.setState(prevState => ({
                item: {...prevState.item, [name]: value}
            }));
        }
    }

    async handleSubmit(event: FormEvent<HTMLFormElement>) {
        event.preventDefault();
        const {item} = this.state;
        const {navigate} = this.props;
        await fetch('/feature-toggle/api/v1/operations/features/release', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Role': localStorage.getItem(ROLE_HEADER) || '""'
            },
            body: JSON.stringify(item),
        });
        navigate('/feature-toggle/features');
    }

    render() {
        const {item} = this.state;

        return (
            <div>
                <AppNavbar/>
                <Container>
                    <h2>Create new release</h2>
                    <Form onSubmit={this.handleSubmit}>
                        <FormGroup>
                            <Label for="version_id">Version name</Label>
                            <Input type="text" name="version_id" id="version_id" value={item.version_id || ''}
                                   onChange={this.handleChange} autoComplete="version_id"/>
                        </FormGroup>
                        <FormGroup>
                            <Label for="description">Description</Label>
                            <Input type="text" name="description" id="description" value={item.description || ''}
                                   onChange={this.handleChange} autoComplete="description"/>
                        </FormGroup>
                        <FormGroup>
                            <Label for="feature_toggle_names">Comma separated feature toggle names</Label>
                            <Input type="text" name="feature_toggle_names" id="feature_toggle_names"
                                   value={item.feature_toggle_names.join(',') || ''}
                                   onChange={this.handleChange} autoComplete="feature_toggle_names"/>
                        </FormGroup>
                        <FormGroup>
                            <Button color="primary" type="submit">Save</Button>{' '}
                            <Button color="secondary" tag={Link} to="/clients">Cancel</Button>
                        </FormGroup>
                    </Form>
                </Container>
            </div>
        );
    }
}

export default withRouter(ReleasesAdd);
