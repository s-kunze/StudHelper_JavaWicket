package de.kunze.studhelper.view.core;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;

import de.kunze.studhelper.view.pages.degreecourse.DegreeCourse;
import de.kunze.studhelper.view.pages.department.Department;
import de.kunze.studhelper.view.pages.index.Index;
import de.kunze.studhelper.view.pages.university.University;

public class StudHelperApplication extends WebApplication {

	public StudHelperApplication() {
		super();
	}

	@Override
	public Class<? extends Page> getHomePage() {
		return Index.class;
	}
	
	@Override
    protected void init()
    {
        super.init();
    
        mountPage("/index", Index.class);
        mountPage("/university", University.class);
        mountPage("/department", Department.class);
        mountPage("/degreecourse", DegreeCourse.class);
    }

}
