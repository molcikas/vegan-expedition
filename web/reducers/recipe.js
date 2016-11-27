var initialState = {
    recipeFetchState: 'NOT_FETCHED',
    recipeDeleteState: 'NOT_DELETED',
    recipe: null
}

const store = (state = initialState, action) => {
    switch (action.type) {
        case 'FETCH_RECIPE':
            let newState = Object.assign({}, state, {
                recipeFetchState: action.state
            })
            if(action.state === 'FETCHING') {
                newState.recipe = null
            }
            if(action.recipe) {
                newState.recipe = action.recipe
            }
            if(newState.recipe && action.recipeId) {
                newState.recipe.recipeId = action.recipeId
            }
            return newState
        default:
            return state
    }
}

export default store