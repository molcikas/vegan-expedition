import React from 'react'
import { Link } from 'react-router'
import { fetchRecipe, addRecipe, updateRecipe } from '../actions'
import Radium from 'radium'

const indexRegex = /\[([0-9]+)\]/

let store
let storeUnsubscribe
let baseStyles

class RecipeEdit extends React.Component {
    constructor(props, ...args) {
        super(props, ...args)
        store = props.route.store
        baseStyles = props.route.baseStyles

        this.onStoreChanged = this.onStoreChanged.bind(this)
        this.updateRecipeProperty = this.updateRecipeProperty.bind(this)
        this.updateIngredient = this.updateIngredient.bind(this)
        this.addIngredient = this.addIngredient.bind(this)
        this.moveIngredient = this.moveIngredient.bind(this)
        this.deleteIngredient = this.deleteIngredient.bind(this)
        this.updateInstruction = this.updateInstruction.bind(this)
        this.addInstruction = this.addInstruction.bind(this)

        storeUnsubscribe = store.subscribe(this.onStoreChanged)
        this.state = store.getState().recipe
        this.state.creatingNew = !!!props.params.recipeId
    }

    componentWillMount() {
        if(this.state.creatingNew) {
            this.state.recipeFetchState = 'FETCHED'
            this.state.recipe = {
                ingredients: [{}],
                instructions: [{}]
            }
            this.setState(this.state)
        }
        else {
            store.dispatch(fetchRecipe(this.props.params.recipeId))
        }
    }

    componentWillUnmount() {
        storeUnsubscribe()
    }

    onStoreChanged() {
        let newState = Object.assign({}, this.state, store.getState().recipe)
        if(newState.recipeFetchState === 'ADDED' || newState.recipeFetchState === 'UPDATED') {
            this.props.history.push(`/recipes/${newState.recipe.recipeId}`)
        }
        this.setState(newState)
    }

    updateRecipeProperty(event) {
        if(event.target.type === 'checkbox') {
            this.state.recipe[event.target.name] = !!!this.state.recipe[event.target.name]
        }
        else {
            this.state.recipe[event.target.name] = event.target.value
        }
        this.setState(this.state)
    }

    updateIngredient(event) {
        let index = parseInt(event.target.name.match(indexRegex)[1])
        let name = event.target.name.substring(event.target.name.indexOf('.') + 1)
        if(event.target.type === 'checkbox') {
            this.state.recipe.ingredients[index][name] = !!!this.state.recipe.ingredients[index][name]
        }
        else {
            this.state.recipe.ingredients[index][name] = event.target.value
        }
        this.setState(this.state)
    }

    addIngredient(event) {
        event.preventDefault()
        this.state.recipe.ingredients.push({})
        this.setState(this.state)
    }

    moveIngredient(event, i, moveUp) {
        event.preventDefault()
        if((moveUp && i === 0) || (!moveUp && i === this.state.recipe.ingredients.length - 1)) {
            return
        }
        let ingredient = this.state.recipe.ingredients[i]
        this.state.recipe.ingredients.splice(i, 1)
        this.state.recipe.ingredients.splice(moveUp ? i - 1 : i + 1, 0, ingredient)
        this.setState(this.state)
    }

    deleteIngredient(event, i) {
        event.preventDefault()
        this.state.recipe.ingredients.splice(i, 1)
        this.setState(this.state)
    }

    updateInstruction(event) {
        var index = parseInt(event.target.name.match(indexRegex)[1])
        this.state.recipe.instructions[index].description = event.target.value
        this.setState(this.state)
    }

    addInstruction(event) {
        event.preventDefault()
        this.state.recipe.instructions.push({})
        this.setState(this.state)
    }

    formSubmitted(event) {
        event.preventDefault()
        if(this.state.creatingNew) {
            store.dispatch(addRecipe(this.state.recipe))
        }
        else {
            store.dispatch(updateRecipe(this.state.recipe))
        }
    }

