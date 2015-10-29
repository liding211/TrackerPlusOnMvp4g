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

import com.github.gwtbootstrap.client.ui.constants.Constants;

//@formatter:off
/**
 * FluidContainers (and {@link Container Containers} are the largest panels 
 * in the Bootstrap grid system.
 * 
 * <p>
 * They contain almost all the other widgets (mentionable exceptions are {@link
 * Navbar fixed Navbars}) in {@link FluidRow FluidRows} and 
 * {@link Column Columns}. In contrast to Containers, FluidContainers adapt 
 * their width to the width of the browser window. 
 * 
 * <p>
 * <h3>UiBinder Usage:</h3>
 * 
 * <pre>
 * {@code 
 * <b:FluidContainer>
 *     <b:FluidRow>
 *         <b:Column size="4">...</b:Column>
 *         <b:Column size="8">...</b:Column>
 *     </b:FluidRow>
 * </b:FluidContainer>}
 * </pre>
 * </p>
 * 
 * @since 2.0.4.0
 * 
 * @author Carlos Alexandro Becker
 * 
 * @see <a href="http://getbootstrap.com/2.3.2/scaffolding.html#layouts">Bootstrap documentation</a>
 * @see Row
 */
//@formatter:on
public class FluidContainer extends Container {

	/**
	 * Creates an empty FluidContainer.
	 */
	public FluidContainer() {
		setStylePrimaryName(Constants.CONTAINER_FLUID);
	}
}
