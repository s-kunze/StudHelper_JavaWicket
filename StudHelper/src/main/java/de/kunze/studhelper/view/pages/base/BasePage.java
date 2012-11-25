package de.kunze.studhelper.view.pages.base;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kunze.studhelper.view.pages.degreecourse.DegreeCourse;
import de.kunze.studhelper.view.pages.department.Department;
import de.kunze.studhelper.view.pages.index.Index;
import de.kunze.studhelper.view.pages.university.University;

public class BasePage extends WebPage {

	private static final long serialVersionUID = 1L;

	protected Logger logger = LoggerFactory.getLogger(BasePage.class);
	
	public BasePage() {
		add(new BookmarkablePageLink<String>("index", Index.class));
		add(new BookmarkablePageLink<String>("university", University.class));
		add(new BookmarkablePageLink<String>("department", Department.class));
		add(new BookmarkablePageLink<String>("degreeCourse", DegreeCourse.class));
		
		add(new Label("header", "StudHelper"));
		add(new Label("footer", "by Stefan Kunze"));
	}

}
