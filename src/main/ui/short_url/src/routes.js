import React from 'react';
import { Switch, Route } from 'react-router-dom';
import Home from './web_pages/Home';
import ViewPage from './web_pages/ViewPage';

export default () => (
    <Switch>
        <Route exact path="/" component={Home} />
        <Route path="/:shortURL" component={ViewPage} />
    </Switch>
);