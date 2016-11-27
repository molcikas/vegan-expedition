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

        let ingredientsList = this.state.recipe.ingredients.map((ing) => {
            let text = getQuantityString(ing.quantity) +
                (ing.quantityDetail ? ing.quantityDetail + ' ' : '') + 
                getQuantityUnit(ing.quantityUnit, ing.quantity) + 
                ing.name + 
                (ing.preparation ? ', ' + ing.preparation : '')
            text = text.trim()
            text = text[0].toUpperCase() + text.substring(1)
            return (
                <li key={ing.recipeIngredientId} className="litext">{text}</li>
            )
        })

        let instructionsList = this.state.recipe.instructions.map((ins) => 
            <li key={ins.recipeInstructionId} className="litext">{ins.description}</li>
        )

        return (
            <div>
                <h1>{this.state.recipe.name}</h1>
                <span id="lblDescription">{this.state.recipe.description}</span>
                <table id="tblStats">
                    <tbody>
                        <tr>
                            <th><label htmlFor="PrepTime">Prep Time</label>:</th>
                            <td>{this.state.recipe.prepTime} minutes</td>
                        </tr>
                        <tr>
                            <th><label htmlFor="CookTime">Cook Time</label>:</th>
                            <td>{this.state.recipe.cookTime} minutes</td>
                        </tr>
                        <tr>
                            <th>Total Time:</th>
                            <td>{this.state.recipe.prepTime + this.state.recipe.cookTime} minutes</td>
                        </tr>
                        <tr>
                            <th><label htmlFor="Servings">Servings</label>:</th>
                            <td>{this.state.recipe.servings}</td>
                        </tr>
                        <tr>
                            <th><label htmlFor="IsVegetarian">Vegetarian?</label></th>
                            <td>{this.state.recipe.vegetarian ? 'Yes' : 'No'}</td>
                        </tr>
                        <tr>
                            <th><label htmlFor="IsVegan">Vegan?</label></th>
                            <td>{this.state.recipe.vegan ? 'Yes' : 'No'}</td>
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

export default Radium(Recipe)

function getQuantityString(quantity) {
    if(!quantity || quantity === 1) {
        return ''
    }
    return quantity
        .toString()
        .replace(/[0\.]+$/g, '')
        .replace('.12', ' 1/8')
        .replace('.25', ' 1/4')
        .replace('.33', ' 1/3')
        .replace('.37', ' 3/8')
        .replace('.38', ' 3/8')
        .replace('.5', ' 1/2')
        .replace('.62', ' 5/8')
        .replace('.63', ' 5/8')
        .replace('.66', ' 2/3')
        .replace('.67', ' 2/3')
        .replace('.75', ' 3/4')
        .replace('.87', ' 7/8')
        .replace('.88', ' 7/8')
        .replace('0 ', '') + ' '
}

function getQuantityUnit(quantityUnit, quantity) {
    if(!quantityUnit) {
        return ''
    }
    return quantity >= 0 && quantity <= 1 ? quantityUnit + ' ' : quantityUnit + 's '
}