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
                <Route path='/' element={<Home/>}/>
                <Route path='/features' element={<FeatureList/>}/>
                <Route path='/features/:technical_name' element={<FeatureToggleEdit/>}/>
                <Route path='/releases' element={<ReleasesAdd/>}/>
            </Routes>
        </Router>
    );
}

export default App;
