import React from 'react'
import ReactDOM from 'react-dom'
import { createStore } from 'redux'
import { Router, Route, hashHistory } from 'react-router'

import baseStyles from './baseStyles'
import App from './components/app'
import mainReducer from './reducers'

let store = createStore(mainReducer)

ReactDOM.render((
    <Router history={hashHistory}>
        <Route path="/" component={App} store={store} baseStyles={baseStyles} />
    </Router>
), document.getElementById('root'));
