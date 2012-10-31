package de.kunze.studhelper.view.pages.index;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;

public class Index extends WebPage {

	private static final long serialVersionUID = 1L;
	 
    public Index() {
        add(new Label("welcome", "Welcome to StudHelper"));
    }
	
}
