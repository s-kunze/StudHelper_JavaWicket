package de.kunze.studhelper.view.pages.university;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.request.resource.SharedResourceReference;

import de.kunze.studhelper.rest.transfer.backend.UniversityTransfer;
import de.kunze.studhelper.view.pages.base.BasePage;
import de.kunze.studhelper.view.rest.RestUniversity;

public class University extends BasePage {

	private static final long serialVersionUID = 1L;

	private DataView<UniversityTransfer> dataView = null;
	
	private List<UniversityTransfer> list = null;

	public University() {

		RestUniversity rest = new RestUniversity();
		this.list = rest.getUniversities();

		final WebMarkupContainer panel = new WebMarkupContainer("dataviewPanel");
		
		this.dataView = new DataView<UniversityTransfer>("universityTable", 
				new ListDataProvider<UniversityTransfer>(list)) {
			private static final long serialVersionUID = 1L;

			public void populateItem(final Item<UniversityTransfer> item) {
				final UniversityTransfer ut = (UniversityTransfer) item
						.getModelObject();
				item.add(new Label("name", ut.getName()));
				item.add(new AjaxLink<Void>("deleteUniversity") {

					private static final long serialVersionUID = 1L;

					@Override
					public void onClick(AjaxRequestTarget target) {
						/** Löschen */
						RestUniversity rest = new RestUniversity();
						rest.deleteUniversity(Long.toString(ut.getId()));

						list.clear();
						list = rest.getUniversities();
						
						/** reload table */
						target.add(panel);
					}

				}.add(new Image("imageDeleteUniversity",
						new SharedResourceReference(BasePage.class,"../../gfx/delete.png"))
				));
			}
		};

		dataView.setOutputMarkupId(true);
		panel.setOutputMarkupId(true);
		
		panel.add(dataView);

		add(panel);
		
		add(new BookmarkablePageLink<Void>("newUniversity",
				CreateUniversity.class));

		// add(new PagingNavigator("universityNavigator", dataView));

	}

}
