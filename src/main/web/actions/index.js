
export const fetchRecipes = () => {
    return function(dispatch) {
        dispatch({
            type: 'FETCH_RECIPES',
            state: 'FETCHING'
        })

        fetch('/api/recipes')
            .then(throwIfError)
            .then(response => response.json())
            .then(recipes => dispatch({
                type: 'FETCH_RECIPES',
                state: 'FETCHED',
                recipes: recipes
            }))
            .catch(error => dispatch({
                type: 'FETCH_RECIPES',
                state: 'ERROR',
                error: error
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
            .then(throwIfError)
            .then(response => response.json())
            .then(recipe => dispatch({
                type: 'FETCH_RECIPE',
                state: 'FETCHED',
                recipe: recipe
            }))
            .catch(error => dispatch({
                type: 'FETCH_RECIPE',
                state: 'ERROR',
                error: error
            }))
    }
}

export const addRecipe = (recipe) => {
    return function(dispatch) {
        dispatch({
            type: 'FETCH_RECIPE',
            state: 'ADDING',
            recipe: recipe
        })
        fetch(`/api/recipes`, {
            method: 'post',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(recipe)
        })
            .then(throwIfError)
            .then(response => response.json())
            .then(recipe => dispatch({
                type: 'FETCH_RECIPE',
                state: 'ADDED',
                recipeId: recipe.recipeId
            }))
            .catch(error => dispatch({
                type: 'FETCH_RECIPE',
                state: 'ERROR',
                error: error
            }))
    }
}

export const updateRecipe = (recipe) => {
    return function(dispatch) {
        dispatch({
            type: 'FETCH_RECIPE',
            state: 'UPDATING',
            recipe: recipe
        })
        fetch(`/api/recipes/${recipe.recipeId}`, {
            method: 'put',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(recipe)
        })
            .then(throwIfError)
            .then(response => dispatch({
                type: 'FETCH_RECIPE',
                state: 'UPDATED'
            }))
            .catch(error => dispatch({
                type: 'FETCH_RECIPE',
                state: 'ERROR',
                error: error
            }))
    }
}

export const deleteRecipe = (recipeId) => {
    return function(dispatch) {
        dispatch({
            type: 'DELETE_RECIPE',
            state: 'DELETING',
            recipeId: recipeId
        })
        fetch(`/api/recipes/${recipeId}`, {
            method: 'delete'
        })
            .then(throwIfError)
            .then(recipe => dispatch({
                type: 'DELETE_RECIPE',
                state: 'DELETED',
                recipeId: recipeId
            }))
            .catch(error => dispatch({
                type: 'DELETE_RECIPE',
                state: 'ERROR',
                error: error
            }))
    }
}

function throwIfError(response) {
    if (!response.ok) {
        throw Error(response.statusText);
    }
    return response;
}