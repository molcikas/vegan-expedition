import { combineReducers } from 'redux'
import recipesList from './recipesList'

const mainReducer = combineReducers({
    recipesList
})

export default mainReducer