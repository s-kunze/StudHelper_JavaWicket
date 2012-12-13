package de.kunze.studhelper.view.pages.admin.index;

import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;

import de.kunze.studhelper.view.pages.base.AdminBasePage;

@AuthorizeInstantiation(Roles.ADMIN)
public class AdminIndex extends AdminBasePage {

	private static final long serialVersionUID = 1L;
	 
    public AdminIndex() {
        add(new Label("welcome", "Hallo Benutzer: benutzer"));
    }
	
}
