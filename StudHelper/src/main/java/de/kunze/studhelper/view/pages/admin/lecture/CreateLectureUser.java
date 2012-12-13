package de.kunze.studhelper.view.pages.admin.lecture;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;

import de.kunze.studhelper.rest.transfer.backend.LectureTransfer;
import de.kunze.studhelper.rest.transfer.user.UserTransfer;
import de.kunze.studhelper.view.rest.RestLecture;
import de.kunze.studhelper.view.rest.RestUser;

@AuthorizeInstantiation(Roles.ADMIN)
public class CreateLectureUser extends Panel {

	private static final long serialVersionUID = 1L;

	private LectureTransfer lec;
	private UserTransfer ut;
	
	private Form<Mark> form;
	
	private Lecture page;
	
	private DropDownChoice<UserTransfer> ddcUser;

	private List<UserTransfer> usersSelect = new ArrayList<UserTransfer>();

	public CreateLectureUser(String id, LectureTransfer lec, Lecture page) {
		super(id);
		this.lec = lec;
		this.page = page;
		initComponents();
	}

	private void initComponents() {
		this.form = new Form<Mark>("createLectureUserForm", new CompoundPropertyModel<Mark>(new Mark()));
	
		this.form.add(new TextField<Integer>("mark"));

		final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
		feedbackPanel.setOutputMarkupId(true);

		add(feedbackPanel);

		this.form.add(new AjaxSubmitLink("submitLectureUserForm", this.form) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {

				final Mark mark = (Mark) form.getModelObject();
				
				RestLecture restLecture = new RestLecture();
				
				if(restLecture.addUserToLecture(lec.getId().toString(), ut.getId().toString(), mark.getMark())) {
					page.getModal().close(target);
				} else {
					error("Es ist ein Fehler aufgetreten!");
				}
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				target.add(feedbackPanel);
			}
		});

		final List<UserTransfer> users = new RestUser().getUsers();

		this.ddcUser = new DropDownChoice<UserTransfer>("ddcUser", new PropertyModel(this, "ut"),
				users, new ChoiceRenderer<UserTransfer>("username", "id"));

		this.ddcUser.setModelObject(ut);
		this.ddcUser.setOutputMarkupId(true);
		this.ddcUser.add(new AjaxFormComponentUpdatingBehavior("onchange") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				ut = ddcUser.getModelObject();
				target.add(form);
			}
		});
		
		
		add(this.ddcUser);
		add(new Label("msgCreateLectureUser", "Vorlesung und Benutzer verbinden"));
		add(this.form);

	}
	
}
