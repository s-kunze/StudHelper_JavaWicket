package de.kunze.studhelper.view.pages.department;

import org.apache.wicket.markup.html.basic.Label;

import de.kunze.studhelper.view.pages.base.BasePage;

public class Department extends BasePage {

	private static final long serialVersionUID = 1L;

	public Department() {
		add(new Label("msgDepartment", "Hier können Sie die aktuellen Fakultäten zu einer Universität ansehen."));
	}
}
