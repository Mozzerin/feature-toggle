import React, {Component} from 'react';
import AppNavbar from '../../component/AppNavbar';
import {Link} from 'react-router-dom';
import {Button, Container} from 'reactstrap';
import {ADMIN_ROLE, ROLE_HEADER} from "../../types/conts";

class Home extends Component<{}, {}> {
    render() {
        return (
            <div>
                <AppNavbar/>
                <Container fluid>
                    <Button color="link">
                        <Link to="/features">Features</Link><br></br>
                        {localStorage.getItem(ROLE_HEADER) === ADMIN_ROLE && <Link to="/releases">Releases</Link>}
                    </Button>
                </Container>
            </div>
        );
    }
}

export default Home;
