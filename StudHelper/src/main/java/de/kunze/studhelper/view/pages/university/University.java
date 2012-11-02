package de.kunze.studhelper.view.pages.university;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;

import de.kunze.studhelper.rest.transfer.backend.UniversityTransfer;
import de.kunze.studhelper.view.pages.base.BasePage;
import de.kunze.studhelper.view.rest.RestUniversity;

public class University extends BasePage {

	private static final long serialVersionUID = 1L;

	public University() {
		
		RestUniversity rest = new RestUniversity();
		List<UniversityTransfer> list = rest.getUniversities();
        
        final DataView<UniversityTransfer> dataView = new DataView<UniversityTransfer>("universityTable", 
        		new ListDataProvider<UniversityTransfer>(list)) {
					private static final long serialVersionUID = 1L;

					public void populateItem(final Item<UniversityTransfer> item) {
		                final UniversityTransfer ut = (UniversityTransfer) item.getModelObject();
		                item.add(new Label("name", ut.getName()));
		            }
        };

        dataView.setItemsPerPage(10);
        
        add(dataView);

//        add(new PagingNavigator("universityNavigator", dataView));
		
		
	}
	
}
