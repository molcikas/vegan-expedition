var initialState = {
    recipeFetchState: 'NOT_FETCHED',
    recipe: null
}

const store = (state = initialState, action) => {
    switch (action.type) {
        case 'FETCH_RECIPE':
            return Object.assign({}, state, {
                recipeFetchState: action.state,
                recipe: action.recipe ? action.recipe : null
            })
        default:
            return state
    }
}

export default store