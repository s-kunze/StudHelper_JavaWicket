package de.kunze.studhelper.view.pages.degreecourse;

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
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.SharedResourceReference;

import de.kunze.studhelper.rest.transfer.backend.DegreeCourseTransfer;
import de.kunze.studhelper.rest.transfer.backend.DepartmentTransfer;
import de.kunze.studhelper.view.pages.base.BasePage;
import de.kunze.studhelper.view.rest.RestDegreeCourse;
import de.kunze.studhelper.view.rest.RestDepartment;

public class DegreeCourse extends BasePage {

	private static final long serialVersionUID = 7582704077674139325L;

	private DataView<DegreeCourseTransfer> dataView = null;

	private List<DegreeCourseTransfer> list = null;

	private DepartmentTransfer department;

	private DropDownChoice<DepartmentTransfer> ddc;

	private Long depId = null;

	public DegreeCourse() {
		initComponents();
	}

	public DegreeCourse(final PageParameters parameters) {
		this.depId = new Long(parameters.get("depId").toInteger());
		initComponents();
	}

	private void initComponents() {

		RestDepartment rest = new RestDepartment();

		if (this.depId == null) {
			this.list = new ArrayList<DegreeCourseTransfer>();
		} else {
			DepartmentTransfer dt = rest.getDepartment(this.depId.toString());
			this.list = rest
					.getDegreeCourseForDepartment(dt.getId().toString());
		}
		final WebMarkupContainer panel = new WebMarkupContainer("dataviewPanel");

		this.dataView = new DataView<DegreeCourseTransfer>("departmentTable",
				new ListDataProvider<DegreeCourseTransfer>(list)) {
			private static final long serialVersionUID = 1L;

			public void populateItem(final Item<DegreeCourseTransfer> item) {
				final DegreeCourseTransfer dct = (DegreeCourseTransfer) item
						.getModelObject();
				item.add(new Label("name", dct.getName()));
				item.add(new Label("cp", dct.getCreditPoints().toString()));

				item.add(new Link<Void>("addPart") {

					private static final long serialVersionUID = 2897632616206239753L;

					@Override
					public void onClick() {
						// PageParameters parameters = new PageParameters();
						// parameters.add("degName", dct.getName());
						// parameters.add("degId", dct.getId());
						// setResponsePage(CreateDegreeCourse.class,
						// parameters);
					}

				}.add(new Image("imageAddPart", new SharedResourceReference(
						BasePage.class, "../../gfx/add.png"))));

				item.add(new Link<Void>("editDegreeCourse") {

					private static final long serialVersionUID = 1L;

					@Override
					public void onClick() {
						DepartmentTransfer dt = ddc.getModel().getObject();

						PageParameters parameters = new PageParameters();

						parameters.add("update", "1");
						parameters.add("depName", dt.getName());
						parameters.add("depId", dt.getId());
						parameters.add("degName", dct.getName());
						parameters.add("degCp", dct.getCreditPoints());
						parameters.add("degId", dct.getId());

						setResponsePage(CreateDegreeCourse.class, parameters);
					}

				}.add(new Image("imageEditDegreeCourse",
						new SharedResourceReference(BasePage.class,
								"../../gfx/edit.png"))));
				item.add(new AjaxLink<Void>("deleteDegreeCourse") {

					private static final long serialVersionUID = 1L;

					@Override
					public void onClick(AjaxRequestTarget target) {
						/** Löschen */
						RestDegreeCourse restDegreeCourse = new RestDegreeCourse();
						restDegreeCourse.deleteDegreeCourse(Long.toString(dct
								.getId()));

						RestDepartment restDepartment = new RestDepartment();

						list.clear();
						list.addAll(restDepartment
								.getDegreeCourseForDepartment(ddc.getModel()
										.getObject().getId().toString()));

						/** reload table */
						target.add(panel);
					}

				}.add(new Image("imageDeleteDegreeCourse",
						new SharedResourceReference(BasePage.class,
								"../../gfx/delete.png"))));
			}
		};

		dataView.setOutputMarkupId(true);
		panel.setOutputMarkupId(true);

		panel.add(dataView);

		add(panel);

		List<DepartmentTransfer> departments = rest.getDepartments();

		this.ddc = new DropDownChoice<DepartmentTransfer>("ddcDepartment",
				new CompoundPropertyModel<DepartmentTransfer>(department), departments,
				new ChoiceRenderer<DepartmentTransfer>("name", "id"));

		if (departments != null) {
			for (DepartmentTransfer dt : departments) {
				if (dt.getId().equals(this.depId)) {
					this.ddc.setModelObject(dt);
				}
			}
		}

		this.ddc.setOutputMarkupId(true);

		this.ddc.add(new AjaxFormComponentUpdatingBehavior("onchange") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				RestDepartment restDepartment = new RestDepartment();

				department = ddc.getModel().getObject();

				if (department != null) {
					list.clear();

					list.addAll(restDepartment
							.getDegreeCourseForDepartment(department.getId()
									.toString()));
				}

				target.add(panel);
			}
		});

		Form<Void> form = new Form<Void>("formSelect");

		form.add(ddc);
		add(form);
	}

}
