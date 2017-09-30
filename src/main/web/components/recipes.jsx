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
        this.state = store.getState().recipesList
        this.state.filters = {
            recipeName: '',
            maxPoints: null
        }

        this.updateFilter = this.updateFilter.bind(this)
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

    updateFilter(event) {
        this.state.filters[event.target.name] = event.target.value
        this.setState(this.state)
    }

    render() {
        if(this.state.recipesFetchState === 'FETCHING') {
            return <div></div>
        }

        var recipes = this.state.recipes.filter((recipe) => {
            if(this.state.filters.recipeName && recipe.name.indexOf(this.state.filters.recipeName) === -1) {
                return false
            }
            if(this.state.filters.maxPoints && (recipe.points == null || recipe.points > this.state.filters.maxPoints)) {
                return false
            }
            return true
        })

        var recipeRows = recipes.map((recipe) => (
            <tr key={recipe.recipeId}>
                <td><Link to={`/recipes/${recipe.recipeId}`}>{recipe.name}</Link></td>
                <td>{recipe.isVegetarian ? 'Yes' : 'No'}/{recipe.isVegan ? 'Yes' : 'No'}</td>
                <td>{recipe.points}</td>
                <td>{recipe.servings}</td>
                <td>
                    <Link to={`/recipes/${recipe.recipeId}/edit`} style={ingredientChangeLinkStyle} title="Edit">&#8601;</Link>
                    <a href="" onClick={(e) => this.deleteRecipe(e, recipe)} style={ingredientChangeLinkStyle} title="Delete">X</a>
                </td>
            </tr>
        ))

        return (
            <div>
                <h1>Recipes <Link to="/recipes/add" onClick={this.addRecipe} style={{fontSize: '8pt', paddingLeft: '10px'}}>Add New</Link></h1>
                <table className="NoBorderTh NoLeftPaddingTh">
                    <tr>
                        <th><label>Recipe:</label></th>
                        <td><input type="text" name="recipeName" onChange={this.updateFilter} /></td>
                    </tr>
                    <tr>
                        <th><label>Max Points:</label></th>
                        <td><input type="number" name="maxPoints" onChange={this.updateFilter} /></td>
                    </tr>
                </table>
                <table style={{width: '100%'}}>
                    <thead>
                        <tr>
                            <th>Recipe</th>
                            <th>Veggie/Vegan</th>
                            <th>Points</th>
                            <th>Servings</th>
                            <th>Edit</th>
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

const ingredientChangeLinkStyle = {
    textDecoration: 'none',
    fontSize: '16pt',
    paddingRight: '6px',
    verticalAlign: 'middle'
}

export default Radium(Recipes)
