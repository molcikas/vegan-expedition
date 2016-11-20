
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

export const fetchRecipe = (recipeId) => {
    return function(dispatch) {
        dispatch({
            type: 'FETCH_RECIPE',
            state: 'FETCHING'
        })

        fetch(`/api/recipes/${recipeId}`)
            .then(response => response.json())
            .then(recipe => dispatch({
                type: 'FETCH_RECIPE',
                state: 'FETCHED',
                recipe: recipe
            }))
    }
}