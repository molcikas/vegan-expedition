import React from 'react'
import { Link } from 'react-router'
import { fetchRecipes, deleteRecipe } from '../actions'
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
        this.deleteRecipe = this.deleteRecipe.bind(this)

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

    deleteRecipe(event, recipe) {
        event.preventDefault()
        if(confirm(`Are you sure you want to delete ${recipe.name}?`)) {
            store.dispatch(deleteRecipe(recipe.recipeId))
        }
    }

    render() {
        if(this.state.recipesFetchState === 'FETCHING') {
            return <div></div>
        }

        var recipeRows = this.state.recipes.map((recipe) => (
            <tr key={recipe.recipeId}>
                <td><Link to={`/recipes/${recipe.recipeId}`}>{recipe.name}</Link></td>
                <td>{recipe.isVegetarian ? 'Yes' : 'No'}</td>
                <td>{recipe.isVegan ? 'Yes' : 'No'}</td>
                <td>{recipe.servings}</td>
                <td>
                    <Link to={`/recipes/${recipe.recipeId}/edit`} style={ingredientChangeLinkStyle} title="Edit">&#8601;</Link>
                    <a href="" onClick={(e) => this.deleteRecipe(e, recipe)} style={ingredientChangeLinkStyle} title="Delete">X</a>
                </td>
            </tr>
        ))

        return (
            <div>
                <table>
                    <thead>
                        <tr>
                            <th>Recipe</th>
                            <th>Vegetarian?</th>
                            <th>Vegan?</th>
                            <th>Servings</th>
                            <th>Edit</th>
                        </tr>
                    </thead>
                    <tbody>
                        {recipeRows}
                    </tbody>
                </table>
                <Link to="/recipes/add" onClick={this.addRecipe}>Add Recipe</Link>
            </div>
        )
    }
}

const ingredientChangeLinkStyle = {
    textDecoration: 'none',
    fontSize: '16pt',
    paddingRight: '6px',
    verticalAlign: 'middle'
}

export default Radium(Recipes)
