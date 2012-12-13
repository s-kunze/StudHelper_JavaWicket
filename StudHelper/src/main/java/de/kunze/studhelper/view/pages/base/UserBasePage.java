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

import de.kunze.studhelper.view.pages.user.index.Index;

public class UserBasePage extends WebPage {

	private static final long serialVersionUID = 1L;

	protected Logger logger = LoggerFactory.getLogger(AdminBasePage.class);

	protected static ModalWindow modal = null;

	public UserBasePage() {
		this.modal = new ModalWindow("uniModal");

		this.modal.setCloseButtonCallback(new ModalWindow.CloseButtonCallback() {

			private static final long serialVersionUID = 1L;

			public boolean onCloseButtonClicked(AjaxRequestTarget target) {
				return true;
			}
		});

		makeUserMenu();
		
		add(this.modal);

		add(new Label("header", "StudHelper"));
		add(new Label("footer", "by Stefan Kunze"));
	}

	public static ModalWindow getModal() {
		return modal;
	}
	
	public void makeUserMenu() {
//		add(new BookmarkablePageLink<String>("index", Index.class));
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
