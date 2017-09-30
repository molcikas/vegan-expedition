import React from 'react'
import { Link } from 'react-router'
import { fetchRecipe } from '../actions'
import Radium from 'radium'

let store
let storeUnsubscribe
let baseStyles

class Recipe extends React.Component {
    constructor(props, ...args) {
        super(props, ...args)
        store = props.route.store
        baseStyles = props.route.baseStyles

        this.onStoreChanged = this.onStoreChanged.bind(this)

        storeUnsubscribe = store.subscribe(this.onStoreChanged)
        this.state = store.getState().recipe
    }

    componentWillMount() {
        store.dispatch(fetchRecipe(this.props.params.recipeId))
    }

    componentWillUnmount() {
        storeUnsubscribe()
    }

    onStoreChanged() {
        this.setState(store.getState().recipe)
    }

    render() {
        if(this.state.recipeFetchState === 'FETCHING') {
            return <div></div>
        }

        let ingredientsList = this.state.recipe.ingredients.map((ing, i) => {
            let text = (ing.quantity && ing.quantity !== '1' ? ing.quantity + ' ' : '') +
                (ing.quantityDetail ? ing.quantityDetail + ' ' : '') + 
                getQuantityUnit(ing.quantityUnit, ing.quantity) + 
                ing.name + 
                (ing.preparation ? ', ' + ing.preparation : '')
            text = text.trim()
            text = text[0].toUpperCase() + text.substring(1)
            return (
                <li key={i} className="litext">{text}</li>
            )
        })

        let instructionsList = this.state.recipe.instructions.map((ins, i) => 
            <li key={i} className="litext">{ins.description}</li>
        )

        return (
            <div>
                <h1>{this.state.recipe.name} <Link to={`/recipes/${this.state.recipe.recipeId}/edit`} style={ingredientChangeLinkStyle}>&#8601;</Link></h1>
                <span id="lblDescription">{this.state.recipe.description}</span>
                <table id="tblStats">
                    <tbody>
                        <tr>
                            <th>Prep Time:</th>
                            <td>{this.state.recipe.prepTime} minutes</td>
                        </tr>
                        <tr>
                            <th>Cook Time:</th>
                            <td>{this.state.recipe.cookTime} minutes</td>
                        </tr>
                        <tr>
                            <th>Total Time:</th>
                            <td>{this.state.recipe.prepTime + this.state.recipe.cookTime} minutes</td>
                        </tr>
                        <tr>
                            <th>Servings:</th>
                            <td>{this.state.recipe.servings}</td>
                        </tr>
                        <tr>
                            <th>Vegetarian?</th>
                            <td>{this.state.recipe.isVegetarian ? 'Yes' : 'No'}</td>
                        </tr>
                        <tr>
                            <th>Vegan?</th>
                            <td>{this.state.recipe.isVegan ? 'Yes' : 'No'}</td>
                        </tr>
                        <tr>
                            <th>Points</th>
                            <td>{this.state.recipe.points}</td>
                        </tr>
                    </tbody>
                </table>
                <ul>
                    {ingredientsList}
                </ul>
                <ol>
                    {instructionsList}
                </ol>
	        </div>
        )
    }
}

function getQuantityUnit(quantityUnit, quantity) {
    if(!quantityUnit) {
        return ''
    }
    return quantity >= 0 && quantity <= 1 ? quantityUnit + ' ' : quantityUnit + 's '
}

const ingredientChangeLinkStyle = {
    textDecoration: 'none',
    fontSize: '16pt',
    paddingLeft: '6px',
    verticalAlign: 'middle'
}

export default Radium(Recipe)
