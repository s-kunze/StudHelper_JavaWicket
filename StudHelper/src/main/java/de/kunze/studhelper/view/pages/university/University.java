package de.kunze.studhelper.view.pages.university;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.resource.SharedResourceReference;

import de.kunze.studhelper.rest.transfer.backend.UniversityTransfer;
import de.kunze.studhelper.view.pages.base.BasePage;
import de.kunze.studhelper.view.pages.department.CreateDepartment;
import de.kunze.studhelper.view.pages.department.Department;
import de.kunze.studhelper.view.rest.RestUniversity;

public class University extends BasePage {

	private static final long serialVersionUID = 1L;

	private DataView<UniversityTransfer> dataView = null;

	private List<UniversityTransfer> list = null;

	private WebMarkupContainer panel = null;

	private RestUniversity rest = null;
	
	public University() {
		rest = new RestUniversity();
		initComponents();
	}

	protected void refreshUniversity() {
		RestUniversity rest = new RestUniversity();

		list.clear();
		list.addAll(rest.getUniversities());
	}

	private void initComponents() {
		this.list = rest.getUniversities();

		this.panel = new WebMarkupContainer("dataviewPanel");

		this.dataView = new DataView<UniversityTransfer>("universityTable", new ListDataProvider<UniversityTransfer>(list)) {
			private static final long serialVersionUID = 1L;

			public void populateItem(final Item<UniversityTransfer> item) {
				final UniversityTransfer ut = (UniversityTransfer) item.getModelObject();
				item.add(new Label("name", ut.getName()));
				
				item.add(new AjaxLink<Void>("addDepartment") {

					private static final long serialVersionUID = 1L;

					@Override
					public void onClick(AjaxRequestTarget target) {
						getModal().setContent(new CreateDepartment(getModal().getContentId(), ut, University.this));
						getModal().setTitle("Universität anlegen");
						getModal().setInitialHeight(150);
						getModal().setInitialWidth(400);
						getModal().show(target);
					}

				}.add(new Image("imageAddDepartment", new SharedResourceReference(BasePage.class, "../../gfx/add.png"))));
				
				
				item.add(new AjaxLink<Void>("editUniversity") {

					private static final long serialVersionUID = 1L;

					@Override
					public void onClick(AjaxRequestTarget target) {
						getModal().setContent(new CreateUniversity(getModal().getContentId(), ut));
						getModal().setTitle("Universität anlegen");
						getModal().setInitialHeight(150);
						getModal().setInitialWidth(400);
						getModal().show(target);
					}

				}.add(new Image("imageEditUniversity", new SharedResourceReference(BasePage.class, "../../gfx/edit.png"))));
				
				item.add(new AjaxLink<Void>("deleteUniversity") {

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
										rest.deleteUniversity(Long.toString(ut.getId()));
										refreshUniversity();
										
										target.add(panel);
										modal.close(target);
									}

								});
							}

						});
						getModal().setTitle("Universität löschen");
						getModal().setInitialHeight(150);
						getModal().setInitialWidth(400);
						getModal().show(target);

					}

				}.add(new Image("imageDeleteUniversity", new SharedResourceReference(BasePage.class, "../../gfx/delete.png"))));
			}
		};

		dataView.setOutputMarkupId(true);
		panel.setOutputMarkupId(true);

		panel.add(dataView);

		add(panel);

		add(new AjaxLink<Void>("newUniversity") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				getModal().setContent(new CreateUniversity(getModal().getContentId()));
				getModal().setTitle("Universität anlegen");
				getModal().setInitialHeight(150);
				getModal().setInitialWidth(400);
				getModal().show(target);
			}

		});

	}

	private class CreateUniversity extends Panel {

		private static final long serialVersionUID = 1L;

		private Form<UniversityTransfer> form;

		private UniversityTransfer ut = null;

		public CreateUniversity(String id) {
			super(id);
			initComponents();
		}

		public CreateUniversity(String id, UniversityTransfer ut) {
			super(id);
			this.ut = ut;
			initComponents();
		}

		private void initComponents() {

			this.form = new Form<UniversityTransfer>("createUniversityForm", new CompoundPropertyModel<UniversityTransfer>(new UniversityTransfer()));

			this.form.add(new TextField<String>("name"));

			if (this.ut != null) {
				this.form.setModelObject(this.ut);
			}

			final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
			feedbackPanel.setOutputMarkupId(true);

			add(feedbackPanel);

			this.form.add(new AjaxSubmitLink("submitUniversityForm", this.form) {

				private static final long serialVersionUID = 1L;

				@Override
				protected void onSubmit(AjaxRequestTarget target, Form<?> form) {

					final UniversityTransfer newUniversity = (UniversityTransfer) form.getModelObject();

					if (ut == null) {
						if (rest.createUniversity(newUniversity)) {
							refreshUniversity();
							target.add(panel);

							modal.close(target);
						} else {
							error("Es ist ein Fehler beim Erstellen der Universität aufgetreten!");
						}
					} else {
						if (rest.updateUniversity(newUniversity)) {
							refreshUniversity();
							target.add(panel);

							modal.close(target);
						} else {
							error("Es ist ein Fehler beim Aktualisieren der Universität aufgetreten!");
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
