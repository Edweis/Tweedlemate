package com.tm.pages;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.tm.dao.CountryDAO;
import com.tm.entities.Country;

@ManagedBean
@ViewScoped
public class ProfileTools {

	@EJB
	CountryDAO countryDao;

	public List<Country> getAllCountries() {
		List<Country> a = countryDao.getAll();
		return a;
	}
}
