package de.kunze.studhelper.view.pages.base;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kunze.studhelper.view.pages.degreecourse.DegreeCourse;
import de.kunze.studhelper.view.pages.department.Department;
import de.kunze.studhelper.view.pages.index.Index;
import de.kunze.studhelper.view.pages.modul.Modul;
import de.kunze.studhelper.view.pages.part.Part;
import de.kunze.studhelper.view.pages.university.University;

public class BasePage extends WebPage {

	private static final long serialVersionUID = 1L;

	protected Logger logger = LoggerFactory.getLogger(BasePage.class);

	protected static ModalWindow modal = null;

	public BasePage() {
		add(new BookmarkablePageLink<String>("index", Index.class));
		add(new BookmarkablePageLink<String>("university", University.class));
		add(new BookmarkablePageLink<String>("department", Department.class));
		add(new BookmarkablePageLink<String>("degreeCourse", DegreeCourse.class));
		add(new BookmarkablePageLink<String>("part", Part.class));
		add(new BookmarkablePageLink<String>("modul", Modul.class));
		
		this.modal = new ModalWindow("uniModal");

		this.modal.setCloseButtonCallback(new ModalWindow.CloseButtonCallback() {

			private static final long serialVersionUID = 1L;

			public boolean onCloseButtonClicked(AjaxRequestTarget target) {
				return true;
			}
		});

		add(this.modal);

		add(new Label("header", "StudHelper"));
		add(new Label("footer", "by Stefan Kunze"));
	}

	public static ModalWindow getModal() {
		return modal;
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
