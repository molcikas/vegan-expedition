import React from 'react'
import { Link } from 'react-router'
import { fetchRecipes } from '../actions'
import Radium from 'radium'

let store
let storeUnsubscribe
let baseStyles

class Recipes extends React.Component {
    constructor(props, ...args) {
        super(props, ...args)
        store = props.route.store
        baseStyles = props.route.baseStyles

        this.onStoreChanged = this.onStoreChanged.bind(this)

        storeUnsubscribe = store.subscribe(this.onStoreChanged)
        this.state = store.getState().recipesList;
    }

    componentWillMount() {
        store.dispatch(fetchRecipes())
    }

    componentWillUnmount() {
        storeUnsubscribe()
    }

    onStoreChanged() {
        this.setState(store.getState().recipesList)
    }

    render() {
        if(this.state.recipesFetchState === 'FETCHING') {
            return <div></div>
        }

        var recipeRows = this.state.recipes.map((recipe) => (
            <tr key={recipe.recipeId}>
                <td><Link to={`/recipe/${recipe.recipeId}`}>{recipe.name}</Link></td>
                <td>{recipe.vegetarian ? 'Yes' : 'No'}</td>
                <td>{recipe.vegan ? 'Yes' : 'No'}</td>
                <td>{recipe.servings}</td>
                <td><input type="checkbox" value={`recipe:${recipe.recipeId}`} /></td>
            </tr>
        ));

        return (
            <div>
                <table>
                    <thead>
                        <tr>
                            <th>Recipe</th>
                            <th>Vegetarian?</th>
                            <th>Vegan?</th>
                            <th>Servings</th>
                        </tr>
                    </thead>
                    <tbody>
                        {recipeRows}
                    </tbody>
                </table>
            </div>
        )
    }
}

export default Radium(Recipes)
