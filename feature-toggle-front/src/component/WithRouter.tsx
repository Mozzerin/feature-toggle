import React, {ComponentType} from 'react';
import {useNavigate, useParams} from 'react-router-dom';

// Higher-Order Component to inject `navigate` and `params`
function withRouter(Component: ComponentType<any>) {
    return (props: any) => {
        const navigate = useNavigate();
        const params = useParams();
        return <Component {...props} navigate={navigate} params={params} />;
    };
}

export default withRouter;
