package de.kunze.studhelper.view.pages.admin.modul;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;

import de.kunze.studhelper.rest.transfer.backend.ModulTransfer;
import de.kunze.studhelper.rest.transfer.backend.PartTransfer;
import de.kunze.studhelper.view.pages.admin.part.Part;
import de.kunze.studhelper.view.rest.RestModul;

@AuthorizeInstantiation(Roles.ADMIN)
public class CreateModul extends Panel {

	private static final long serialVersionUID = 1L;

	private Form<ModulTransfer> form;

	private PartTransfer par = null;
	
	private ModulTransfer mod = null;

	private Part parPage = null;
	
	private Modul modPage = null;

	private RestModul restMod = null;

	public CreateModul(String id, PartTransfer par, Part parPage) {
		super(id);
		this.par = par;
		this.parPage = parPage;
		this.restMod = new RestModul();

		initComponents();
	}

	public CreateModul(String id, PartTransfer par, Modul modPage) {
		super(id);
		this.par = par;
		this.modPage = modPage;
		this.restMod = new RestModul();

		initComponents();
	}

	public CreateModul(String id, PartTransfer par, ModulTransfer mod, Modul modPage) {
		super(id);
		this.par = par;
		this.mod = mod;
		this.modPage = modPage;
		this.restMod = new RestModul();

		initComponents();
	}

	private void initComponents() {

		this.form = new Form<ModulTransfer>("createModulForm", new CompoundPropertyModel<ModulTransfer>(new ModulTransfer()));

		this.form.add(new TextField<String>("name"));
		this.form.add(new TextField<Integer>("creditPoints"));

		if (this.mod != null) {
			this.form.setModelObject(this.mod);
		}

		final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
		feedbackPanel.setOutputMarkupId(true);

		add(feedbackPanel);

		this.form.add(new AjaxSubmitLink("submitModulForm", this.form) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {

				final ModulTransfer newModul = (ModulTransfer) form.getModelObject();

				if (mod == null) {
					if (restMod.createModul(par.getId().toString(), newModul)) {

						if (modPage != null) {
							modPage.refreshModul();
							target.add(modPage.getPanel());

							modPage.getModal().close(target);
						} else {
							parPage.getModal().close(target);
						}
					} else {
						/** Fehler anzeigen */
						error("Es ist ein Fehler beim Erstellen der Fakultät aufgetreten!");
					}
				} else {
					if (restMod.updateModul(newModul)) {
						if (modPage != null) {
							modPage.refreshModul();
							target.add(modPage.getPanel());

							modPage.getModal().close(target);
						} else {
							parPage.getModal().close(target);
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

		add(new Label("msgCreateModul", "Modul für " + this.par.getName() + " erstellen"));
		add(this.form);

	}
}
