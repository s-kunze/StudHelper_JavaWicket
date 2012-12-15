package de.kunze.studhelper.view.pages.user.lectureOverview;

import java.util.List;

import org.apache.wicket.Session;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;

import de.kunze.studhelper.rest.transfer.backend.LectureMarkTransfer;
import de.kunze.studhelper.rest.transfer.backend.LectureTransfer;
import de.kunze.studhelper.view.core.StudhelperWebSession;
import de.kunze.studhelper.view.pages.base.UserBasePage;
import de.kunze.studhelper.view.pages.base.UserMenuPanel;
import de.kunze.studhelper.view.rest.RestUser;

public class LectureOverview extends UserBasePage {

	private static final long serialVersionUID = 1L;

	private DataView<LectureMarkTransfer> dataView = null;

	private List<LectureMarkTransfer> list = null;

	private WebMarkupContainer panel = null;

	public LectureOverview() {

		initComponents();
		
		add(new UserMenuPanel("userMenuPanel"));
	}
	
	private void initComponents() {
		panel = new WebMarkupContainer("dataviewPanel");

		String userId = ((StudhelperWebSession) Session.get()).getUserId().toString();
		this.list = (new RestUser()).getLectureFromUser(userId);
		
		this.dataView = new DataView<LectureMarkTransfer>("lectureTable", new ListDataProvider<LectureMarkTransfer>(list)) {
			private static final long serialVersionUID = 1L;

			public void populateItem(final Item<LectureMarkTransfer> item) {
				final LectureMarkTransfer lecture = (LectureMarkTransfer) item.getModelObject();
				item.add(new Label("name", lecture.getName()));
				item.add(new Label("cp", lecture.getCreditPoints().toString()));
				item.add(new Label("mark", lecture.getMark().toString()));
			}
		};

		dataView.setOutputMarkupId(true);
		panel.setOutputMarkupId(true);

		panel.add(dataView);

		add(panel);
	}

	public WebMarkupContainer getPanel() {
		return panel;
	}

	public void setPanel(WebMarkupContainer panel) {
		this.panel = panel;
	}
	
}
