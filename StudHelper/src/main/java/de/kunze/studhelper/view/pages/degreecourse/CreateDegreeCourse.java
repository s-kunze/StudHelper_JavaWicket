package de.kunze.studhelper.view.pages.degreecourse;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.kunze.studhelper.rest.transfer.backend.DegreeCourseTransfer;
import de.kunze.studhelper.rest.transfer.backend.DepartmentTransfer;
import de.kunze.studhelper.rest.transfer.backend.UniversityTransfer;
import de.kunze.studhelper.view.pages.base.BasePage;
import de.kunze.studhelper.view.pages.department.Department;
import de.kunze.studhelper.view.pages.university.University;
import de.kunze.studhelper.view.rest.RestDegreeCourse;
import de.kunze.studhelper.view.rest.RestDepartment;

public class CreateDegreeCourse extends Panel {

	private static final long serialVersionUID = 1L;

	private Form<DegreeCourseTransfer> form;

	private DepartmentTransfer dt = null;

	private DegreeCourseTransfer dct = null;

	private DegreeCourse degPage = null;

	private Department depPage = null;

	private RestDegreeCourse restDeg = null;

	public CreateDegreeCourse(String id, DepartmentTransfer dt, Department depPage) {
		super(id);
		this.dt = dt;
		this.depPage = depPage;
		this.restDeg = new RestDegreeCourse();

		initComponents();
	}

	public CreateDegreeCourse(String id, DepartmentTransfer dt, DegreeCourse degPage) {
		super(id);
		this.dt = dt;
		this.degPage = degPage;
		this.restDeg = new RestDegreeCourse();

		initComponents();
	}

	public CreateDegreeCourse(String id, DepartmentTransfer dt, DegreeCourseTransfer dct, DegreeCourse degPage) {
		super(id);
		this.dt = dt;
		this.dct = dct;
		this.degPage = degPage;
		this.restDeg = new RestDegreeCourse();

		initComponents();
	}

	private void initComponents() {

		this.form = new Form<DegreeCourseTransfer>("createDegreeCourseForm", new CompoundPropertyModel<DegreeCourseTransfer>(new DegreeCourseTransfer()));

		this.form.add(new TextField<String>("name"));
		this.form.add(new TextField<Integer>("creditPoints"));

		if (this.dct != null) {
			this.form.setModelObject(this.dct);
		}

		final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
		feedbackPanel.setOutputMarkupId(true);

		add(feedbackPanel);

		this.form.add(new AjaxSubmitLink("submitDegreeCourseForm", this.form) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {

				final DegreeCourseTransfer newDegreeCourse = (DegreeCourseTransfer) form.getModelObject();

				if (dct == null) {
					if (restDeg.createDegreeCourse(dt.getId().toString(), newDegreeCourse)) {

						if (degPage != null) {
							degPage.refreshDegreeCourse();
							target.add(degPage.getPanel());

							degPage.getModal().close(target);
						} else {
							depPage.getModal().close(target);
						}
					} else {
						/** Fehler anzeigen */
						error("Es ist ein Fehler beim Erstellen der Fakultät aufgetreten!");
					}
				} else {
					if (restDeg.updateDegreeCourse(newDegreeCourse)) {
						if (degPage != null) {
							degPage.refreshDegreeCourse();
							target.add(degPage.getPanel());

							degPage.getModal().close(target);
						} else {
							depPage.getModal().close(target);
						}
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

		add(new Label("msgCreateDegreeCourse", "Studiengang für " + this.dt.getName() + " erstellen"));
		add(this.form);

	}

}
