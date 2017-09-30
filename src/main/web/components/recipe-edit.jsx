import React from 'react'
import { Link } from 'react-router'
import Radium from 'radium'

import { fetchRecipe, addRecipe, updateRecipe } from '../actions'
import InputNumber from './input-number'
import IngredientsEdit from './ingredients-edit'

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
        this.updateRecipePropertyAndValidity = this.updateRecipePropertyAndValidity.bind(this)
        this.onIngredientsUpdated = this.onIngredientsUpdated.bind(this)
        this.updateInstruction = this.updateInstruction.bind(this)
        this.addInstruction = this.addInstruction.bind(this)
        this.formSubmitted = this.formSubmitted.bind(this)
        this.validate = this.validate.bind(this)

        storeUnsubscribe = store.subscribe(this.onStoreChanged)
        this.state = {
            recipe: store.getState().recipe,
            errors: {}
        }
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
            this.validate()
        }
        this.setState(this.state)
    }

    updateRecipePropertyAndValidity(updateEvent) {
        this.state.recipe[updateEvent.name] = updateEvent.value
        if(updateEvent.isValid) {
            delete this.state.errors[updateEvent.name]
        }
        else {
            this.state.errors[updateEvent.name] = Object.assign(this.state.errors[updateEvent.name] || {}, getErrorMessage(updateEvent.name))
        }
        this.setState(this.state)
    }

    onIngredientsUpdated(ingredients, errors) {
        this.state.recipe.ingredients = ingredients
        Object.keys(this.state.errors).forEach(field => {
            if(this.state.errors[field].isIngredientError && !errors[field]) {
                delete this.state.errors[field]
            }
        })
        Object.keys(errors).forEach(field => {
            this.state.errors[field] = Object.assign(this.state.errors[field] || {}, errors[field], { 
                isIngredientError: true,
                order: 5
            })
        })
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
        this.validate()
        if(Object.keys(this.state.errors).length) {
            Object.keys(this.state.errors).forEach(field => this.state.errors[field].presentBeforeFormSubmit = true)
            this.setState(this.state)
            return
        }
        if(this.state.creatingNew) {
            store.dispatch(addRecipe(this.state.recipe))
        }
        else {
            store.dispatch(updateRecipe(this.state.recipe))
        }
    }

    validate() {
        if(!this.state.recipe.name) {
            this.state.errors.name = Object.assign(this.state.errors.name || {}, getErrorMessage('name'))
        }
        else {
            delete this.state.errors.name
        }
        if(!this.state.recipe.description) {
            this.state.errors.description = Object.assign(this.state.errors.description || {}, getErrorMessage('description'))
        }
        else {
            delete this.state.errors.description
        }
        if(this.state.recipe.instructions.filter(ins => ins.description).length === 0) {
            this.state.errors.instructions = Object.assign(this.state.errors.instructions || {}, getErrorMessage('instructions'))
        }
        else {
            delete this.state.errors.instructions
        }
        this.setState(this.state)
    }

    render() {
        if(this.state.recipeFetchState === 'FETCHING') {
            return <div></div>
        }

        let instructionsList = this.state.recipe.instructions.map((ins, i) => 
            <li key={i} className="litext">
                <textarea rows="4" cols="80" name={`instruction[${i}]`} value={ins.description} onChange={this.updateInstruction}></textarea>
            </li>
        )

        let errorList = Object.keys(this.state.errors)
            .sort((x, y) => parseInt(this.state.errors[x].order) - parseInt(this.state.errors[y].order))
            .filter(field => this.state.errors[field].presentBeforeFormSubmit)
            .map((field, i) => 
                <li key={i}>{this.state.errors[field].message}</li>
            )

        return (
            <form onSubmit={(...args) => this.formSubmitted(...args)}>
                <h1>Name: <input type="text" name="name" value={this.state.recipe.name} onChange={this.updateRecipeProperty} /></h1>
                <span id="lblDescription"><textarea rows="4" cols="80" name="description" value={this.state.recipe.description} min="0" max="1000" onChange={this.updateRecipeProperty}></textarea></span>
                <table id="tblStats">
                    <tbody>
                        <tr>
                            <th><label htmlFor="prepTime">Prep Time</label>:</th>
                            <td><InputNumber name="prepTime" value={this.state.recipe.prepTime} style={textboxStyle} min="0" max="1000" onChange={this.updateRecipePropertyAndValidity} /> minutes</td>
                        </tr>
                        <tr>
                            <th><label htmlFor="cookTime">Cook Time</label>:</th>
                            <td><InputNumber name="cookTime" value={this.state.recipe.cookTime} style={textboxStyle} min="0" max="1000" onChange={this.updateRecipePropertyAndValidity} /> minutes</td>
                        </tr>
                        <tr>
                            <th><label htmlFor="servings">Servings</label>:</th>
                            <td><InputNumber name="servings" value={this.state.recipe.servings} style={textboxStyle} min="0" max="1000" onChange={this.updateRecipePropertyAndValidity} /></td>
                        </tr>
                        <tr>
                            <th><label htmlFor="isVegetarian">Vegetarian?</label></th>
                            <td><input type="checkbox" name="isVegetarian" checked={this.state.recipe.isVegetarian} min="0" max="1000" onChange={this.updateRecipeProperty} /></td>
                        </tr>
                        <tr>
                            <th><label htmlFor="isVegan">Vegan?</label></th>
                            <td><input type="checkbox" name="isVegan" checked={this.state.recipe.isVegan} min="0" max="1000" onChange={this.updateRecipeProperty} /></td>
                        </tr>
                        <tr>
                            <th><label htmlFor="points">Points</label></th>
                            <td><InputNumber name="points" value={this.state.recipe.points} style={textboxStyle} min="-1000" max="1000" onChange={this.updateRecipePropertyAndValidity} required={false} /></td>
                        </tr>
                    </tbody>
                </table>
                <h2>Ingredients</h2>
                <IngredientsEdit ingredients={this.state.recipe.ingredients} onIngredientsUpdated={this.onIngredientsUpdated} />
                <h2>Instructions</h2>
                <ol>
                    {instructionsList}
                </ol>
                <div style={{marginBottom: '20px'}}>
                    <a href="" onClick={this.addInstruction}>Add Step</a>
                </div>
                <input type="submit" value={this.state.creatingNew ? 'Create' : 'Update'} />
                <div style={errorList.length ? {} : {display: 'none'}}>
                    <h2>There are some problems with this recipe. Please fix them and try saving again.</h2>
                    <ul>
                        {errorList}
                    </ul>
                </div>
	        </form>
        )
    }
}

export default Radium(RecipeEdit)

function getErrorMessage(name) {
    return Object.assign({}, errorMessages[name])
}

const indexRegex = /\[([0-9]+)\]/

const errorMessages = {
    name: {
        message: 'A name is required.',
        order: 0
    },
    description: {
        message: 'A description is required.',
        order: 1
    },
    prepTime: {
        message: 'Prep time must be between 0 and 1000.',
        order: 2
    },
    cookTime: {
        message: 'Cook time must be between 0 and 1000.',
        order: 3
    },
    servings: {
        message: 'Servings must be between 0 and 1000.',
        order: 4
    },
    // ingredients order: 5
    instructions: {
        message: 'Instructions are required.',
        order: 6
    }
}

const textboxStyle = {
    width: '50px'
}

/*
<pre>
    {JSON.stringify(this.state, null, 2)}
</pre>
*/