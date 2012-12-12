package de.kunze.studhelper.view.pages.user;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.resource.SharedResourceReference;

import de.kunze.studhelper.rest.transfer.backend.DegreeCourseTransfer;
import de.kunze.studhelper.rest.transfer.backend.DepartmentTransfer;
import de.kunze.studhelper.rest.transfer.backend.UniversityTransfer;
import de.kunze.studhelper.rest.transfer.user.NewUserTransfer;
import de.kunze.studhelper.rest.transfer.user.UserTransfer;
import de.kunze.studhelper.util.Util;
import de.kunze.studhelper.view.pages.base.BasePage;
import de.kunze.studhelper.view.rest.RestDepartment;
import de.kunze.studhelper.view.rest.RestUniversity;
import de.kunze.studhelper.view.rest.RestUser;

public class User extends BasePage {

	private static final long serialVersionUID = 1L;

	private DataView<UserTransfer> dataView = null;

	private List<UserTransfer> list = null;

	private WebMarkupContainer panel = null;

	private RestUser rest = null;

	public User() {
		rest = new RestUser();
		initComponents();
	}

	protected void refreshUser() {
		list.clear();
		list.addAll(rest.getUsers());
	}

	private void initComponents() {
		this.list = rest.getUsers();

		this.panel = new WebMarkupContainer("dataviewPanel");

		this.dataView = new DataView<UserTransfer>("userTable", new ListDataProvider<UserTransfer>(list)) {
			private static final long serialVersionUID = 1L;

			public void populateItem(final Item<UserTransfer> item) {
				final UserTransfer user = (UserTransfer) item.getModelObject();
				item.add(new Label("firstname", user.getFirstname()));
				item.add(new Label("lastname", user.getLastname()));
				item.add(new Label("username", user.getUsername()));

				item.add(new AjaxLink<Void>("editUser") {

					private static final long serialVersionUID = 1L;

					@Override
					public void onClick(AjaxRequestTarget target) {
						getModal().setContent(new CreateUser(getModal().getContentId(), user));
						getModal().setTitle("User ändern");
						getModal().setInitialHeight(450);
						getModal().setInitialWidth(500);
						getModal().show(target);
					}

				}.add(new Image("imageEditUser", new SharedResourceReference(BasePage.class, "../../gfx/edit.png"))));

				item.add(new AjaxLink<Void>("deleteUser") {

					private static final long serialVersionUID = 1L;

					@Override
					public void onClick(AjaxRequestTarget target) {

						getModal().setContent(new ConfirmPanel(getModal().getContentId()) {

							private static final long serialVersionUID = 1L;

							@Override
							protected void setOkButton() {
								add(new AjaxLink<Void>("yes") {

									private static final long serialVersionUID = 1L;

									@Override
									public void onClick(AjaxRequestTarget target) {
										rest.deleteUser(Long.toString(user.getId()));
										refreshUser();

										target.add(panel);
										modal.close(target);
									}

								});
							}

						});
						getModal().setTitle("User löschen");
						getModal().setInitialHeight(150);
						getModal().setInitialWidth(400);
						getModal().show(target);

					}

				}.add(new Image("imageDeleteUser", new SharedResourceReference(BasePage.class, "../../gfx/delete.png"))));
			}
		};

		dataView.setOutputMarkupId(true);
		panel.setOutputMarkupId(true);

		panel.add(dataView);

		add(panel);

		add(new AjaxLink<Void>("newUser") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				getModal().setContent(new CreateUser(getModal().getContentId()));
				getModal().setTitle("User anlegen");
				getModal().setInitialHeight(450);
				getModal().setInitialWidth(500);
				getModal().show(target);
			}

		});

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

		public CreateUser(String id) {
			super(id);
			initComponents();
		}

		public CreateUser(String id, UserTransfer userTransfer) {
			super(id);
			this.newUserTransfer = new NewUserTransfer();
			this.newUserTransfer.setFirstname(userTransfer.getFirstname());
			this.newUserTransfer.setLastname(userTransfer.getLastname());
			this.newUserTransfer.setUsername(userTransfer.getUsername());

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
					target.add(panel);
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
					
					//MD5
					newUser.setPassword(DigestUtils.md5Hex(newUser.getPassword()));
					newUser.setPassword2(newUser.getPassword());
					
					logger.debug(newUser.getFirstname());
					logger.debug(newUser.getLastname());
					logger.debug(newUser.getUsername());
					logger.debug(newUser.getPassword());
					logger.debug(newUser.getPassword2());
					
					if (newUserTransfer == null) {
						logger.debug("Create New User");
						if (rest.createUser(newUser, dct.getId().toString())) {
							refreshUser();
							target.add(panel);

							modal.close(target);
						} else {
							error("Es ist ein Fehler beim Erstellen des Users aufgetreten!");
						}
					} else {
						UserTransfer userTransfer = new UserTransfer();
						
						userTransfer.setFirstname(newUser.getFirstname());
						userTransfer.setLastname(newUser.getLastname());
						userTransfer.setUsername(newUser.getUsername());
						
						if (rest.updateUser(userTransfer)) {
							refreshUser();
							target.add(panel);

							modal.close(target);
						} else {
							error("Es ist ein Fehler beim Aktualisieren des Users aufgetreten!");
						}
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
