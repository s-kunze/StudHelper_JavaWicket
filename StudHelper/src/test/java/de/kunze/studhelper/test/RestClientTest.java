package de.kunze.studhelper.test;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.kunze.studhelper.rest.transfer.backend.UniversityTransfer;
import de.kunze.studhelper.view.rest.RestUniversity;

public class RestClientTest {

	Logger logger = LoggerFactory.getLogger(RestClientTest.class);
	
	@Test
	public void createUniversity() {
		
		UniversityTransfer ut1 = new UniversityTransfer();
		ut1.setName("Friedrich Schiller University");
		
		UniversityTransfer ut2 = new UniversityTransfer();
		ut2.setName("FH Schmalkalden");
		
		RestUniversity rest = new RestUniversity();
		
		boolean res1 = rest.createUniversity(ut1);
		boolean res2 = rest.createUniversity(ut2);
		
		assertTrue(res1);
		assertTrue(res2);
	}
	
	@Test
	public void getUniversities() {
		List<UniversityTransfer> result = new ArrayList<UniversityTransfer>();
		
		RestUniversity rest = new RestUniversity();
		result = rest.getUniversities();
		
		assertTrue(result.size() == 2);
		
		assertTrue(result.get(0).getName().equals("Friedrich Schiller University"));
		assertTrue(result.get(1).getName().equals("FH Schmalkalden"));
	}
	
	@Test
	public void updateUniversity() {
		List<UniversityTransfer> result = new ArrayList<UniversityTransfer>();
		
		RestUniversity rest = new RestUniversity();
		result = rest.getUniversities();
		
		assertTrue(result.get(0).getName().equals("Friedrich Schiller University"));
		assertTrue(result.get(1).getName().equals("FH Schmalkalden"));
		
		UniversityTransfer ut = result.get(0);
		ut.setName("FH Erfurt");
		
		if(rest.updateUniversity(ut)) {
			result = rest.getUniversities();
			
			assertTrue(result.get(0).getName().equals("FH Erfurt"));
			assertTrue(result.get(1).getName().equals("FH Schmalkalden"));
		}
	}
	
	@Test
	public void deleteUniversity() {
		List<UniversityTransfer> result = new ArrayList<UniversityTransfer>();
		
		RestUniversity rest = new RestUniversity();
		result = rest.getUniversities();

		assertTrue(rest.deleteUniversity(Long.toString(result.get(0).getId())));
		assertTrue(rest.deleteUniversity(Long.toString(result.get(1).getId())));
		
		result = rest.getUniversities();
		
		assertTrue(result.size() == 0);
	}
	
}
