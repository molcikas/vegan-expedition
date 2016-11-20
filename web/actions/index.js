
export const fetchRecipes = () => {
    return function(dispatch) {
        dispatch({
            type: 'FETCH_RECIPES',
            state: 'FETCHING'
        })

        fetch('/api/recipes')
            .then(response => response.json())
            .then(recipes => dispatch({
                type: 'FETCH_RECIPES',
                state: 'FETCHED',
                recipes: recipes
            }))
    }
}