package de.kunze.studhelper.view.pages.part;

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

import de.kunze.studhelper.rest.transfer.backend.DegreeCourseTransfer;
import de.kunze.studhelper.rest.transfer.backend.DepartmentTransfer;
import de.kunze.studhelper.rest.transfer.backend.PartTransfer;
import de.kunze.studhelper.view.pages.base.BasePage;
import de.kunze.studhelper.view.pages.base.BasePage.ConfirmPanel;
import de.kunze.studhelper.view.pages.degreecourse.CreateDegreeCourse;
import de.kunze.studhelper.view.pages.degreecourse.DegreeCourse;
import de.kunze.studhelper.view.rest.RestDegreeCourse;
import de.kunze.studhelper.view.rest.RestDepartment;
import de.kunze.studhelper.view.rest.RestPart;

public class Part extends BasePage {

	private static final long serialVersionUID = 1L;

	private DataView<PartTransfer> dataView = null;

	private List<PartTransfer> list = null;

	private DegreeCourseTransfer dt = null;

	private DropDownChoice<DegreeCourseTransfer> ddc;

	private RestDegreeCourse restDeg = null;
	private RestPart restPar = null;

	private WebMarkupContainer panel = null;

	public Part() {
		this.restDeg = new RestDegreeCourse();
		this.restPar = new RestPart();

		initComponents();
	}

	public Part(DegreeCourseTransfer dt) {
		this.dt = dt;

		this.restDeg = new RestDegreeCourse();
		this.restPar = new RestPart();

		initComponents();
	}

	protected void refreshPart() {
		list.clear();
		list.addAll(restDeg.getPartForDegreeCourse(dt.getId().toString()));
	}

	private void initComponents() {

		if (this.dt == null) {
			this.list = new ArrayList<PartTransfer>();
		} else {
			DegreeCourseTransfer ut = restDeg.getDegreeCourse(this.dt.getId().toString());
			this.list = restDeg.getPartForDegreeCourse(dt.getId().toString());
		}

		panel = new WebMarkupContainer("dataviewPanel");

		this.dataView = new DataView<PartTransfer>("partTable", new ListDataProvider<PartTransfer>(list)) {
			private static final long serialVersionUID = 1L;

			public void populateItem(final Item<PartTransfer> item) {
				final PartTransfer part = (PartTransfer) item.getModelObject();
				item.add(new Label("name", part.getName()));
				item.add(new Label("cp", part.getCreditPoints().toString()));

				item.add(new AjaxLink<Void>("editPart") {

					private static final long serialVersionUID = 1L;

					@Override
					public void onClick(AjaxRequestTarget target) {
						getModal().setContent(new CreatePart(getModal().getContentId(), ddc.getModelObject(), part, Part.this));
						getModal().setTitle("Bereich anlegen");
						getModal().setInitialHeight(150);
						getModal().setInitialWidth(400);
						getModal().show(target);
					}

				}.add(new Image("imageEditPart", new SharedResourceReference(BasePage.class, "../../gfx/edit.png"))));
				item.add(new AjaxLink<Void>("deletePart") {

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
										restPar.deletePart(Long.toString(part.getId()));
										refreshPart();

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

				}.add(new Image("imageDeletePart", new SharedResourceReference(BasePage.class, "../../gfx/delete.png"))));
			}
		};

		dataView.setOutputMarkupId(true);
		panel.setOutputMarkupId(true);

		panel.add(dataView);

		add(panel);

		add(new AjaxLink<Void>("newPart") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				getModal().setContent(new CreatePart(getModal().getContentId(), ddc.getModelObject(), Part.this));
				getModal().setTitle("Bereich anlegen");
				getModal().setInitialHeight(150);
				getModal().setInitialWidth(400);
				getModal().show(target);
			}

		});

		List<DegreeCourseTransfer> degreeCourses = restDeg.getDegreeCourses();

		this.ddc = new DropDownChoice<DegreeCourseTransfer>("ddcPart", new CompoundPropertyModel<DegreeCourseTransfer>(dt), degreeCourses,
				new ChoiceRenderer<DegreeCourseTransfer>("name", "id"));

		this.ddc.setModelObject(dt);

		this.ddc.setOutputMarkupId(true);

		this.ddc.add(new AjaxFormComponentUpdatingBehavior("onchange") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				dt = ddc.getModelObject();

				if (dt != null) {
					refreshPart();
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
