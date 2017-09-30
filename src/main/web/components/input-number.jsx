import React from 'react'
import { Link } from 'react-router'

class InputNumber extends React.Component {

	constructor(props, ...args) {
        super(props, ...args)

		this.onChange = this.onChange.bind(this)
		this.isValueValid = this.isValueValid.bind(this)
	}

	componentDidMount() {
		if(!this.isValueValid(this.props.value)) {
			this.props.onChange({
				name: this.props.name,
				value: this.props.value,
				isValid: false
			})
		}
	}

	onChange (event) {
		this.props.onChange({
			name: this.props.name,
			value: event.target.value,
			isValid: this.isValueValid(event.target.value)
		})
	}

	isValueValid(value) {
	    if(!this.props.required && value === '') {
	        return true
	    }
		const intValue = parseInt(value)
		return !isNaN(intValue) && intValue == value && intValue >= parseInt(this.props.min) && intValue <= parseInt(this.props.max)
	}

    render() {
        return (
            <input 
				type="text" 
				name={this.props.name}
				value={this.props.value || ''}
				style={this.props.style}
				onChange={this.onChange} 
			/>
        )
    }
}

InputNumber.propTypes = {
    required: React.PropTypes.bool
}

InputNumber.defaultProps = {
    required: true
}

export default InputNumber