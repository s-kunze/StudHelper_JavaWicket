package de.kunze.studhelper.rest.core;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import de.kunze.studhelper.rest.impl.backend.DegreeCourseImpl;
import de.kunze.studhelper.rest.impl.backend.DepartmentImpl;
import de.kunze.studhelper.rest.impl.backend.LectureImpl;
import de.kunze.studhelper.rest.impl.backend.ModulImpl;
import de.kunze.studhelper.rest.impl.backend.PartImpl;
import de.kunze.studhelper.rest.impl.backend.UniversityImpl;
import de.kunze.studhelper.rest.impl.core.InfoImpl;
import de.kunze.studhelper.rest.impl.user.UserImpl;

public class StudHelperApplication extends Application {

	public Set<Class<?>> getClasses() {
		Set<Class<?>> s = new HashSet<Class<?>>();

		s.add(InfoImpl.class);
		s.add(UniversityImpl.class);
		s.add(DepartmentImpl.class);
		s.add(DegreeCourseImpl.class);
		s.add(PartImpl.class);
		s.add(ModulImpl.class);
		s.add(LectureImpl.class);
		s.add(UserImpl.class);
		
		return s;
	}

}