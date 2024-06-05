import React from 'react';
import './App.css';
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import Home from './pages/home/Home';
import FeatureList from './pages/feature-list/FeatureList';
import FeatureToggleEdit from "./pages/feature-edit/FeatureEdit";
import ReleasesAdd from "./pages/release-create/ReleaseAdd";

function App() {
    return (
        <Router>
            <Routes>
                <Route path='/feature-toggle' element={<Home/>}/>
                <Route path='/feature-toggle/features' element={<FeatureList/>}/>
                <Route path='/feature-toggle/features/:technical_name' element={<FeatureToggleEdit/>}/>
                <Route path='/feature-toggle/releases' element={<ReleasesAdd/>}/>
            </Routes>
        </Router>
    );
}

export default App;
