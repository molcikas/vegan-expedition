import React from 'react'
import { Link } from 'react-router'

const headerImage = require('../images/vegan-expedition.png');

class Layout extends React.Component {
    render() {
        return (
            <div id="page-container">
	            <div id="header">
					<img src="images/vegan-expedition.png" />
		            <div id="menu-bar">
			            <div id="menu">
				            <ul>
					            <li>
						            <Link to='/recipes'>Recipes</Link>
					            </li>
				            </ul>
			            </div>
		            </div>
	            </div>
	            <div id="content">
                    {this.props.children}
	            </div>
                <div id="footer">
                    <div id="copyright">&copy; {new Date().getFullYear()} Vegan Expedition.</div>
                </div>
            </div>
        )
    }
}

export default Layout
