package de.kunze.studhelper.view.core;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.WebApplication;

import de.kunze.studhelper.view.pages.index.Index;

public class StudHelperApplication extends WebApplication {

	@Override
	public Class<? extends Page> getHomePage() {
		return Index.class;
	}

}
