package de.kunze.studhelper.view.pages.admin.lecture;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBoxMultipleChoice;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;

import de.kunze.studhelper.rest.transfer.backend.DepartmentTransfer;
import de.kunze.studhelper.rest.transfer.backend.LectureTransfer;
import de.kunze.studhelper.rest.transfer.backend.ModulTransfer;
import de.kunze.studhelper.view.pages.admin.modul.Modul;
import de.kunze.studhelper.view.rest.RestDepartment;
import de.kunze.studhelper.view.rest.RestLecture;
import de.kunze.studhelper.view.rest.RestModul;

@AuthorizeInstantiation(Roles.ADMIN)
public class CreateLecture extends Panel {
	
	private static final long serialVersionUID = 1L;

	private Form<LectureTransfer> form;

	private ModulTransfer mod = null;

	private LectureTransfer lec = null;

	private Modul modPage = null;

	private Lecture lecPage = null;

	private RestLecture restLec = null;

	private List<ModulTransfer> modulesSelect = new ArrayList<ModulTransfer>();

	public CreateLecture(String id, ModulTransfer mod, Modul modPage) {
		super(id);
		this.mod = mod;
		this.modPage = modPage;
		this.restLec = new RestLecture();

		initComponents();
	}

	public CreateLecture(String id, ModulTransfer mod, Lecture lecPage) {
		super(id);
		this.mod = mod;
		this.lecPage = lecPage;
		this.restLec = new RestLecture();

		initComponents();
	}

	public CreateLecture(String id, ModulTransfer mod, LectureTransfer lec, Lecture lecPage) {
		super(id);
		this.mod = mod;
		this.lec = lec;
		this.lecPage = lecPage;
		this.restLec = new RestLecture();

		initComponents();
	}

	private void initComponents() {
		
		RestModul restMod = new RestModul();
		RestDepartment restDep = new RestDepartment();
		
		modulesSelect.add(this.mod);
		
		DepartmentTransfer departmentTransfer = restMod.getDepartment(this.mod.getId().toString());
		List<ModulTransfer> modules = restDep.getModules(departmentTransfer.getId().toString());
		
		final CheckBoxMultipleChoice<ModulTransfer> listModules = 
				new CheckBoxMultipleChoice<ModulTransfer>("modules", new Model((Serializable) modulesSelect), modules, new ChoiceRenderer<ModulTransfer>("name", "id"));

		this.form = new Form<LectureTransfer>("createLectureForm", new CompoundPropertyModel<LectureTransfer>(new LectureTransfer()));

		this.form.add(listModules);
		
		this.form.add(new TextField<String>("name"));
		this.form.add(new TextField<Integer>("creditPoints"));

		if (this.lec != null) {
			this.form.setModelObject(this.lec);
		}

		final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
		feedbackPanel.setOutputMarkupId(true);

		add(feedbackPanel);

		this.form.add(new AjaxSubmitLink("submitLectureForm", this.form) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {

				final LectureTransfer newLecture = (LectureTransfer) form.getModelObject();

				if (lec == null) {
					for(ModulTransfer mt : modulesSelect) {
						restLec.createLecture(mt.getId().toString(), newLecture);
					}
					
					if (lecPage != null) {
						lecPage.refreshLecture();
						target.add(lecPage.getPanel());

						lecPage.getModal().close(target);
					} else {
						modPage.getModal().close(target);
					}
				} else {
					if (restLec.updateLecture(newLecture)) {
						if (lec != null) {
							lecPage.refreshLecture();
							target.add(lecPage.getPanel());

							lecPage.getModal().close(target);
						} else {
							modPage.getModal().close(target);
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

		add(new Label("msgCreateLecture", "Vorlesung für " + this.mod.getName() + " erstellen"));
		add(this.form);

	}

}
