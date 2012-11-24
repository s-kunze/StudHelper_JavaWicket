package de.kunze.studhelper.view.pages.university;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.kunze.studhelper.rest.transfer.backend.UniversityTransfer;
import de.kunze.studhelper.view.pages.base.BasePage;
import de.kunze.studhelper.view.rest.RestUniversity;

public class CreateUniversity extends BasePage {

	private static final long serialVersionUID = 1L;

	private Form<UniversityTransfer> form;

	private boolean isNew = true;
	
	private String name = null;
	private Long id = null;
	
	public CreateUniversity() {
		initComponents();
	}
	
	public CreateUniversity(final PageParameters parameters) {
		
		String update = parameters.get("update").toString();
		this.name = parameters.get("name").toString();
		this.id = new Long(parameters.get("id").toInteger());
		
		if("1".equals(update)) {
			this.isNew = false;
		}
	
		initComponents();
	}

	private void initComponents() {	
		
		this.form = new Form<UniversityTransfer>("createUniversityForm",
				new CompoundPropertyModel<UniversityTransfer>(
						new UniversityTransfer()));

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
		
		this.form.add(new AjaxSubmitLink("submitUniversityForm", this.form) {
			
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				
				final UniversityTransfer ut = (UniversityTransfer) form.getModelObject();

				/** Jetzt Speichern! */
				RestUniversity rest = new RestUniversity();

				if(isNew) {
					if (rest.createUniversity(ut)) {
						/** Zu University wechseln */
						setResponsePage(University.class);
					} else {
						/** Fehler anzeigen */
						error("Es ist ein Fehler beim Erstellen der Universität aufgetreten!");
					}
				} else {
					if (rest.updateUniversity(ut)) {
						/** Zu University wechseln */
						setResponsePage(University.class);
					} else {
						/** Fehler anzeigen */
						error("Es ist ein Fehler beim Aktualisieren der Universität aufgetreten!");
					}
				}
				
			}
			
			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				target.add( feedbackPanel );
			}
		});

		add(this.form);

	}

}
