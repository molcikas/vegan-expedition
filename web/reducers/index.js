import { combineReducers } from 'redux'

import recipe from './recipe'
import recipesList from './recipesList'

const mainReducer = combineReducers({
    recipe,
    recipesList
})

export default mainReducer