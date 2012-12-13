package de.kunze.studhelper.view.pages.user.index;

import org.apache.wicket.Session;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;

import de.kunze.studhelper.view.core.StudhelperWebSession;
import de.kunze.studhelper.view.pages.base.UserBasePage;
import de.kunze.studhelper.view.pages.base.UserMenuPanel;

@AuthorizeInstantiation("USER")
public class Index extends UserBasePage {

	private static final long serialVersionUID = 1L;

	public Index() {
		
		StudhelperWebSession session = (StudhelperWebSession) Session.get();
		
		add(new UserMenuPanel("userMenuPanel"));
		
		add(new Label("welcome", "Das ist die Startseite " + session.getUsername()));
	}
	
}
