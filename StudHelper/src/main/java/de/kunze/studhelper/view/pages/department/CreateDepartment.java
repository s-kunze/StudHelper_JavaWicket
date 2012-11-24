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
	
	private String nameUni = null;
	private String name = null;
	private Long id = null;
	
	public CreateDepartment() {
		initComponents();
	}
	
	public CreateDepartment(final PageParameters parameters) {
		
		String update = parameters.get("update").toString();
		this.nameUni = parameters.get("nameUni").toString();
		this.id = new Long(parameters.get("id").toInteger());
		
		if("1".equals(update)) {
			this.isNew = false;
		}
	
		initComponents();
	}

	private void initComponents() {	
		
		
		
		this.form = new Form<DepartmentTransfer>("createDepartmentForm",
				new CompoundPropertyModel<DepartmentTransfer>(
						new DepartmentTransfer()));

		this.form.add(new TextField<String>("name"));

		if(this.name != null) {
			this.form.getModel().getObject().setName(this.name);
		}
		
		if(this.id != null) {
			this.form.getModel().getObject().setId(this.id);
		}
		
		final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
		feedbackPanel.setOutputMarkupId(true);

		add(feedbackPanel);
		
		this.form.add(new AjaxSubmitLink("submitDepartmentForm", this.form) {
			
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				
				final DepartmentTransfer dt = (DepartmentTransfer) form.getModelObject();

				/** Jetzt Speichern! */
				RestDepartment rest = new RestDepartment();

				if(isNew) {
					if (rest.createDepartment(id.toString(), dt)) {
						
						PageParameters parameters = new PageParameters();
						parameters.add("uniId", id.toString());
						
						setResponsePage(Department.class, parameters);
					} else {
						/** Fehler anzeigen */
						error("Es ist ein Fehler beim Erstellen der Fakultät aufgetreten!");
					}
				} else {
					if (rest.updateDepartment(dt)) {
						/** Zu University wechseln */
						
						PageParameters parameters = new PageParameters();
						parameters.add("uniId", id.toString());
						
						setResponsePage(Department.class, parameters);
					} else {
						/** Fehler anzeigen */
						error("Es ist ein Fehler beim Aktualisieren der Fakultät aufgetreten!");
					}
				}
				
			}
			
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				target.add( feedbackPanel );
			}
		});

		add(new Label("msgCreateDepartment","Fakultät für " + this.nameUni + " erstellen"));
		add(this.form);

	}

}