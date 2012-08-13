package de.kunze.studhelper.view.pages;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;

public class HelloWicket extends WebPage {

	private static final long serialVersionUID = 1L;
	 
    public HelloWicket() {
 
        add(new Label("message", "Hello World, Wicket"));
 
    }
	
}
