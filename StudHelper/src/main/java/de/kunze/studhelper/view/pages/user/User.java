package de.kunze.studhelper.view.pages.user;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.request.resource.SharedResourceReference;

import de.kunze.studhelper.rest.transfer.user.UserTransfer;
import de.kunze.studhelper.view.pages.base.BasePage;
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
//						getModal().setContent(new CreateUniversity(getModal().getContentId(), ut));
//						getModal().setTitle("Universität anlegen");
//						getModal().setInitialHeight(150);
//						getModal().setInitialWidth(400);
//						getModal().show(target);
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
				getModal().setInitialWidth(400);
				getModal().show(target);
			}

		});

	}

	private class CreateUser extends Panel {

		private static final long serialVersionUID = 1L;

//		private Form<UniversityTransfer> form;
//
//		private UniversityTransfer ut = null;

		public CreateUser(String id) {
			super(id);
			initComponents();
		}

//		public CreateUniversity(String id, UniversityTransfer ut) {
//			super(id);
//			this.ut = ut;
//			initComponents();
//		}

		private void initComponents() {

//			this.form = new Form<UniversityTransfer>("createUniversityForm", new CompoundPropertyModel<UniversityTransfer>(new UniversityTransfer()));
//
//			this.form.add(new TextField<String>("name"));
//
//			if (this.ut != null) {
//				this.form.setModelObject(this.ut);
//			}
//
//			final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
//			feedbackPanel.setOutputMarkupId(true);
//
//			add(feedbackPanel);
//
//			this.form.add(new AjaxSubmitLink("submitUniversityForm", this.form) {
//
//				private static final long serialVersionUID = 1L;
//
//				@Override
//				protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
//
//					final UniversityTransfer newUniversity = (UniversityTransfer) form.getModelObject();
//
//					if (ut == null) {
//						if (rest.createUniversity(newUniversity)) {
//							refreshUniversity();
//							target.add(panel);
//
//							modal.close(target);
//						} else {
//							error("Es ist ein Fehler beim Erstellen der Universität aufgetreten!");
//						}
//					} else {
//						if (rest.updateUniversity(newUniversity)) {
//							refreshUniversity();
//							target.add(panel);
//
//							modal.close(target);
//						} else {
//							error("Es ist ein Fehler beim Aktualisieren der Universität aufgetreten!");
//						}
//					}
//				}
//
//				@Override
//				protected void onError(AjaxRequestTarget target, Form<?> form) {
//					target.add(feedbackPanel);
//				}
//			});
//
//			add(this.form);

		}

	}
	
}
