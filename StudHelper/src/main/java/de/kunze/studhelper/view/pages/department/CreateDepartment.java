package de.kunze.studhelper.view.pages.department;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.kunze.studhelper.rest.transfer.backend.DepartmentTransfer;
import de.kunze.studhelper.view.pages.base.BasePage;
import de.kunze.studhelper.view.pages.university.University;
import de.kunze.studhelper.view.rest.RestDepartment;

public class CreateDepartment extends BasePage {

	private static final long serialVersionUID = 1L;

	private Form<DepartmentTransfer> form;

	private boolean isNew = true;

	private String uniName = null;
	private Long uniId = null;

	private String depName = null;
	private Long depId = null;

	public CreateDepartment() {
		initComponents();
	}

	public CreateDepartment(final PageParameters parameters) {

		try {

			String update = parameters.get("update").toString();
			this.uniName = parameters.get("uniName").toString();
			this.uniId = new Long(parameters.get("uniId").toInteger());

			this.depName = parameters.get("depName").toString();
			this.depId = new Long(parameters.get("depId").toInteger());

			if ("1".equals(update)) {
				this.isNew = false;
			}

		} catch (Exception e) {
			// Well, we have no Parameters, that's not a fault...
		}

		initComponents();
	}

	private void initComponents() {

		this.form = new Form<DepartmentTransfer>("createDepartmentForm",
				new CompoundPropertyModel<DepartmentTransfer>(
						new DepartmentTransfer()));

		this.form.add(new TextField<String>("name"));

		if (this.depName != null) {
			this.form.getModel().getObject().setName(this.depName);
		}

		if (this.depId != null) {
			this.form.getModel().getObject().setId(this.depId);
		}

		final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
		feedbackPanel.setOutputMarkupId(true);

		add(feedbackPanel);

		this.form.add(new AjaxSubmitLink("submitDepartmentForm", this.form) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {

				final DepartmentTransfer dt = (DepartmentTransfer) form
						.getModelObject();

				/** Jetzt Speichern! */
				RestDepartment rest = new RestDepartment();

				if (isNew) {
					if (rest.createDepartment(uniId.toString(), dt)) {

						PageParameters parameters = new PageParameters();
						parameters.add("uniId", uniId.toString());

						setResponsePage(Department.class, parameters);
					} else {
						/** Fehler anzeigen */
						error("Es ist ein Fehler beim Erstellen der Fakultät aufgetreten!");
					}
				} else {
					if (rest.updateDepartment(dt)) {
						/** Zu University wechseln */

						PageParameters parameters = new PageParameters();
						parameters.add("uniId", uniId.toString());

						setResponsePage(Department.class, parameters);
					} else {
						/** Fehler anzeigen */
						error("Es ist ein Fehler beim Aktualisieren der Fakultät aufgetreten!");
					}
				}

			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				target.add(feedbackPanel);
			}
		});

		add(new Label("msgCreateDepartment", "Fakultät für " + this.uniName
				+ " erstellen"));
		add(this.form);

	}

}