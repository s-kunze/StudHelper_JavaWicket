package de.kunze.studhelper.view.pages.index;

import org.apache.wicket.markup.html.basic.Label;

import de.kunze.studhelper.view.pages.base.BasePage;

public class Index extends BasePage {

	private static final long serialVersionUID = 1L;
	 
    public Index() {
        add(new Label("welcome", "Das ist die Startseite"));
    }
	
}
