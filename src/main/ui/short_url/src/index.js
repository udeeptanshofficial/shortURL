import React from 'react';
import ReactDOM from 'react-dom';
import { Router } from 'react-router-dom';
import { Provider } from 'react-redux';
import { createBrowserHistory } from 'history';
import registerServiceWorker from './registerServiceWorker';
import Routes from './routes';
import store from './store/configureStore';

import './index.css';


const history = createBrowserHistory();


ReactDOM.render(
    <Provider store={store}>
        <Router history={history}>
            <Routes />
        </Router>
    </Provider>, 
    document.getElementById('root')
);
registerServiceWorker();
