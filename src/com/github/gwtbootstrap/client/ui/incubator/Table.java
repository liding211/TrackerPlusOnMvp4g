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
package com.github.gwtbootstrap.client.ui.incubator;

import com.github.gwtbootstrap.client.ui.base.ComplexWidget;
import com.github.gwtbootstrap.client.ui.constants.Constants;
import com.google.gwt.user.client.ui.Widget;

//@formatter:off
/**
 * Wrapper for an HTML Table.
 *
 * <p>Table Usage:</p>
 * <pre>
 * {@code
 * <b:Table>
 *     <b:TableHeader>Name</b:TableHeader>
 *     <b:TableHeader>Surname</b:TableHeader>
 *     <tr>
 *         <td>John</td>
 *         <td>Smith</td>
 *     </tr>
 * </b:Table>
 * }
 * </pre>
 *
 * @since 2.0.4.0
 * 
 * @author Dominik Mayer
 * 
 * @see <a href="http://getbootstrap.com/2.3.2/base-css.html#tables">Bootstrap documentation</a>
 */
//@formatter:on
public class Table extends ComplexWidget {

	private Header header = new Header();

	public Table() {
		super("table");
		super.add(header);
		setStylePrimaryName(Constants.TABLE);
	}

	/**
	 * Prints the rows in two different colors. Does not work in IE7 - IE8.
	 * 
	 * @param striped
	 */
	public void setStriped(boolean striped) {
		setStyleDependentName(Constants.STRIPED, striped);
	}

    public void setBordered(boolean bordered) {
        setStyleDependentName(Constants.BORDERED, bordered);
    }

    public void setCondensed(boolean condensed) {
        setStyleDependentName(Constants.CONDENSED, condensed);
    }

    public void setHover(boolean hover) {
        setStyleDependentName(Constants.HOVER, hover);
    }

	@Override
	public void add(Widget w) {
		if (w instanceof TableHeader)
			header.add(w);
		else
			super.add(w);
	}

	private class Header extends ComplexWidget {

		public Header() {
			super("thead");
		}
	}
}
