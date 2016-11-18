import React from 'react'
import { incrementCount } from '../actions'
import Radium from 'radium'
import qajax from 'qajax'

let store
let baseStyles

class App extends React.Component {
    constructor(props, ...args) {
        super(props, ...args)
        store = props.route.store
        baseStyles = props.route.baseStyles
    }

    render() {
        store.dispatch(incrementCount())
        store.dispatch(incrementCount())

        return (
            <div style={myStyle}>
             <div>Main App Works!</div>
             <div>{JSON.stringify(store.getState())}</div>
            </div>
        )
    }
}

let myStyle = {
    backgroundColor: 'green'
}

export default Radium(App)
