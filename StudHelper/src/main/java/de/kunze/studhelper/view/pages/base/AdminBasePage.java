package de.kunze.studhelper.view.pages.base;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kunze.studhelper.view.pages.admin.degreecourse.DegreeCourse;
import de.kunze.studhelper.view.pages.admin.department.Department;
import de.kunze.studhelper.view.pages.admin.index.AdminIndex;
import de.kunze.studhelper.view.pages.admin.lecture.Lecture;
import de.kunze.studhelper.view.pages.admin.modul.Modul;
import de.kunze.studhelper.view.pages.admin.part.Part;
import de.kunze.studhelper.view.pages.admin.university.University;
import de.kunze.studhelper.view.pages.admin.user.User;

public class AdminBasePage extends WebPage {

	private static final long serialVersionUID = 1L;

	protected Logger logger = LoggerFactory.getLogger(AdminBasePage.class);

	protected static ModalWindow modal = null;

	public AdminBasePage() {
		
		
		this.modal = new ModalWindow("uniModal");

		this.modal.setCloseButtonCallback(new ModalWindow.CloseButtonCallback() {

			private static final long serialVersionUID = 1L;

			public boolean onCloseButtonClicked(AjaxRequestTarget target) {
				return true;
			}
		});

		makeAdminMenu();
		
		add(this.modal);

		add(new Label("header", "StudHelper"));
		add(new Label("footer", "by Stefan Kunze"));
	}

	public static ModalWindow getModal() {
		return modal;
	}
	
	public void makeAdminMenu() {
		add(new BookmarkablePageLink<String>("admin", AdminIndex.class));
		add(new BookmarkablePageLink<String>("university", University.class));
		add(new BookmarkablePageLink<String>("department", Department.class));
		add(new BookmarkablePageLink<String>("degreeCourse", DegreeCourse.class));
		add(new BookmarkablePageLink<String>("part", Part.class));
		add(new BookmarkablePageLink<String>("modul", Modul.class));
		add(new BookmarkablePageLink<String>("lecture", Lecture.class));
		add(new BookmarkablePageLink<String>("user", User.class));
	}
	
	public class ConfirmPanel extends Panel {

		private static final long serialVersionUID = 1L;

		public ConfirmPanel(String id) {
			super(id);
			initComponents();
		}

		protected void initComponents() {
			
			add(new Label("msg", "Wollen Sie wirklich fortfahrten!"));
			
			setOkButton();
			setCloseButton();
		}

		protected void setOkButton() {
			add(new AjaxLink<Void>("yes") {

				private static final long serialVersionUID = 1L;

				@Override
				public void onClick(AjaxRequestTarget target) {
					modal.close(target);
				}

			});
		}

		protected void setCloseButton() {
			add(new AjaxLink<Void>("no") {

				private static final long serialVersionUID = 1L;

				@Override
				public void onClick(AjaxRequestTarget target) {
					modal.close(target);
				}

			});

		}
	}
}
