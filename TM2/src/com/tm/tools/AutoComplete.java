package com.tm.tools;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

import com.tm.dao.CRUDint;
import com.tm.dao.SchoolDAO;
import com.tm.entities.Country;
import com.tm.entities.Scholarship;

@ManagedBean
public class AutoComplete {

	@EJB
	private SchoolDAO schoolDao;
	@EJB
	private CRUDint crud;;

	public List<String> allSchools(String input) {
		List<String> results = new ArrayList<String>();
		results = schoolDao.selectSchoolName_StartWith(input);

		return results;
	}

	public List<Country> getAllCountries() {
		List<Country> a = crud.getAll(Country.class);
		return a;
	}

	public List<Scholarship> allScholarships() {
		List<Scholarship> a = crud.getAll(Scholarship.class);
		return a;
	}
}
