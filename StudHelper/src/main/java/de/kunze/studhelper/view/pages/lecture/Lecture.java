package de.kunze.studhelper.view.pages.lecture;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.resource.SharedResourceReference;

import de.kunze.studhelper.rest.transfer.backend.LectureTransfer;
import de.kunze.studhelper.rest.transfer.backend.ModulTransfer;
import de.kunze.studhelper.rest.transfer.backend.PartTransfer;
import de.kunze.studhelper.view.pages.base.BasePage;
import de.kunze.studhelper.view.pages.base.BasePage.ConfirmPanel;
import de.kunze.studhelper.view.pages.modul.CreateModul;
import de.kunze.studhelper.view.pages.modul.Modul;
import de.kunze.studhelper.view.rest.RestLecture;
import de.kunze.studhelper.view.rest.RestModul;
import de.kunze.studhelper.view.rest.RestPart;

public class Lecture extends BasePage {

	private static final long serialVersionUID = 1L;

	private DataView<LectureTransfer> dataView = null;

	private List<LectureTransfer> list = null;

	private ModulTransfer mt = null;

	private DropDownChoice<ModulTransfer> ddc;

	private RestModul restMod = null;
	private RestLecture restLec = null;
	
	private WebMarkupContainer panel = null;

	public Lecture() {
		this.restMod = new RestModul();
		this.restLec = new RestLecture();

		initComponents();
	}

	public Lecture(ModulTransfer mt) {
		this.mt = mt;

		this.restMod = new RestModul();
		this.restLec = new RestLecture();

		initComponents();
	}

	protected void refreshLecture() {
		list.clear();
		list.addAll(restMod.getLecturesForModul(mt.getId().toString()));
	}

	private void initComponents() {

		if (this.mt == null) {
			this.list = new ArrayList<LectureTransfer>();
		} else {
			ModulTransfer mt = restMod.getModul(this.mt.getId().toString());
			this.list = restMod.getLecturesForModul(mt.getId().toString());
		}

		panel = new WebMarkupContainer("dataviewPanel");

		this.dataView = new DataView<LectureTransfer>("lectureTable", new ListDataProvider<LectureTransfer>(list)) {
			private static final long serialVersionUID = 1L;

			public void populateItem(final Item<LectureTransfer> item) {
				final LectureTransfer lecture = (LectureTransfer) item.getModelObject();
				item.add(new Label("name", lecture.getName()));
				item.add(new Label("cp", lecture.getCreditPoints().toString()));

				item.add(new AjaxLink<Void>("addLectureUser") {

					private static final long serialVersionUID = 1L;

					@Override
					public void onClick(AjaxRequestTarget target) {
						getModal().setContent(new CreateLectureUser(getModal().getContentId(), lecture, Lecture.this));
						getModal().setTitle("Vorlesung mit Benutzer anlegen");
						getModal().setInitialHeight(400);
						getModal().setInitialWidth(400);
						getModal().show(target);
					}

				}.add(new Image("imageAddLectureUser", new SharedResourceReference(BasePage.class, "../../gfx/add.png"))));
				
				
				item.add(new AjaxLink<Void>("editLecture") {

					private static final long serialVersionUID = 1L;

					@Override
					public void onClick(AjaxRequestTarget target) {
						getModal().setContent(new CreateLecture(getModal().getContentId(), ddc.getModelObject(), lecture, Lecture.this));
						getModal().setTitle("Vorlesung anlegen");
						getModal().setInitialHeight(400);
						getModal().setInitialWidth(400);
						getModal().show(target);
					}

				}.add(new Image("imageEditLecture", new SharedResourceReference(BasePage.class, "../../gfx/edit.png"))));
				
				item.add(new AjaxLink<Void>("deleteLecture") {

					private static final long serialVersionUID = 1L;

					@Override
					public void onClick(AjaxRequestTarget target) {
						getModal().setContent(new ConfirmPanel(getModal().getContentId()) {

							private static final long serialVersionUID = 1L;

							@Override
							protected void setOkButton() {
								add(new AjaxLink<Void>("yes") {

									private static final long serialVersionUID = 1L;

									@Override
									public void onClick(AjaxRequestTarget target) {
										Long id = lecture.getId();
										String strId = Long.toString(id);
										restLec.deleteLecture(strId);
										refreshLecture();

										target.add(panel);
										modal.close(target);
									}

								});
							}

						});
						getModal().setTitle("Vorlesung löschen");
						getModal().setInitialHeight(150);
						getModal().setInitialWidth(400);
						getModal().show(target);
					}

				}.add(new Image("imageDeleteLecture", new SharedResourceReference(BasePage.class, "../../gfx/delete.png"))));
			}
		};

		dataView.setOutputMarkupId(true);
		panel.setOutputMarkupId(true);

		panel.add(dataView);

		add(panel);

		add(new AjaxLink<Void>("newLecture") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				getModal().setContent(new CreateLecture(getModal().getContentId(), ddc.getModelObject(), Lecture.this));
				getModal().setTitle("Vorlesung anlegen");
				getModal().setInitialHeight(150);
				getModal().setInitialWidth(400);
				getModal().show(target);
			}

		});

		List<ModulTransfer> modules = restMod.getModuls();

		this.ddc = new DropDownChoice<ModulTransfer>("ddcLecture", new CompoundPropertyModel<ModulTransfer>(mt), modules,
				new ChoiceRenderer<ModulTransfer>("name", "id"));

		this.ddc.setModelObject(mt);

		this.ddc.setOutputMarkupId(true);

		this.ddc.add(new AjaxFormComponentUpdatingBehavior("onchange") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				mt = ddc.getModelObject();

				if (mt != null) {
					refreshLecture();
				}

				target.add(panel);
			}
		});

		Form<Void> form = new Form<Void>("formSelect");

		form.add(ddc);
		add(form);
	}

	public WebMarkupContainer getPanel() {
		return panel;
	}

	public void setPanel(WebMarkupContainer panel) {
		this.panel = panel;
	}
	
}