    render() {
        if(this.state.recipeFetchState === 'FETCHING') {
            return <div></div>
        }

        let ingredientsList = this.state.recipe.ingredients.map((ing, i) => (
            <tr key={ing.recipeIngredientId}>
                <td>
                    <a href="" onClick={(e) => this.moveIngredient(e, i, true)} style={ingredientChangeLinkStyle} title="Move Up">&#8593;</a>
                    <a href="" onClick={(e) => this.moveIngredient(e, i, false)} style={ingredientChangeLinkStyle} title="Move Down">&#8595;</a>
                    <a href="" onClick={(e) => this.deleteIngredient(e, i)} style={ingredientChangeLinkStyle} title="Delete">X</a>
                </td>
                <td><input type="text" name={`ingredients[${i}].quantity`} value={ing.quantity} onChange={this.updateIngredient} style={textboxStyle} /></td>
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
        ingredientsList.push(<tr><td colSpan="7"><a href="" onClick={this.addIngredient}>Add Ingredient</a></td></tr>)

        let instructionsList = this.state.recipe.instructions.map((ins, i) => 
            <li key={ins.recipeInstructionId} className="litext">
                <textarea rows="4" cols="80" name={`instruction[${i}]`} value={ins.description} onChange={this.updateInstruction}></textarea>
            </li>
        )

        return (
            <form onSubmit={(...args) => this.formSubmitted(...args)}>
                <h1>Name: <input type="text" name="name" value={this.state.recipe.name} onChange={this.updateRecipeProperty} /></h1>
                <span id="lblDescription"><textarea rows="4" cols="80" name="description" value={this.state.recipe.description} min="0" max="1000" onChange={this.updateRecipeProperty}></textarea></span>
                <table id="tblStats">
                    <tbody>
                        <tr>
                            <th><label htmlFor="PrepTime">Prep Time</label>:</th>
                            <td><input type="number" name="prepTime" value={this.state.recipe.prepTime} style={textboxStyle} min="0" max="1000" onChange={this.updateRecipeProperty} /> minutes</td>
                        </tr>
                        <tr>
                            <th><label htmlFor="CookTime">Cook Time</label>:</th>
                            <td><input type="number" name="cookTime" value={this.state.recipe.cookTime} style={textboxStyle} min="0" max="1000" onChange={this.updateRecipeProperty} /> minutes</td>
                        </tr>
                        <tr>
                            <th><label htmlFor="Servings">Servings</label>:</th>
                            <td><input type="number" name="servings" value={this.state.recipe.servings} style={textboxStyle} min="0" max="1000" onChange={this.updateRecipeProperty} /></td>
                        </tr>
                        <tr>
                            <th><label htmlFor="IsVegetarian">Vegetarian?</label></th>
                            <td><input type="checkbox" name="isVegetarian" checked={this.state.recipe.isVegetarian} min="0" max="1000" onChange={this.updateRecipeProperty} /></td>
                        </tr>
                        <tr>
                            <th><label htmlFor="IsVegan">Vegan?</label></th>
                            <td><input type="checkbox" name="isVegan" checked={this.state.recipe.isVegan} min="0" max="1000" onChange={this.updateRecipeProperty} /></td>
                        </tr>
                    </tbody>
                </table>
                <h2>Ingredients</h2>
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
                <h2>Instructions</h2>
                <ol>
                    {instructionsList}
                </ol>
                <div style={{marginBottom: '20px'}}>
                    <a href="" onClick={this.addInstruction}>Add Step</a>
                </div>
                <input type="submit" value={this.state.creatingNew ? 'Create' : 'Update'} />
	        </form>
        )
    }
}

export default Radium(RecipeEdit)

const textboxStyle = {
    width: '50px'
}

const ingredientChangeLinkStyle = {
    textDecoration: 'none',
    fontSize: '16pt',
    paddingRight: '6px',
    verticalAlign: 'middle'
}