package de.kunze.studhelper.view.pages.department;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;

import de.kunze.studhelper.rest.transfer.backend.DepartmentTransfer;
import de.kunze.studhelper.rest.transfer.backend.UniversityTransfer;
import de.kunze.studhelper.view.pages.university.University;
import de.kunze.studhelper.view.rest.RestDepartment;

public class CreateDepartment extends Panel {

	private static final long serialVersionUID = 1L;

	private Form<DepartmentTransfer> form;

	private UniversityTransfer ut = null;

	private DepartmentTransfer dt = null;

	private Department depPage = null;

	private University uniPage = null;

	private RestDepartment restDep = null;

	public CreateDepartment(String id, UniversityTransfer ut, University uniPage) {
		super(id);
		this.ut = ut;
		this.uniPage = uniPage;
		this.restDep = new RestDepartment();

		initComponents();
	}

	public CreateDepartment(String id, UniversityTransfer ut, Department depPage) {
		super(id);
		this.ut = ut;
		this.depPage = depPage;
		this.restDep = new RestDepartment();

		initComponents();
	}

	public CreateDepartment(String id, UniversityTransfer ut, DepartmentTransfer dt, Department depPage) {
		super(id);
		this.ut = ut;
		this.dt = dt;
		this.depPage = depPage;
		this.restDep = new RestDepartment();

		initComponents();
	}

	private void initComponents() {
		this.form = new Form<DepartmentTransfer>("createDepartmentForm", new CompoundPropertyModel<DepartmentTransfer>(new DepartmentTransfer()));

		this.form.add(new TextField<String>("name"));

		if (this.dt != null) {
			this.form.setModelObject(this.dt);
		}

		final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
		feedbackPanel.setOutputMarkupId(true);

		add(feedbackPanel);

		this.form.add(new AjaxSubmitLink("submitDepartmentForm", this.form) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {

				final DepartmentTransfer newDepartment = (DepartmentTransfer) form.getModelObject();

				if (dt == null) {
					if (restDep.createDepartment(ut.getId().toString(), newDepartment)) {

						if (depPage != null) {
							depPage.refreshDepartment();
							target.add(depPage.getPanel());

							depPage.getModal().close(target);
						} else {
							uniPage.getModal().close(target);
						}
					} else {
						/** Fehler anzeigen */
						error("Es ist ein Fehler beim Erstellen der Fakultät aufgetreten!");
					}
				} else {
					if (restDep.updateDepartment(newDepartment)) {
						if (depPage != null) {
							depPage.refreshDepartment();
							target.add(depPage.getPanel());

							depPage.getModal().close(target);
						} else {
							uniPage.getModal().close(target);
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

		add(new Label("msgCreateDepartment", "Fakultät für " + ut.getName() + " erstellen"));
		add(this.form);
	}

}
