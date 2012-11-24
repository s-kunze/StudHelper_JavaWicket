package de.kunze.studhelper.view.pages.department;

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
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.SharedResourceReference;

import de.kunze.studhelper.rest.transfer.backend.DepartmentTransfer;
import de.kunze.studhelper.rest.transfer.backend.UniversityTransfer;
import de.kunze.studhelper.view.pages.base.BasePage;
import de.kunze.studhelper.view.rest.RestDepartment;
import de.kunze.studhelper.view.rest.RestUniversity;

public class Department extends BasePage {

	private static final long serialVersionUID = 1L;

	private DataView<DepartmentTransfer> dataView = null;

	private List<DepartmentTransfer> list = null;

	private UniversityTransfer university;
	
	private DropDownChoice<UniversityTransfer> ddc;

	private String uniId = null;

	public Department() {
		initComponents();
	}

	public Department(final PageParameters parameters) {
		this.uniId = parameters.get("uniId").toString();
		initComponents();
	}

	private void initComponents() {

		RestUniversity rest = new RestUniversity();

		if (this.uniId == null || this.uniId.length() == 0) {
			this.list = new ArrayList<DepartmentTransfer>();
		} else {
			UniversityTransfer ut = rest.getUniversity(this.uniId);
			this.list = rest.getDepartmentsForUniversity(ut.getId().toString());
		}
		final WebMarkupContainer panel = new WebMarkupContainer("dataviewPanel");

		this.dataView = new DataView<DepartmentTransfer>("departmentTable",
				new ListDataProvider<DepartmentTransfer>(list)) {
			private static final long serialVersionUID = 1L;

			public void populateItem(final Item<DepartmentTransfer> item) {
				final DepartmentTransfer dt = (DepartmentTransfer) item
						.getModelObject();
				item.add(new Label("name", dt.getName()));

				item.add(new Link<Void>("addDegreeCourse") {

					private static final long serialVersionUID = 2897632616206239753L;

					@Override
					public void onClick() {
						// TODO: implement
						// PageParameters parameters = new PageParameters();
						// parameters.add("nameUni", ut.getName());
						// parameters.add("id", ut.getId());
						// setResponsePage(CreateDepartment.class, parameters);
					}

				}.add(new Image("imageAddDegreeCourse",
						new SharedResourceReference(BasePage.class,
								"../../gfx/add.png"))));

				item.add(new Link<Void>("editDepartment") {

					private static final long serialVersionUID = 1L;

					@Override
					public void onClick() {
						// TODO: implement
						// PageParameters parameters = new PageParameters();
						// parameters.add("update", "1");
						// parameters.add("name", ut.getName());
						// parameters.add("id", ut.getId());
						// setResponsePage(CreateUniversity.class, parameters);
					}

				}.add(new Image("imageEditDepartment",
						new SharedResourceReference(BasePage.class,
								"../../gfx/edit.png"))));
				item.add(new AjaxLink<Void>("deleteDepartment") {

					private static final long serialVersionUID = 1L;

					@Override
					public void onClick(AjaxRequestTarget target) {
						/** Löschen */
						RestDepartment restDepartment = new RestDepartment();
						restDepartment.deleteDepartment(Long.toString(dt
								.getId()));

						RestUniversity restUniversity = new RestUniversity();

						list.clear();
						list.addAll(restUniversity
								.getDepartmentsForUniversity(ddc.getModel().getObject().getId().toString()));

						/** reload table */
						target.add(panel);
					}

				}.add(new Image("imageDeleteDepartment",
						new SharedResourceReference(BasePage.class,
								"../../gfx/delete.png"))));
			}
		};

		dataView.setOutputMarkupId(true);
		panel.setOutputMarkupId(true);

		panel.add(dataView);

		add(panel);

		this.ddc = new DropDownChoice<UniversityTransfer>(
				"ddCUniversity", new Model<UniversityTransfer>(
						university), rest.getUniversities(),
				new ChoiceRenderer<UniversityTransfer>("name", "id"));

		this.ddc.setOutputMarkupId(true);

		this.ddc.add(new AjaxFormComponentUpdatingBehavior("onchange") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				RestUniversity restUniversity = new RestUniversity();

				university = ddc.getModel().getObject();
				
				if (university != null) {
					list.clear();

					list.addAll(restUniversity
							.getDepartmentsForUniversity(university.getId()
									.toString()));
				}

				target.add(panel);
			}
		});

		Form<Void> form = new Form<Void>("formSelect");
	
		form.add(ddc);
		add(form);
	}

	public UniversityTransfer getUniversity() {
		return university;
	}

	public void setUniversity(UniversityTransfer university) {
		this.university = university;
	}
}
