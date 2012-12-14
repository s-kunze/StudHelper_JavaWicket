package de.kunze.studhelper.view.pages.login;

import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;

import de.kunze.studhelper.view.core.StudhelperWebSession;
import de.kunze.studhelper.view.pages.admin.index.AdminIndex;
import de.kunze.studhelper.view.pages.base.UserBasePage;
import de.kunze.studhelper.view.pages.user.index.Index;

public class Login extends UserBasePage {

	private static final long serialVersionUID = 1L;
	
	private String username;
	private String password;

	public Login() {
		
		final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
		feedbackPanel.setOutputMarkupId(true);

		add(feedbackPanel);
		
		add(new WebMarkupContainer("userMenuPanel"));
		
		Form<Void> form = new Form<Void>("login");
		
		TextField<String> username = new TextField<String>("username", new PropertyModel<String>(this, "username"));
		PasswordTextField password = new PasswordTextField("password", new PropertyModel<String>(this, "password"));		
		
		form.add(username);
		form.add(password);
		
		form.add(new AjaxSubmitLink("submit", form) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				String username = Login.this.username;
				String password = Login.this.password;
				
				StudhelperWebSession webSession = (StudhelperWebSession) Session.get();
				
				if(webSession.signIn(username, password)) {
					if(webSession.isUser()) {
						setResponsePage(Index.class);						
					} else if(webSession.isAdmin()) {
						setResponsePage(AdminIndex.class);
					}
				} else {
					error("Fehler beim Login");
				}
			}
			
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				// TODO Auto-generated method stub
				target.add(feedbackPanel);
			}
		});
		
		add(form);
	}
	
}
