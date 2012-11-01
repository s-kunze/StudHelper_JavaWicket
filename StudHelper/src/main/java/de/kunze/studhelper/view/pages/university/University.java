package de.kunze.studhelper.view.pages.university;

import org.apache.wicket.markup.html.basic.Label;

import de.kunze.studhelper.view.pages.base.BasePage;

public class University extends BasePage {

	private static final long serialVersionUID = 1L;

	public University() {
		add(new Label("msgUniversity", "Hier können Sie die aktuellen Universitäten verwalten."));
	}
	
}
