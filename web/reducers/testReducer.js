var initialState = {
    count: 0
}

const store = (state = initialState, action) => {
    switch (action.type) {
        case 'INCREMENT_COUNT':
            return Object.assign({}, state, {
                count: state.count + 1
            })
            break;
        default:
            return state
    }
}

export default store