package de.kunze.studhelper.view.pages.user.lecture;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.resource.SharedResourceReference;

import de.kunze.studhelper.rest.transfer.backend.DegreeCourseTransfer;
import de.kunze.studhelper.rest.transfer.backend.DepartmentTransfer;
import de.kunze.studhelper.rest.transfer.backend.LectureTransfer;
import de.kunze.studhelper.rest.transfer.backend.ModulTransfer;
import de.kunze.studhelper.rest.transfer.backend.PartTransfer;
import de.kunze.studhelper.view.core.StudhelperWebSession;
import de.kunze.studhelper.view.pages.admin.lecture.CreateLecture;
import de.kunze.studhelper.view.pages.admin.lecture.CreateLectureUser;
import de.kunze.studhelper.view.pages.admin.lecture.Lecture;
import de.kunze.studhelper.view.pages.admin.lecture.Mark;
import de.kunze.studhelper.view.pages.base.AdminBasePage;
import de.kunze.studhelper.view.pages.base.UserBasePage;
import de.kunze.studhelper.view.pages.base.AdminBasePage.ConfirmPanel;
import de.kunze.studhelper.view.pages.base.UserMenuPanel;
import de.kunze.studhelper.view.rest.RestDegreeCourse;
import de.kunze.studhelper.view.rest.RestLecture;
import de.kunze.studhelper.view.rest.RestModul;
import de.kunze.studhelper.view.rest.RestPart;
import de.kunze.studhelper.view.rest.RestUniversity;
import de.kunze.studhelper.view.rest.RestUser;

@AuthorizeInstantiation("USER")
public class UserLecture extends UserBasePage {

	private static final long serialVersionUID = 1L;

	private DataView<LectureTransfer> dataView = null;

	private List<LectureTransfer> list = null;

	private Form<Void> selectForm;
	
	private ModulTransfer mt = null;
	private PartTransfer pt = null;

	private DropDownChoice<PartTransfer> ddcPart;
	private DropDownChoice<ModulTransfer> ddcModul;

	private RestModul restMod = null;
	private RestLecture restLec = null;

	private WebMarkupContainer panel = null;

	public UserLecture() {
		this.restMod = new RestModul();
		this.restLec = new RestLecture();

		initComponents();
		
		add(new UserMenuPanel("userMenuPanel"));
	}

	private void refreshLecture() {
		list.clear();
		list.addAll(restMod.getLecturesForModul(mt.getId().toString()));
	}
	
	private void refreshModuls() {
		this.ddcModul.setChoices(new RestPart().getModulForPart(this.pt.getId().toString()));
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

				item.add(new AjaxLink<Void>("attendLecture") {

					private static final long serialVersionUID = 1L;

					@Override
					public void onClick(AjaxRequestTarget target) {
						getModal().setContent(new AttendLecture(getModal().getContentId(), lecture, UserLecture.this));
						getModal().setTitle("Vorlesung belegen");
						getModal().setInitialHeight(200);
						getModal().setInitialWidth(400);
						getModal().show(target);
					}

				}.add(new Image("imageAttendLecture", new SharedResourceReference(AdminBasePage.class,
						"../../gfx/add.png"))));
			}
		};

		dataView.setOutputMarkupId(true);
		panel.setOutputMarkupId(true);

		panel.add(dataView);

		add(panel);

		String id = ((StudhelperWebSession) Session.get()).getUserId().toString();
		DegreeCourseTransfer dct = new RestUser().getDegreeCourseFromUser(id);
		
		List<PartTransfer> parts = new RestDegreeCourse().getPartForDegreeCourse(dct.getId().toString());
		
		this.ddcPart = new DropDownChoice<PartTransfer>("ddcPart", new PropertyModel(this, "pt"), parts,
				new ChoiceRenderer<PartTransfer>("name", "id"));

		this.ddcPart.setOutputMarkupId(true);
		this.ddcPart.add(new AjaxFormComponentUpdatingBehavior("onchange") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				pt = ddcPart.getModelObject();
				refreshModuls();
				
				mt = ddcModul.getModelObject();
				
				if(mt != null) {
					logger.info("Update Panel!!");
					refreshLecture();
				}
				
				target.add(selectForm);
			}
		});
		
		this.ddcModul = new DropDownChoice<ModulTransfer>("ddcModul", new PropertyModel(this, "mt"), new ArrayList<ModulTransfer>(),
				new ChoiceRenderer<ModulTransfer>("name", "id"));

		this.ddcModul.setOutputMarkupId(true);
		this.ddcModul.add(new AjaxFormComponentUpdatingBehavior("onchange") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				mt = ddcModul.getModelObject();
				
				if(mt != null) {
					refreshLecture();
				}
				
				target.add(panel);
			}
		});

		this.selectForm = new Form<Void>("selectForm");
		
		this.selectForm.add(this.ddcPart);
		this.selectForm.add(this.ddcModul);
		
		add(this.selectForm);
	}

	public WebMarkupContainer getPanel() {
		return panel;
	}

	public void setPanel(WebMarkupContainer panel) {
		this.panel = panel;
	}

	public class AttendLecture extends Panel {

		private static final long serialVersionUID = 1L;

		private LectureTransfer lecture;
		private UserLecture pageUserLecture;

		private Float mark;
		private TextField<Float> textField; 

		public AttendLecture(String id, LectureTransfer lecture, UserLecture pageUserLecture) {
			super(id);
			this.lecture = lecture;
			this.pageUserLecture = pageUserLecture;
			
			initComponents();
		}

		private void initComponents() {
			Form<Void> form = new Form<Void>("form");
		
			this.textField = new TextField<Float>("mark", new PropertyModel<Float>(this, "mark"));
			form.add(this.textField);

			form.add(new AjaxSubmitLink("submitAttendLecture", form) {

				private static final long serialVersionUID = 1L;

				@Override
				protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
					RestLecture restLecture = new RestLecture();

					Long userId = ((StudhelperWebSession) Session.get()).getUserId();
					
					mark = textField.getModelObject();
					
					if (restLecture.addUserToLecture(lecture.getId().toString(), userId.toString(), mark)) {
						pageUserLecture.getModal().close(target);
					} else {
						error("Es ist ein Fehler aufgetreten!");
					}
				}
			});

			add(new Label("msgAttendLecture", "Vorlesung belegen"));
			add(form);
		}

	}

}
