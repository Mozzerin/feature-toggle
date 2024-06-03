import React, {useEffect, useState, ChangeEvent, FormEvent} from 'react';
import {useParams, useNavigate, Link} from 'react-router-dom';
import {Button, Container, Form, FormGroup, Input, Label} from 'reactstrap';
import AppNavbar from '../../component/AppNavbar';
import {FeatureToggle} from "../../types/types";

const FeatureToggleEdit: React.FC = () => {
    const navigate = useNavigate();
    const { technical_name } = useParams<string>();

    const emptyItem: FeatureToggle = {
        technical_name: '',
        display_name: '',
        description: '',
        expires_on: '',
        inverted: false,
        version_id: '',
        archived: false,
        customer_ids: []
    };

    const [item, setItem] = useState<FeatureToggle>(emptyItem);

    useEffect(() => {
        if (technical_name !== 'new') {
            fetch(`/api/v1/features/${technical_name}`)
                .then(response => response.json())
                .then(data => setItem(data));
        }
    }, [technical_name]);

    const handleChange = (event: ChangeEvent<HTMLInputElement>) => {
        const {name, value} = event.target;
        if (name === 'customer_ids') {
            let customer_ids = value.split(",");
            setItem(prevItem => ({...prevItem, [name]: customer_ids}));

        } else {
            setItem(prevItem => ({...prevItem, [name]: value}));
        }
    };

    const handleSubmit = async (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        await fetch('/api/v1/features' + (item.technical_name ? '/' + item.technical_name : ''), {
            method: item.technical_name ? 'PUT' : 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(item),
        });
        navigate('/features');
    };

    const title = <h2>{item.technical_name ? 'Edit feature toggle' : 'Add feature toggle'}</h2>;

    return (
        <div>
            <AppNavbar/>
            <Container>
                {title}
                <Form onSubmit={handleSubmit}>
                    <FormGroup>
                        <Label for="display_name">Display Name</Label>
                        <Input type="text" name="display_name" id="display_name" value={item.display_name || ''}
                               onChange={handleChange} autoComplete="display_name"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="description">Description</Label>
                        <Input type="text" name="description" id="description" value={item.description || ''}
                               onChange={handleChange} autoComplete="description"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="technical_name">Technical name</Label>
                        <Input type="text" name="technical_name" id="technical_name" value={item.technical_name || ''}
                               onChange={handleChange} autoComplete="technical_name"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="expires_on">Expiration datetime</Label>
                        <Input type="datetime-local" name="expires_on" id="expires_on" value={item.expires_on || ''}
                               onChange={handleChange} autoComplete="expires_on"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="inverted">Inverted</Label>
                        <Input type="checkbox" name="inverted" id="inverted" value={String(item.inverted) || ''}
                               onChange={handleChange} autoComplete="inverted"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="description">Archived</Label>
                        <Input type="checkbox" name="archived" id="archived" value={String(item.archived) || ''}
                               onChange={handleChange} autoComplete="archived"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="customer_ids">Customer ids</Label>
                        <Input type="text" name="customer_ids" id="customer_ids" value={item.customer_ids || []}
                               onChange={handleChange} autoComplete="customer_ids"/>
                    </FormGroup>
                    <FormGroup>
                        <Button color="primary" type="submit">Save</Button>{' '}
                        <Button color="secondary" tag={Link} to="/clients">Cancel</Button>
                    </FormGroup>
                </Form>
            </Container>
        </div>
    );
};

export default FeatureToggleEdit;
