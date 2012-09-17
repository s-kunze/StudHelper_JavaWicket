package de.kunze.studhelper.rest.core;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import de.kunze.studhelper.rest.impl.InfoImpl;

public class StudHelperApplication extends Application {

	public Set<Class<?>> getClasses() {
		Set<Class<?>> s = new HashSet<Class<?>>();

		s.add(InfoImpl.class);

		return s;
	}

}