import React from 'react';
import ReactDOM from 'react-dom';
import { Router } from 'react-router-dom';
import { createBrowserHistory } from 'history';
import './index.css';
import registerServiceWorker from './registerServiceWorker';
import Routes from './routes';

const history = createBrowserHistory();

ReactDOM.render(
    <Router history={history}>
        <Routes />
    </Router>, 
    document.getElementById('root'
));
registerServiceWorker();
