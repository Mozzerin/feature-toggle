import React, {Component} from 'react';
import AppNavbar from '../../component/AppNavbar';
import {Link} from 'react-router-dom';
import {Button, Container} from 'reactstrap';

class Home extends Component<{}, {}> {
    render() {
        return (
            <div>
                <AppNavbar/>
                <Container fluid>
                    <Button color="link">
                        <Link to="/features">Features</Link>
                    </Button>
                </Container>
            </div>
        );
    }
}

export default Home;