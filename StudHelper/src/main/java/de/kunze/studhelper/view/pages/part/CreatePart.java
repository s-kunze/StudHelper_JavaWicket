package de.kunze.studhelper.view.pages.part;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;

import de.kunze.studhelper.rest.transfer.backend.DegreeCourseTransfer;
import de.kunze.studhelper.rest.transfer.backend.PartTransfer;
import de.kunze.studhelper.view.pages.degreecourse.DegreeCourse;
import de.kunze.studhelper.view.rest.RestPart;

public class CreatePart extends Panel {

	private static final long serialVersionUID = 1L;

	private Form<PartTransfer> form;

	private DegreeCourseTransfer dct = null;
	
	private PartTransfer par = null;

	private DegreeCourse degPage = null;
	
	private Part parPage = null;

	private RestPart restPar = null;

	public CreatePart(String id, DegreeCourseTransfer dct, DegreeCourse degPage) {
		super(id);
		this.dct = dct;
		this.degPage = degPage;
		this.restPar = new RestPart();

		initComponents();
	}

	public CreatePart(String id, DegreeCourseTransfer dct, Part parPage) {
		super(id);
		this.dct = dct;
		this.parPage = parPage;
		this.restPar = new RestPart();

		initComponents();
	}

	public CreatePart(String id, DegreeCourseTransfer dct, PartTransfer par, Part parPage) {
		super(id);
		this.dct = dct;
		this.par = par;
		this.parPage = parPage;
		this.restPar = new RestPart();

		initComponents();
	}

	private void initComponents() {

		this.form = new Form<PartTransfer>("createPartForm", new CompoundPropertyModel<PartTransfer>(new PartTransfer()));

		this.form.add(new TextField<String>("name"));
		this.form.add(new TextField<Integer>("creditPoints"));

		if (this.par != null) {
			this.form.setModelObject(this.par);
		}

		final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
		feedbackPanel.setOutputMarkupId(true);

		add(feedbackPanel);

		this.form.add(new AjaxSubmitLink("submitPartForm", this.form) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {

				final PartTransfer newPart = (PartTransfer) form.getModelObject();

				if (par == null) {
					if (restPar.createPart(dct.getId().toString(), newPart)) {

						if (parPage != null) {
							parPage.refreshPart();
							target.add(parPage.getPanel());

							parPage.getModal().close(target);
						} else {
							degPage.getModal().close(target);
						}
					} else {
						/** Fehler anzeigen */
						error("Es ist ein Fehler beim Erstellen der Fakultät aufgetreten!");
					}
				} else {
					if (restPar.updatePart(newPart)) {
						if (parPage != null) {
							parPage.refreshPart();
							target.add(parPage.getPanel());

							parPage.getModal().close(target);
						} else {
							degPage.getModal().close(target);
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

		add(new Label("msgCreatePart", "Bereich für " + this.dct.getName() + " erstellen"));
		add(this.form);

	}
}
