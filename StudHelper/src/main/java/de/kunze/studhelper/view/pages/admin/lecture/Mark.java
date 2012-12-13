package de.kunze.studhelper.view.pages.admin.lecture;

import java.io.Serializable;

public class Mark implements Serializable {

	private static final long serialVersionUID = 1L;
	private Float mark;

	public Float getMark() {
		return mark;
	}

	public void setMark(Float mark) {
		this.mark = mark;
	}

}
