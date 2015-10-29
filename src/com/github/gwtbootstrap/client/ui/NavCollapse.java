/*
 *  Copyright 2012 GWT-Bootstrap
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.github.gwtbootstrap.client.ui;

import com.github.gwtbootstrap.client.ui.base.DivWidget;
import com.github.gwtbootstrap.client.ui.constants.Constants;

//@formatter:off
/**
 * A container for things hidden when the window width is too small. Has to be
 * placed inside a {@link ResponsiveNavbar}.
 * 
 * <p>
 * <h3>UiBinder Usage:</h3>
 * 
 * <pre>
 * {@code
 * <b:ResponsiveNavbar>
 *     <b:Brand>Bootstrap</b:Brand>
 *     <b:NavCollapse>
 *         <b:Nav>
 *             <b:NavLink>Link 1</b:NavLink>
 *             <b:NavLink>Link 2</b:NavLink>
 *         </b:Nav>
 *         <b:NavForm size="1" />
 *         <b:Nav alignment="RIGHT">
 *             <b:NavLink>Link 3</b:NavLink>
 *         </b:Nav>
 *     </b:NavCollapse>
 * </b:ResponsiveNavbar>
 * }
 * </pre>
 * </p>
 * 
 * @since 2.0.4.0
 * 
 * @author Dominik Mayer
 * 
 * @see <a href="http://getbootstrap.com/2.3.2/components.html#navbar">Bootstrap documentation</a>
 */
//@formatter:on
public class NavCollapse extends DivWidget {

	/**
	 * Creates an empty widget.
	 */
	public NavCollapse() {
		super(Constants.NAV_COLLAPSE);
	}

}
