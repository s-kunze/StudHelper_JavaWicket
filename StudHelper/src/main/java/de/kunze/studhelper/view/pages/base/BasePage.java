package de.kunze.studhelper.view.pages.base;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;

public class BasePage extends WebPage {

	private static final long serialVersionUID = 1L;

	public BasePage() {
		add(new Label("header", "StudHelper"));
		add(new Label("footer", "by Stefan Kunze"));
	}

}
