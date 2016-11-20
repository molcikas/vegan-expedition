import React from 'react'

class Layout extends React.Component {
    render() {
        return (
            <div id="page-container">
	            <div id="header">
		            <div id="menu-bar">
			            <div id="menu">
				            <ul>
					            <li>
						            <a href="/recipe">Recipes</a>
					            </li>
				            </ul>
			            </div>
		            </div>
	            </div>
	            <div id="content">
                    {this.props.children}
	            </div>
                <div id="footer">
                    <a href="/Login">Login</a>
                    <div id="copyright">Vegan Expedition. Copyright 2012. All rights reserved.</div>
                </div>
            </div>
        )
    }
}

export default Layout
