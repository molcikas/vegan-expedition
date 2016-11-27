var initialState = {
    recipesFetchState: 'NOT_FETCHED',
    recipes: []
}

const store = (state = initialState, action) => {
    switch (action.type) {
        case 'FETCH_RECIPES':
            return Object.assign({}, state, {
                recipesFetchState: action.state,
                recipes: action.recipes ? action.recipes : []
            })
        case 'DELETE_RECIPE':
            let newState = Object.assign({}, state, {
                recipeDeleteState: action.state
            })
            if(action.state === 'DELETED') {
                newState.recipes = state.recipes.filter(r => r.recipeId !== action.recipeId)
            }
            return newState
        default:
            return state
    }
}

export default store