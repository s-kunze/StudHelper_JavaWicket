package de.kunze.studhelper.view.pages.degreecourse;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.kunze.studhelper.rest.transfer.backend.DegreeCourseTransfer;
import de.kunze.studhelper.view.pages.base.BasePage;
import de.kunze.studhelper.view.rest.RestDegreeCourse;

public class CreateDegreeCourse extends BasePage {

	private static final long serialVersionUID = 1L;

	private Form<DegreeCourseTransfer> form;

	private boolean isNew = true;

	private String depName = null;
	private Long depId = null;

	private String degName = null;
	private Integer degCp = null;
	private Long degId = null;

	public CreateDegreeCourse() {
		initComponents();
	}

	public CreateDegreeCourse(final PageParameters parameters) {

		try {

			String update = parameters.get("update").toString();
			this.depName = parameters.get("depName").toString();
			this.depId = new Long(parameters.get("depId").toInteger());

			this.degName = parameters.get("degName").toString();
			this.degCp = parameters.get("degCp").toInteger();
			this.degId = new Long(parameters.get("degId").toInteger());

			
			
			if ("1".equals(update)) {
				this.isNew = false;
			}

		} catch (Exception e) {
			// Well, we have no Parameters, that's not a fault...
		}

		initComponents();
	}

	private void initComponents() {

		this.form = new Form<DegreeCourseTransfer>("createDegreeCourseForm",
				new CompoundPropertyModel<DegreeCourseTransfer>(
						new DegreeCourseTransfer()));

		this.form.add(new TextField<String>("name"));
		this.form.add(new TextField<Integer>("creditPoints"));

		if (this.degName != null) {
			this.form.getModel().getObject().setName(this.degName);
		}

		if(this.degCp != null) {
			this.form.getModel().getObject().setCreditPoints(this.degCp);
		}
		
		if (this.degId != null) {
			this.form.getModel().getObject().setId(this.degId);
		}

		final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
		feedbackPanel.setOutputMarkupId(true);

		add(feedbackPanel);

		this.form.add(new AjaxSubmitLink("submitDegreeCourseForm", this.form) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {

				final DegreeCourseTransfer dt = (DegreeCourseTransfer) form.getModelObject();

				/** Jetzt Speichern! */
				RestDegreeCourse rest = new RestDegreeCourse();

				if (isNew) {
					if (rest.createDegreeCourse(depId.toString(), dt)) {

						PageParameters parameters = new PageParameters();
						parameters.add("depId", depId.toString());

						setResponsePage(DegreeCourse.class, parameters);
					} else {
						/** Fehler anzeigen */
						error("Es ist ein Fehler beim Erstellen des Studiengangs aufgetreten!");
					}
				} else {
					if (rest.updateDegreeCourse(dt)) {
						/** Zu University wechseln */

						PageParameters parameters = new PageParameters();
						parameters.add("depId", depId.toString());

						setResponsePage(DegreeCourse.class, parameters);
					} else {
						/** Fehler anzeigen */
						error("Es ist ein Fehler beim Aktualisieren des Studiengangs aufgetreten!");
					}
				}

			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				target.add(feedbackPanel);
			}
		});

		add(new Label("msgCreateDegreeCourse", "Studiengang für " + this.depName
				+ " erstellen"));
		add(this.form);

	}

}
