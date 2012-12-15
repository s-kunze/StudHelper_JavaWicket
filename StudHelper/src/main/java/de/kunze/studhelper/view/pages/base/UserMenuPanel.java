package de.kunze.studhelper.view.pages.base;

import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;

import de.kunze.studhelper.view.pages.user.index.Index;
import de.kunze.studhelper.view.pages.user.lecture.UserLecture;
import de.kunze.studhelper.view.pages.user.lectureOverview.LectureOverview;

public class UserMenuPanel extends Panel {

	private static final long serialVersionUID = 1L;

	public UserMenuPanel(String id) {
		super(id);
		
		initComponents();
	}
	
	public void initComponents() {
		
		add(new BookmarkablePageLink<String>("index", Index.class));
		add(new BookmarkablePageLink<String>("lecture", UserLecture.class));
		add(new BookmarkablePageLink<String>("lectureOverview", LectureOverview.class));
	}
	
}
