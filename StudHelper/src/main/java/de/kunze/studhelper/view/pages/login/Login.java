package de.kunze.studhelper.view.pages.login;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;

import de.kunze.studhelper.rest.transfer.backend.DegreeCourseTransfer;
import de.kunze.studhelper.rest.transfer.backend.DepartmentTransfer;
import de.kunze.studhelper.rest.transfer.backend.UniversityTransfer;
import de.kunze.studhelper.rest.transfer.user.NewUserTransfer;
import de.kunze.studhelper.rest.transfer.user.UserTransfer;
import de.kunze.studhelper.util.Util;
import de.kunze.studhelper.view.core.StudhelperWebSession;
import de.kunze.studhelper.view.pages.admin.index.AdminIndex;
import de.kunze.studhelper.view.pages.base.UserBasePage;
import de.kunze.studhelper.view.pages.user.index.Index;
import de.kunze.studhelper.view.rest.RestDepartment;
import de.kunze.studhelper.view.rest.RestUniversity;
import de.kunze.studhelper.view.rest.RestUser;

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
		
		add(new AjaxLink<Void>("newUser") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				getModal().setContent(new CreateUser(getModal().getContentId()));
				getModal().setTitle("User ändern");
				getModal().setInitialHeight(450);
				getModal().setInitialWidth(500);
				getModal().show(target);
			}
			
			
		});
		
		
		add(form);
	}
	
	private class CreateUser extends Panel {

		private static final long serialVersionUID = 1L;

		private Form<NewUserTransfer> form;

		private DropDownChoice<UniversityTransfer> ddcUni;
		private DropDownChoice<DepartmentTransfer> ddcDep;
		private DropDownChoice<DegreeCourseTransfer> ddcDeg;

		private NewUserTransfer newUserTransfer;
		private UniversityTransfer ut;
		private DepartmentTransfer dt;
		private DegreeCourseTransfer dct;
		
		private RestUser rest;
		
		public CreateUser(String id) {
			super(id);
			this.rest = new RestUser();
			initComponents();
		}
		
		private void refreshDepartment() {
			this.ddcDep.setChoices(new RestUniversity().getDepartmentsForUniversity(this.ut.getId().toString()));
		}

		private void refreshDegreeCourse() {
			this.ddcDeg.setChoices(new RestDepartment().getDegreeCourseForDepartment(this.dt.getId().toString()));
		}
		
		private void initComponents() {
			this.form = new Form<NewUserTransfer>("createUserForm", new CompoundPropertyModel<NewUserTransfer>(
					new NewUserTransfer()));

			this.form.add(new TextField<String>("firstname"));
			this.form.add(new TextField<String>("lastname"));
			this.form.add(new TextField<String>("username"));
			this.form.add(new PasswordTextField("password"));
			this.form.add(new PasswordTextField("password2"));

			if (this.newUserTransfer != null) {
				this.form.setModelObject(this.newUserTransfer);
			}

			final List<UniversityTransfer> universities = new RestUniversity().getUniversities();

			this.ddcUni = new DropDownChoice<UniversityTransfer>("ddCUniversity", new PropertyModel(this, "ut"),
					universities, new ChoiceRenderer<UniversityTransfer>("name", "id"));

			this.ddcUni.setModelObject(ut);
			this.ddcUni.setOutputMarkupId(true);
			this.ddcUni.add(new AjaxFormComponentUpdatingBehavior("onchange") {

				private static final long serialVersionUID = 1L;

				@Override
				protected void onUpdate(AjaxRequestTarget target) {
					ut = ddcUni.getModelObject();
					refreshDepartment();
					target.add(form);
				}
			});

			this.ddcDep = new DropDownChoice<DepartmentTransfer>("ddCDepartment", new PropertyModel(this, "dt"),
					new ArrayList<DepartmentTransfer>(), new ChoiceRenderer<DepartmentTransfer>("name", "id"));

			this.ddcDep.setModelObject(dt);
			this.ddcDep.setOutputMarkupId(true);
			this.ddcDep.add(new AjaxFormComponentUpdatingBehavior("onchange") {

				private static final long serialVersionUID = 1L;

				@Override
				protected void onUpdate(AjaxRequestTarget target) {
					dt = ddcDep.getModelObject();
					refreshDegreeCourse();
					target.add(form);
				}
			});

			this.ddcDeg = new DropDownChoice<DegreeCourseTransfer>("ddCDegreeCourse", new PropertyModel(this, "dct"),
					new ArrayList<DegreeCourseTransfer>(), new ChoiceRenderer<DegreeCourseTransfer>("name", "id"));

			this.ddcDeg.setModelObject(dct);
			this.ddcDeg.setOutputMarkupId(true);
			this.ddcDeg.add(new AjaxFormComponentUpdatingBehavior("onchange") {

				private static final long serialVersionUID = 1L;

				@Override
				protected void onUpdate(AjaxRequestTarget target) {
					dct = ddcDeg.getModelObject();
					target.add(form);
				}
			});

			this.form.add(this.ddcUni);
			this.form.add(this.ddcDep);
			this.form.add(this.ddcDeg);

			final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
			feedbackPanel.setOutputMarkupId(true);

			add(feedbackPanel);

			this.form.add(new AjaxSubmitLink("submitUserForm", this.form) {

				private static final long serialVersionUID = 1L;

				@Override
				protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
					
					final NewUserTransfer newUser = (NewUserTransfer) form.getModelObject();
					
					if(Util.isNullOrEmpty(newUser.getFirstname())) {
						error("Bitte einen Vorname eingeben!");
					}
					
					if(Util.isNullOrEmpty(newUser.getLastname())) {
						error("Bitte einen Nachnamen eingeben!");
					}
					
					if(Util.isNullOrEmpty(newUser.getUsername())) {
						error("Bitte einen Benutzernamen eingeben!");
					}
					
					if(Util.isNullOrEmpty(newUser.getPassword())) {
						error("Bitte ein Passwort eingeben!");
					}
					
					if(Util.isNullOrEmpty(newUser.getPassword2())) {
						error("Bitte bestätigen Sie Ihr Passwort eingeben!");
					}

					if(!newUser.getPassword().equals(newUser.getPassword2())) {
						error("Sie haben keine gleichen Passwörter eingegeben!");
					}

					String str = DigestUtils.md5Hex(newUser.getPassword());
					newUser.setPassword(str);
					newUser.setPassword2(newUser.getPassword());
					
					logger.debug(newUser.getFirstname());
					logger.debug(newUser.getLastname());
					logger.debug(newUser.getUsername());
					logger.debug(newUser.getPassword());
					logger.debug(newUser.getPassword2());
					
					logger.debug("Create New User");
					if (rest.createUser(newUser, dct.getId().toString())) {
						modal.close(target);
					} else {
						error("Es ist ein Fehler beim Erstellen des Users aufgetreten!");
					}
					
				}

				@Override
				protected void onError(AjaxRequestTarget target, Form<?> form) {
					target.add(feedbackPanel);
				}
			});

			add(this.form);
		}
		
		
	}
	
}
