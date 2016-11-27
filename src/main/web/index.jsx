import React from 'react'
import ReactDOM from 'react-dom'
import { createStore, applyMiddleware  } from 'redux'
import thunk from 'redux-thunk';
import { Router, Route, IndexRedirect, Redirect, hashHistory } from 'react-router'

import baseStyles from './baseStyles'
import mainReducer from './reducers'

import layout from './components/layout'
import recipes from './components/recipes'
import recipe from './components/recipe'
import recipeEdit from './components/recipe-edit'

let store = createStore(mainReducer, applyMiddleware(thunk));

require('./styles/layout.less');

ReactDOM.render((
    <Router history={hashHistory}>
        <Route path="/" component={layout}>
            <IndexRedirect to="recipes" />
            <Route path="recipes" component={recipes} store={store} baseStyles={baseStyles} />
            <Route path="recipes/:recipeId/edit" component={recipeEdit} store={store} baseStyles={baseStyles} />
            <Route path="recipes/add" component={recipeEdit} store={store} baseStyles={baseStyles} />
            <Route path="recipes/:recipeId" component={recipe} store={store} baseStyles={baseStyles} />
        </Route>
    </Router>
), document.getElementById('root'));