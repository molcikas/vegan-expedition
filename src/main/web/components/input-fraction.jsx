import React from 'react'
import { Link } from 'react-router'

class InputFraction extends React.Component {

	constructor(props, ...args) {
        super(props, ...args)

		this.onChange = this.onChange.bind(this)
		this.onBlur = this.onBlur.bind(this)
		this.isValueValid = this.isValueValid.bind(this)

		this.state = {
			value: toFraction(props.value)
		}
	}

	componentWillReceiveProps(newProps) {
		this.state.value = toFraction(newProps.value)
		this.setState(this.state)
	}

	componentDidMount() {
		const decimalValue = toDecimal(this.state.value)
		if(!this.isValueValid(decimalValue)) {
			this.props.onChange({
				name: this.props.name,
				value: decimalValue,
				isValid: false
			})
		}
	}

	onChange(event) {
		this.state.value = event.target.value
		this.setState(this.state)
	}

	onBlur (event) {
		const decimalValue = toDecimal(event.target.value)
		this.props.onChange({
			name: this.props.name,
			value: decimalValue,
			isValid: this.isValueValid(decimalValue)
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
		if(this.props.renderAsSpan) {
			let fractionValue = toFraction(this.state.value)
			if(fractionValue === '1' && this.props.blankIfOne) {
				fractionValue = ''
			}
			return <span>{fractionValue}</span>
		}
		else {
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
}

InputFraction.defaultProps = {
	value: '',
	min: 0,
	max: 1000,
	renderAsSpan: false,
	blankIfOne: false
}

export default InputFraction

function toFraction(value) {
    if(!value || value != parseFloat(value)) {
        return value
    }
    return value
        .toString()

		// Trim trailing zeroes
        .replace(/([0-9])0+$/g, '$1')

		.replace(/\.125$/, ' 1/8')
		.replace(/\.2$/, ' 1/5')
        .replace(/\.25$/, ' 1/4')
		.replace(/\.333$/, ' 1/3')
		.replace(/\.375$/, ' 3/8')
		.replace(/\.4$/, ' 2/5')
        .replace(/\.5$/, ' 1/2')
		.replace(/\.6$/, ' 3/5')
		.replace(/\.625$/, ' 5/8')
		.replace(/\.666$/, ' 2/3')
		.replace(/\.667$/, ' 2/3')
		.replace(/\.75$/, ' 3/4')
		.replace(/\.8$/, ' 4/5')
		.replace(/\.875$/, ' 7/8')

		// Replace 2-digit precision decimals if 3-digit fails.
        .replace(/\.12$/, ' 1/8')
        .replace(/\.13$/, ' 1/8')
        .replace(/\.33$/, ' 1/3')
        .replace(/\.37$/, ' 3/8')
        .replace(/\.38$/, ' 3/8')
        .replace(/\.62$/, ' 5/8')
        .replace(/\.63$/, ' 5/8')
        .replace(/\.66$/, ' 2/3')
        .replace(/\.67$/, ' 2/3')
        .replace(/\.87$/, ' 7/8')
        .replace(/\.88$/, ' 7/8')

		// Do not show whole number if zero (e.g. 1 4/5 cups is ok, 0 4/5 is not)
		.replace(/^0 ([0-9])/, '$1')
}

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