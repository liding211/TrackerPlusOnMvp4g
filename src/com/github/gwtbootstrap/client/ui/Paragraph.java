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

import com.github.gwtbootstrap.client.ui.base.HtmlWidget;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets;


//@formatter:off
/**
 * Simple html paragraph widget.
 * 
 * @since 2.0.4.0
 * 
 * @author ohashi keisuke
 * @author Dominik Mayer
 */
//@formatter:on
public class Paragraph extends HtmlWidget {

	/**
	 * Creates an empty paragraph.
	 */
	public Paragraph() {
		super("p");
	}
	
	/**
	 * Creates a widget with  the html set..
	 * @param html content html
	 */
	public Paragraph(String html) {
		super("p", html);
	}

	/**
	 * get Inner Text
	 * @return innter Text
	 */
	public String getText() {
		return getElement().getInnerText();
	}

	/**
	 * set Inner Text
	 * @param text set Text
	 */
	public void setText(String text) {
		getElement().setInnerText(text);
	}
}
