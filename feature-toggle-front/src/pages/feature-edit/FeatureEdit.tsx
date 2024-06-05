import React, { ChangeEvent, FormEvent, Component } from 'react';
import { Link, NavigateFunction, Params } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import AppNavbar from '../../component/AppNavbar';
import { FeatureToggle } from '../../types/types';
import withRouter from '../../component/WithRouter';

interface Props {
    navigate: NavigateFunction;
    params: Params<string>;
}

interface State {
    item: FeatureToggle;
}

class FeatureToggleEdit extends Component<Props, State> {
    constructor(props: Props) {
        super(props);

        const { technical_name } = props.params;

        this.state = {
            item: {
                technical_name: '',
                display_name: '',
                description: '',
                expires_on: '',
                inverted: false,
                version_id: '',
                archived: false,
                customer_ids: []
            },
        };

        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);

        if (technical_name && technical_name !== 'new') {
            this.fetchData(technical_name);
        }
    }

    fetchData(technical_name: string) {
        fetch(`/api/v1/features/${technical_name}`)
            .then(response => response.json())
            .then(data => this.setState({ item: data }));
    }

    handleChange(event: ChangeEvent<HTMLInputElement>) {
        const { name, value } = event.target;
        if (name === 'customer_ids') {
            const customer_ids = value.split(",");
            this.setState(prevState => ({
                item: { ...prevState.item, [name]: customer_ids }
            }));
        } else if (name === 'inverted' || name === 'archived') {
            const checked = event.target.checked;
            this.setState(prevState => ({
                item: { ...prevState.item, [name]: checked }
            }));
        } else {
            this.setState(prevState => ({
                item: { ...prevState.item, [name]: value }
            }));
        }
    }

    async handleSubmit(event: FormEvent<HTMLFormElement>) {
        event.preventDefault();
        const { item } = this.state;
        const { navigate } = this.props;
        await fetch('/api/v1/features' + (item.technical_name ? '/' + item.technical_name : ''), {
            method: item.technical_name ? 'PUT' : 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(item),
        });
        navigate('/features');
    }

    render() {
        const { item } = this.state;
        const title = <h2>{item.technical_name ? 'Edit feature toggle' : 'Add feature toggle'}</h2>;

        return (
            <div>
                <AppNavbar />
                <Container>
                    {title}
                    <Form onSubmit={this.handleSubmit}>
                        <FormGroup>
                            <Label for="display_name">Display Name</Label>
                            <Input type="text" name="display_name" id="display_name" value={item.display_name || ''}
                                   onChange={this.handleChange} autoComplete="display_name" />
                        </FormGroup>
                        <FormGroup>
                            <Label for="description">Description</Label>
                            <Input type="text" name="description" id="description" value={item.description || ''}
                                   onChange={this.handleChange} autoComplete="description" />
                        </FormGroup>
                        <FormGroup>
                            <Label for="technical_name">Technical name</Label>
                            <Input type="text" name="technical_name" id="technical_name" value={item.technical_name || ''}
                                   onChange={this.handleChange} autoComplete="technical_name" />
                        </FormGroup>
                        <FormGroup>
                            <Label for="expires_on">Expiration datetime</Label>
                            <Input type="datetime-local" name="expires_on" id="expires_on" value={item.expires_on || ''}
                                   onChange={this.handleChange} autoComplete="expires_on" />
                        </FormGroup>
                        <FormGroup>
                            <Label for="inverted">Inverted</Label>
                            <Input type="checkbox" name="inverted" id="inverted" checked={item.inverted}
                                   onChange={this.handleChange} autoComplete="inverted" />
                        </FormGroup>
                        <FormGroup>
                            <Label for="archived">Archived</Label>
                            <Input type="checkbox" name="archived" id="archived" checked={item.archived}
                                   onChange={this.handleChange} autoComplete="archived" />
                        </FormGroup>
                        <FormGroup>
                            <Label for="customer_ids">Customer ids</Label>
                            <Input type="text" name="customer_ids" id="customer_ids" value={item.customer_ids.join(',') || ''}
                                   onChange={this.handleChange} autoComplete="customer_ids" />
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

export default withRouter(FeatureToggleEdit);
