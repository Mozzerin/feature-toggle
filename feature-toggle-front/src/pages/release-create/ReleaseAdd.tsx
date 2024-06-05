import React, {ChangeEvent, FormEvent, useEffect, useState} from 'react';
import {Link, useNavigate, useParams} from 'react-router-dom';
import {Button, Container, Form, FormGroup, Input, Label} from 'reactstrap';
import AppNavbar from '../../component/AppNavbar';
import {Release} from "../../types/types";
import {ROLE_HEADER} from "../../types/conts";

const ReleasesAdd: React.FC = () => {
    const navigate = useNavigate();

    const emptyItem: Release = {
        version_id: '',
        description: '',
        feature_toggle_names: []
    };

    const [item, setItem] = useState<Release>(emptyItem);

    const handleChange = (event: ChangeEvent<HTMLInputElement>) => {
        const {name, value} = event.target;
        if (name === 'feature_toggle_names') {
            let customer_ids = value.split(",");
            setItem(prevItem => ({...prevItem, [name]: customer_ids}));

        } else {
            setItem(prevItem => ({...prevItem, [name]: value}));
        }
    };

    const handleSubmit = async (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        await fetch('/api/v1/operations/features/release', {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
                'Role': localStorage.getItem(ROLE_HEADER) || '""'
            },
            body: JSON.stringify(item),
        });
        navigate('/features');
    };

    const title = <h2>Create new release</h2>;

    return (
        <div>
            <AppNavbar/>
            <Container>
                {title}
                <Form onSubmit={handleSubmit}>
                    <FormGroup>
                        <Label for="version_id">Version name</Label>
                        <Input type="text" name="version_id" id="version_id" value={item.version_id || ''}
                               onChange={handleChange} autoComplete="version_id"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="description">Description</Label>
                        <Input type="text" name="description" id="description" value={item.description || ''}
                               onChange={handleChange} autoComplete="description"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="customer_ids">Comma separated feature toggle names</Label>
                        <Input type="text" name="feature_toggle_names" id="feature_toggle_names"
                               value={item.feature_toggle_names || []}
                               onChange={handleChange} autoComplete="feature_toggle_names"/>
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

export default ReleasesAdd;
