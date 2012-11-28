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
import de.kunze.studhelper.rest.transfer.backend.UniversityTransfer;
import de.kunze.studhelper.view.pages.base.BasePage;
import de.kunze.studhelper.view.pages.base.BasePage.ConfirmPanel;
import de.kunze.studhelper.view.pages.department.CreateDepartment;
import de.kunze.studhelper.view.pages.department.Department;
import de.kunze.studhelper.view.rest.RestDegreeCourse;
import de.kunze.studhelper.view.rest.RestDepartment;
import de.kunze.studhelper.view.rest.RestUniversity;

public class DegreeCourse extends BasePage {

	private static final long serialVersionUID = 1L;

	private DataView<DegreeCourseTransfer> dataView = null;

	private List<DegreeCourseTransfer> list = null;

	private DepartmentTransfer dt = null;

	private DropDownChoice<DepartmentTransfer> ddc;

	private RestDepartment restDep = null;
	private RestDegreeCourse restDeg = null;

	private WebMarkupContainer panel = null;

	public DegreeCourse() {
		this.restDep = new RestDepartment();
		this.restDeg = new RestDegreeCourse();

		initComponents();
	}

	public DegreeCourse(DepartmentTransfer dt) {
		this.dt = dt;

		this.restDep = new RestDepartment();
		this.restDeg = new RestDegreeCourse();

		initComponents();
	}

	protected void refreshDegreeCourse() {
		list.clear();
		list.addAll(restDep.getDegreeCourseForDepartment(dt.getId().toString()));
	}

	private void initComponents() {

		if (this.dt == null) {
			this.list = new ArrayList<DegreeCourseTransfer>();
		} else {
			DepartmentTransfer ut = restDep.getDepartment(this.dt.getId().toString());
			this.list = restDep.getDegreeCourseForDepartment(dt.getId().toString());
		}

		panel = new WebMarkupContainer("dataviewPanel");

		this.dataView = new DataView<DegreeCourseTransfer>("departmentTable",
				new ListDataProvider<DegreeCourseTransfer>(list)) {
			private static final long serialVersionUID = 1L;

			public void populateItem(final Item<DegreeCourseTransfer> item) {
				final DegreeCourseTransfer dct = (DegreeCourseTransfer) item
						.getModelObject();
				item.add(new Label("name", dct.getName()));
				item.add(new Label("cp", dct.getCreditPoints().toString()));

				item.add(new AjaxLink<Void>("editDegreeCourse") {

					private static final long serialVersionUID = 1L;

					@Override
					public void onClick(AjaxRequestTarget target) {
						getModal().setContent(new CreateDegreeCourse(getModal().getContentId(), ddc.getModelObject(), dct, DegreeCourse.this));
						getModal().setTitle("Studiengang anlegen");
						getModal().setInitialHeight(150);
						getModal().setInitialWidth(400);
						getModal().show(target);
					}

				}.add(new Image("imageEditDegreeCourse",
						new SharedResourceReference(BasePage.class,
								"../../gfx/edit.png"))));
				item.add(new AjaxLink<Void>("deleteDegreeCourse") {

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
										restDeg.deleteDegreeCourse(Long.toString(dct.getId()));
										refreshDegreeCourse();

										target.add(panel);
										modal.close(target);
									}

								});
							}

						});
						getModal().setTitle("Studiengang löschen");
						getModal().setInitialHeight(150);
						getModal().setInitialWidth(400);
						getModal().show(target);
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

		add(new AjaxLink<Void>("newDegreeCourse") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				getModal().setContent(new CreateDegreeCourse(getModal().getContentId(), ddc.getModelObject(), DegreeCourse.this));
				getModal().setTitle("Studiengang anlegen");
				getModal().setInitialHeight(150);
				getModal().setInitialWidth(400);
				getModal().show(target);
			}

		});
		
		List<DepartmentTransfer> departments = restDep.getDepartments();

		this.ddc = new DropDownChoice<DepartmentTransfer>("ddcDepartment",
				new CompoundPropertyModel<DepartmentTransfer>(dt), departments,
				new ChoiceRenderer<DepartmentTransfer>("name", "id"));

		this.ddc.setModelObject(dt);

		this.ddc.setOutputMarkupId(true);

		this.ddc.add(new AjaxFormComponentUpdatingBehavior("onchange") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				dt = ddc.getModelObject();
				
				if (dt != null) {
					refreshDegreeCourse();
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
