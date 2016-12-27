import React from 'react'
import { Link } from 'react-router'
import Radium from 'radium'

import InputNumber from './input-number'
import InputFraction from './input-fraction'

class IngredientsEdit extends React.Component {
    constructor(props, ...args) {
        super(props, ...args)

        this.validateIngredients = this.validateIngredients.bind(this)
        this.updateIngredient = this.updateIngredient.bind(this)
        this.updateIngredientQuantity = this.updateIngredientQuantity.bind(this)
        this.addIngredient = this.addIngredient.bind(this)
        this.moveIngredient = this.moveIngredient.bind(this)
        this.deleteIngredient = this.deleteIngredient.bind(this)

        this.state = {
            errors: {}
        }
    }

    componentWillReceiveProps(newProps) {
        this.validateIngredients(newProps.ingredients)
    }

    validateIngredients(ingredients) {
        if(ingredients.filter(ing => !ing.name).length > 0) {
            this.state.errors.missingName = Object.assign(this.state.errors.missingName || {}, getErrorMessage('missingName'))
        }
        else {
            delete this.state.errors.missingName
        }
        this.setState(this.state)
    }

    updateIngredient(event) {
        let ingredients = Object.assign([], this.props.ingredients)
        let index = parseInt(event.target.name.match(indexRegex)[1])
        let name = event.target.name.substring(event.target.name.indexOf('.') + 1)
        if(event.target.type === 'checkbox') {
            ingredients[index][name] = !!!ingredients[index][name]
        }
        else {
           ingredients[index][name] = event.target.value
        }
        this.validateIngredients(ingredients)
        this.props.onIngredientsUpdated(ingredients, this.state.errors)
    }

    updateIngredientQuantity(event) {
        let ingredients = Object.assign([], this.props.ingredients)
        let index = parseInt(event.name.match(indexRegex)[1])
        let ingredient = ingredients[index]
        ingredient['quantity'] = event.value
        if(!event.isValid) {
            this.state.errors[event.name] = Object.assign(this.state.errors[event.name] || {}, getErrorMessage('invalidQuantity', ingredient.name ? ingredient.name : `Ingredient ${index + 1}`))
        }
        else {
            delete this.state.errors[event.name]
        }
        this.setState(this.state)
        this.props.onIngredientsUpdated(ingredients, this.state.errors)
    }

    addIngredient(event) {
        event.preventDefault()
        let ingredients = Object.assign([], this.props.ingredients)
        ingredients.push({})
        this.validateIngredients(ingredients)
        this.props.onIngredientsUpdated(ingredients, this.state.errors)
    }

    moveIngredient(event, i, moveUp) {
        event.preventDefault()
        let ingredients = Object.assign([], this.props.ingredients)
        if((moveUp && i === 0) || (!moveUp && i === ingredients.length - 1)) {
            return
        }
        let ingredient = ingredients[i]
        ingredients.splice(i, 1)
        ingredients.splice(moveUp ? i - 1 : i + 1, 0, ingredient)
        this.validateIngredients(ingredients)
        this.props.onIngredientsUpdated(ingredients, this.state.errors)
    }

    deleteIngredient(event, i) {
        event.preventDefault()
        let ingredients = Object.assign([], this.props.ingredients)
        ingredients.splice(i, 1)
        this.validateIngredients(ingredients)
        this.props.onIngredientsUpdated(ingredients, this.state.errors)
    }

    render() {
        let ingredientsList = this.props.ingredients.map((ing, i) => (
            <tr key={i}>
                <td>
                    <a href="" onClick={(e) => this.moveIngredient(e, i, true)} style={ingredientChangeLinkStyle} title="Move Up">&#8593;</a>
                    <a href="" onClick={(e) => this.moveIngredient(e, i, false)} style={ingredientChangeLinkStyle} title="Move Down">&#8595;</a>
                    <a href="" onClick={(e) => this.deleteIngredient(e, i)} style={ingredientChangeLinkStyle} title="Delete">X</a>
                </td>
                <td><InputFraction name={`ingredients[${i}].quantity`} value={ing.quantity} onChange={this.updateIngredientQuantity} style={textboxStyle} /></td>
                <td><input type="text" name={`ingredients[${i}].quantityDetail`} value={ing.quantityDetail} onChange={this.updateIngredient} style={textboxStyle} /></td>
                <td>
                    <select name={`ingredients[${i}].quantityUnit`} value={ing.quantityUnit} onChange={this.updateIngredient} >
                        <option></option>
                        <optgroup label="Measurement Units">
                            <option>teaspoon</option>
                            <option>tablespoon</option>
                            <option>cup</option>
                            <option>ounce</option>
                            <option>pound</option>
                        </optgroup>
                        <optgroup label="Other Units">
                            <option>can</option>
                            <option>jar</option>
                            <option>head</option>
                            <option>clove</option>
                        </optgroup>
                    </select>
                </td>
                <td><input type="text" name={`ingredients[${i}].name`} value={ing.name} onChange={this.updateIngredient} style={{width: '150px'}} /></td>
                <td><input type="text" name={`ingredients[${i}].preparation`} value={ing.preparation} onChange={this.updateIngredient} style={{width: '100px'}} /></td>
                <td><input type="checkbox" name="{`ingredients[${i}].required`}" defaultChecked={ing.required} /></td>
            </tr>
        ))
        ingredientsList.push(<tr key={ingredientsList.length}><td colSpan="7"><a href="" onClick={this.addIngredient}>Add Ingredient</a></td></tr>)

        return (
            <table id="tblIngredients">
                <thead>
                    <tr>
                        <th></th>
                        <th>Quantity</th>
                        <th>Quantity<br />Detail</th>
                        <th>Unit</th>
                        <th>Name</th>
                        <th>Preparation</th>
                        <th>Required</th>
                    </tr>
                </thead>
                <tbody>
                    {ingredientsList}
                </tbody>
            </table>
        )
    }
}

export default Radium(IngredientsEdit)

const indexRegex = /\[([0-9]+)\]/

function getErrorMessage(errorMessageName, ingredientName) {
    let errorMessage = Object.assign({}, errorMessages[errorMessageName])
    errorMessage.message = errorMessage.message.replace('{ingredientName}', ingredientName)
    return errorMessage
}

const errorMessages = {
    missingName: {
        message: 'All ingredients must have a name.',
    },
    invalidQuantity: {
        message: 'Quantity for "{ingredientName}" must be a positive number.',
    }
}

const textboxStyle = {
    width: '50px'
}

const ingredientChangeLinkStyle = {
    textDecoration: 'none',
    fontSize: '16pt',
    paddingRight: '6px',
    verticalAlign: 'middle'
}