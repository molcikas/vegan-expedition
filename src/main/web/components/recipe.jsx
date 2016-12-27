import React from 'react'
import { Link } from 'react-router'
import { fetchRecipe } from '../actions'
import Radium from 'radium'

import InputFraction from './input-fraction'

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
            let text = (ing.quantityDetail ? ing.quantityDetail + ' ' : '') + 
                getQuantityUnit(ing.quantityUnit, ing.quantity) + 
                ing.name + 
                (ing.preparation ? ', ' + ing.preparation : '')
            text = text.trim()
            text = text[0].toUpperCase() + text.substring(1)
            return (
                <li key={i} className="litext"><InputFraction value={ing.quantity} renderAsSpan="true" blankIfOne="true" /> {text}</li>
            )
        })

        let instructionsList = this.state.recipe.instructions.map((ins, i) => 
            <li key={i} className="litext">{ins.description}</li>
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
                            <td>{this.state.recipe.isVegetarian ? 'Yes' : 'No'}</td>
                        </tr>
                        <tr>
                            <th><label htmlFor="IsVegan">Vegan?</label></th>
                            <td>{this.state.recipe.isVegan ? 'Yes' : 'No'}</td>
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

function getQuantityUnit(quantityUnit, quantity) {
    if(!quantityUnit) {
        return ''
    }
    return quantity >= 0 && quantity <= 1 ? quantityUnit + ' ' : quantityUnit + 's '
}