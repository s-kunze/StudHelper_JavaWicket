package de.kunze.studhelper.view.pages.admin.modul;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
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
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.resource.SharedResourceReference;

import de.kunze.studhelper.rest.transfer.backend.ModulTransfer;
import de.kunze.studhelper.rest.transfer.backend.PartTransfer;
import de.kunze.studhelper.view.pages.admin.lecture.CreateLecture;
import de.kunze.studhelper.view.pages.base.AdminBasePage;
import de.kunze.studhelper.view.rest.RestModul;
import de.kunze.studhelper.view.rest.RestPart;

@AuthorizeInstantiation(Roles.ADMIN)
public class Modul extends AdminBasePage {

	private static final long serialVersionUID = 1L;

	private DataView<ModulTransfer> dataView = null;

	private List<ModulTransfer> list = null;

	private PartTransfer pt = null;

	private DropDownChoice<PartTransfer> ddc;

	private RestPart restPar = null;
	private RestModul restMod = null;

	private WebMarkupContainer panel = null;

	public Modul() {
		this.restPar = new RestPart();
		this.restMod = new RestModul();

		initComponents();
	}

	public Modul(PartTransfer pt) {
		this.pt = pt;

		this.restPar = new RestPart();
		this.restMod = new RestModul();

		initComponents();
	}

	protected void refreshModul() {
		list.clear();
		list.addAll(restPar.getModulForPart(pt.getId().toString()));
	}

	private void initComponents() {

		if (this.pt == null) {
			this.list = new ArrayList<ModulTransfer>();
		} else {
			PartTransfer pt = restPar.getPart(this.pt.getId().toString());
			this.list = restPar.getModulForPart(pt.getId().toString());
		}

		panel = new WebMarkupContainer("dataviewPanel");

		this.dataView = new DataView<ModulTransfer>("modulTable", new ListDataProvider<ModulTransfer>(list)) {
			private static final long serialVersionUID = 1L;

			public void populateItem(final Item<ModulTransfer> item) {
				final ModulTransfer modul = (ModulTransfer) item.getModelObject();
				item.add(new Label("name", modul.getName()));
				item.add(new Label("cp", modul.getCreditPoints().toString()));

				item.add(new AjaxLink<Void>("addLecture") {

					private static final long serialVersionUID = 1L;

					@Override
					public void onClick(AjaxRequestTarget target) {
						getModal().setContent(new CreateLecture(getModal().getContentId(), modul, Modul.this));
						getModal().setTitle("Vorlesung anlegen");
						getModal().setInitialHeight(400);
						getModal().setInitialWidth(400);
						getModal().show(target);
					}

				}.add(new Image("imageAddLecture", new SharedResourceReference(AdminBasePage.class, "../../gfx/add.png"))));
				
				
				item.add(new AjaxLink<Void>("editModul") {

					private static final long serialVersionUID = 1L;

					@Override
					public void onClick(AjaxRequestTarget target) {
						getModal().setContent(new CreateModul(getModal().getContentId(), ddc.getModelObject(), modul, Modul.this));
						getModal().setTitle("Modul anlegen");
						getModal().setInitialHeight(150);
						getModal().setInitialWidth(400);
						getModal().show(target);
					}

				}.add(new Image("imageEditModul", new SharedResourceReference(AdminBasePage.class, "../../gfx/edit.png"))));
				item.add(new AjaxLink<Void>("deleteModul") {

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
										Long id = modul.getId();
										String strId = Long.toString(id);
										restMod.deleteModul(strId);
										refreshModul();

										target.add(panel);
										modal.close(target);
									}

								});
							}

						});
						getModal().setTitle("Modul löschen");
						getModal().setInitialHeight(150);
						getModal().setInitialWidth(400);
						getModal().show(target);
					}

				}.add(new Image("imageDeleteModul", new SharedResourceReference(AdminBasePage.class, "../../gfx/delete.png"))));
			}
		};

		dataView.setOutputMarkupId(true);
		panel.setOutputMarkupId(true);

		panel.add(dataView);

		add(panel);

		add(new AjaxLink<Void>("newModul") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				getModal().setContent(new CreateModul(getModal().getContentId(), ddc.getModelObject(), Modul.this));
				getModal().setTitle("Modul anlegen");
				getModal().setInitialHeight(150);
				getModal().setInitialWidth(400);
				getModal().show(target);
			}

		});

		List<PartTransfer> parts = restPar.getParts();

		this.ddc = new DropDownChoice<PartTransfer>("ddcModul", new PropertyModel(this, "pt"), parts,
				new ChoiceRenderer<PartTransfer>("name", "id"));

		this.ddc.setModelObject(pt);

		this.ddc.setOutputMarkupId(true);

		this.ddc.add(new AjaxFormComponentUpdatingBehavior("onchange") {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				pt = ddc.getModelObject();

				if (pt != null) {
					refreshModul();
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
