package de.kunze.studhelper.view.core;

import org.apache.wicket.Page;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.authroles.authorization.strategies.role.metadata.MetaDataRoleAuthorizationStrategy;
import org.apache.wicket.markup.html.WebPage;

import de.kunze.studhelper.view.pages.admin.user.User;
import de.kunze.studhelper.view.pages.admin.degreecourse.DegreeCourse;
import de.kunze.studhelper.view.pages.admin.department.Department;
import de.kunze.studhelper.view.pages.admin.index.AdminIndex;
import de.kunze.studhelper.view.pages.admin.lecture.Lecture;
import de.kunze.studhelper.view.pages.admin.modul.Modul;
import de.kunze.studhelper.view.pages.admin.part.Part;
import de.kunze.studhelper.view.pages.admin.university.University;
import de.kunze.studhelper.view.pages.login.Login;
import de.kunze.studhelper.view.pages.user.index.Index;
import de.kunze.studhelper.view.pages.user.lecture.UserLecture;
import de.kunze.studhelper.view.pages.user.lectureOverview.LectureOverview;
/**
 * This Class is the main Wicket Application Class.
 * 
 * @author Stefan Kunze
 * @version 1.0
 * @since 1.0
 */
public class StudHelperApplication extends AuthenticatedWebApplication {

	public StudHelperApplication() {
		super();
	}

	/**
	 * This gets the HomePage. For us this is the IndexPage for the normal user.
	 */
	@Override
	public Class<? extends Page> getHomePage() {
		return Index.class;
	}

	/**
	 * This make some init stuff. Also nice links are generated.
	 */
	@Override
	protected void init() {
		super.init();
		
		mountPage("/index", Index.class);
		mountPage("/admin", AdminIndex.class);
		mountPage("/university", University.class);
		mountPage("/department", Department.class);
		mountPage("/degreecourse", DegreeCourse.class);
		mountPage("/part", Part.class);
		mountPage("/modul", Modul.class);
		mountPage("/lecture", Lecture.class);
		mountPage("/user", User.class);
		mountPage("/login", Login.class);
		mountPage("/userLecture", UserLecture.class);
		mountPage("/lectureOverview", LectureOverview.class);
	
		MetaDataRoleAuthorizationStrategy.authorize(Index.class, "USER");
	
	}

	/**
	 * Returns the LoginPage
	 */
	@Override
	protected Class<? extends WebPage> getSignInPageClass() {
		return Login.class;
	}

	/**
	 * Returns the WebSessionClass
	 */
	@Override
	protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass() {
		return StudhelperWebSession.class;
	}

}
