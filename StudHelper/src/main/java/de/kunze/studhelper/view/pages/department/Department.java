package de.kunze.studhelper.view.pages.department;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.resource.SharedResourceReference;

import de.kunze.studhelper.rest.transfer.backend.DepartmentTransfer;
import de.kunze.studhelper.rest.transfer.backend.UniversityTransfer;
import de.kunze.studhelper.view.pages.base.BasePage;
import de.kunze.studhelper.view.pages.degreecourse.CreateDegreeCourse;
import de.kunze.studhelper.view.pages.university.University;
import de.kunze.studhelper.view.rest.RestDepartment;
import de.kunze.studhelper.view.rest.RestUniversity;

public class Department extends BasePage {

	private static final long serialVersionUID = 1L;

	private DataView<DepartmentTransfer> dataView = null;

	private List<DepartmentTransfer> list = null;

	private UniversityTransfer ut = null;

	private DropDownChoice<UniversityTransfer> ddc;

	private RestUniversity restUni = null;
	private RestDepartment restDep = null;

	private WebMarkupContainer panel = null;

	public Department() {
		this.restUni = new RestUniversity();
		this.restDep = new RestDepartment();

		initComponents();
	}

	public Department(UniversityTransfer ut) {
		this.ut = ut;

		this.restUni = new RestUniversity();
		this.restDep = new RestDepartment();

		initComponents();
	}

	protected void refreshDepartment() {
		list.clear();
		list.addAll(restUni.getDepartmentsForUniversity(ut.getId().toString()));
	}


	private void initComponents() {

		if (this.ut == null) {
			this.list = new ArrayList<DepartmentTransfer>();
		} else {
			UniversityTransfer ut = restUni.getUniversity(this.ut.getId().toString());
			this.list = restUni.getDepartmentsForUniversity(ut.getId().toString());
		}

		panel = new WebMarkupContainer("dataviewPanel");

		this.dataView = new DataView<DepartmentTransfer>("departmentTable", new ListDataProvider<DepartmentTransfer>(list)) {
			private static final long serialVersionUID = 1L;

			public void populateItem(final Item<DepartmentTransfer> item) {
				final DepartmentTransfer dt = (DepartmentTransfer) item.getModelObject();
				item.add(new Label("name", dt.getName()));

				item.add(new AjaxLink<Void>("addDegreeCourse") {

					private static final long serialVersionUID = 1L;

					@Override
					public void onClick(AjaxRequestTarget target) {
						getModal().setContent(new CreateDegreeCourse(getModal().getContentId(), dt, Department.this));
						getModal().setTitle("Studiengang anlegen");
						getModal().setInitialHeight(150);
						getModal().setInitialWidth(400);
						getModal().show(target);
					}

				}.add(new Image("imageAddDegreeCourse", new SharedResourceReference(BasePage.class, "../../gfx/add.png"))));
				
				
				item.add(new AjaxLink<Void>("editDepartment") {

					private static final long serialVersionUID = 1L;

					@Override
					public void onClick(AjaxRequestTarget target) {
						getModal().setContent(new CreateDepartment(getModal().getContentId(), ddc.getModelObject(), dt, Department.this));
						getModal().setTitle("Fakultät anlegen");
						getModal().setInitialHeight(150);
						getModal().setInitialWidth(400);
						getModal().show(target);
					}

				}.add(new Image("imageEditDepartment", new SharedResourceReference(BasePage.class, "../../gfx/edit.png"))));
				
				item.add(new AjaxLink<Void>("deleteDepartment") {

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
										restDep.deleteDepartment(Long.toString(dt.getId()));
										refreshDepartment();

										target.add(panel);
										modal.close(target);
									}

								});
							}

						});
						getModal().setTitle("Fakultät löschen");
						getModal().setInitialHeight(150);
						getModal().setInitialWidth(400);
						getModal().show(target);
					}

				}.add(new Image("imageDeleteDepartment", new SharedResourceReference(BasePage.class, "../../gfx/delete.png"))));
			}
		};

		dataView.setOutputMarkupId(true);
		panel.setOutputMarkupId(true);

		panel.add(dataView);

		add(panel);

		add(new AjaxLink<Void>("newDepartment") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				getModal().setContent(new CreateDepartment(getModal().getContentId(), ddc.getModelObject(), Department.this));
				getModal().setTitle("Fakultät anlegen");
				getModal().setInitialHeight(150);
				getModal().setInitialWidth(400);
				getModal().show(target);
			}

		});

		List<UniversityTransfer> universities = restUni.getUniversities();

		this.ddc = new DropDownChoice<UniversityTransfer>("ddCUniversity", new CompoundPropertyModel<UniversityTransfer>(ut), universities,
				new ChoiceRenderer<UniversityTransfer>("name", "id"));

		this.ddc.setModelObject(ut);

		this.ddc.setOutputMarkupId(true);

		this.ddc.add(new AjaxFormComponentUpdatingBehavior("onchange") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {

				ut = ddc.getModelObject();
				
				if (ut != null) {
					refreshDepartment();
				}

				target.add(panel);
			}
		});

		Form<Void> form = new Form<Void>("formSelect");

		form.add(ddc);
		add(form);
	}

	public UniversityTransfer getUt() {
		return ut;
	}

	public void setUt(UniversityTransfer university) {
		this.ut = university;
	}

	public WebMarkupContainer getPanel() {
		return panel;
	}

	public void setPanel(WebMarkupContainer panel) {
		this.panel = panel;
	}

	
}
