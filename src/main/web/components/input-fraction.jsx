import React from 'react'
import { Link } from 'react-router'

class InputFraction extends React.Component {

	constructor(props, ...args) {
        super(props, ...args)

		this.onChange = this.onChange.bind(this)
		this.onBlur = this.onBlur.bind(this)
		this.isValueValid = this.isValueValid.bind(this)

		this.state = {
			value: props.value
		}
	}

	componentWillReceiveProps(newProps) {
		this.state.value = newProps.value
		this.setState(this.state)
	}

	componentDidMount() {
		if(!this.isValueValid(this.state.value)) {
			this.props.onChange({
				name: this.props.name,
				value: this.state.value,
				isValid: false
			})
		}
	}

	onChange(event) {
		this.state.value = event.target.value
		this.setState(this.state)
	}

	onBlur (event) {
		this.props.onChange({
			name: this.props.name,
			value: event.target.value,
			isValid: this.isValueValid(event.target.value)
		})
	}

	isValueValid(value) {
		if(value == null || value === '') {
			return true
		}
		let decimalValue = toDecimal(value)
		let floatDecimalValue = parseFloat(decimalValue)
		return !isNaN(floatDecimalValue) && floatDecimalValue == decimalValue && floatDecimalValue >= parseFloat(this.props.min) && floatDecimalValue <= parseFloat(this.props.max)
	}

    render() {
		return (
			<input 
				type="text" 
				name={this.props.name}
				value={this.state.value || ''}
				style={this.props.style}
				onChange={this.onChange}
				onBlur={this.onBlur} 
			/>
		)
    }
}

InputFraction.defaultProps = {
	value: '',
	min: 0,
	max: 1000
}

export default InputFraction

function toDecimal(value) {
	if(!value) {
		return value
	}
	value = value.toString()
	if(value.indexOf('/') === -1) {
		return value
	}
	let compoundFractionSplit = value.split(' ')
	let fractionSplit = compoundFractionSplit[compoundFractionSplit.length - 1].split('/')
	if(!fractionSplit[1]) {
		return value
	}
	let wholeNumber = compoundFractionSplit.length === 2 ? parseInt(compoundFractionSplit[0]) : 0
	if(isNaN(wholeNumber)) {
		return value
	}
	let decimalValue = (fractionSplit[0] / fractionSplit[1] + wholeNumber).toFixed(3)
	if(isNaN(decimalValue)) {
		return value
	}
	return decimalValue
}