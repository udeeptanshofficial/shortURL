import React from 'react';
import ReactDOM from 'react-dom';
import { Router } from 'react-router-dom';
import { Provider } from 'react-redux';
import createHistory from 'history/createBrowserHistory';
import registerServiceWorker from './registerServiceWorker';
import Routes from './routes';
import store from './store/configureStore';

import './index.css';


const publicUrl = new URL(process.env.PUBLIC_URL, window.location);
const history = createHistory({
  basename: publicUrl.pathname
});


ReactDOM.render(
    <Provider store={store}>
        <Router history={history}>
            <Routes />
        </Router>
    </Provider>, 
    document.getElementById('root')
);
registerServiceWorker();
