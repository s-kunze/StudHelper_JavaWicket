package de.kunze.studhelper.view.pages.university;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;

import de.kunze.studhelper.rest.transfer.backend.UniversityTransfer;
import de.kunze.studhelper.view.pages.base.BasePage;
import de.kunze.studhelper.view.rest.RestUniversity;

public class CreateUniversity extends BasePage {

	private static final long serialVersionUID = 1L;

	private Form<UniversityTransfer> form;

	public CreateUniversity() {

		this.form = new Form<UniversityTransfer>("createUniversityForm",
				new CompoundPropertyModel<UniversityTransfer>(
						new UniversityTransfer()));

		this.form.add(new TextField<String>("name"));

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

				if (rest.createUniversity(ut)) {
					/** Zu University wechseln */
					setResponsePage(University.class);
				} else {
					/** Fehler anzeigen */
					error("Es ist ein Fehler beim erstellen der Universität aufgetreten!");
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
