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
        default:
            return state
    }
}

export default store