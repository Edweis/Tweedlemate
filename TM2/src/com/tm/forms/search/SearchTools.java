package com.tm.forms.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.tm.converter.SearchURLConverter;
import com.tm.dao.CRUDint;
import com.tm.entities.User;

@ManagedBean
@ViewScoped
public class SearchTools {

	// open variables
	/**
	 * What is in the search bar
	 */
	private String searchInput;
	/**
	 * Represents all tags input by the user and that should be consider in the
	 * research, it can't be null when the research is processed
	 */
	private String tags;
	/**
	 * List of filters initialized at <tt>@PostConstruct</tt> of the bean
	 * (<tt>init()</tt> method)
	 */
	private List<FilterSearch> filters;
	/**
	 * Results sorted by filter.</br>
	 * The filter name is the name of the category
	 */
	private List<SearchResultCategory> researchResult;

	@EJB
	private CRUDint crud;

	// @PostConstruct
	// This method is called after viewParam
	public void init() {
		// Initiate filters
		filters = FilterInitiator.initFilters();

		// Open variables
		searchInput = "";

		// Atoms are initiate in the viewParameter
		// If there are some requests, lets search !
		if (tags != null) {
			if (!getSeparatedTags().isEmpty()) {
				search();
			}
		}
	}

	/**
	 * Launch the research
	 */
	public void search() {
		// Initiate an empty result
		researchResult = new ArrayList<SearchResultCategory>();

		// For all filter
		for (FilterSearch f : filters) {
			List<User> filterResult = new ArrayList<User>();
			// Lets find all users related with every atoms
			for (String s : getSeparatedTags()) {
				filterResult.addAll(f.search(s, crud));
			}
			if (!filterResult.isEmpty()) {
				researchResult.add(new SearchResultCategory(f, filterResult));
			}
		}

	}

	public List<String> getSeparatedTags() {
		if (tags == null) {
			return null;
		} else {
			String[] s = tags.split(",");
			return Arrays.asList(s);
		}
	}

	public void resetTags() {
		tags = "";
	}

	/**
	 * Return the search URL that contains all atoms separated by a "+"
	 * 
	 * @return the search URL
	 */
	public String getURLParam() {
		String res = "search.xhtml?faces-redirect=true&amp;includeViewParams=true";
		if (!getSeparatedTags().isEmpty()) {
			res = res + "&amp;s=";
			SearchURLConverter suc = new SearchURLConverter();
			res = res + suc.getAsString(getSeparatedTags());
		}
		return res;
	}

	/**
	 * Class used to display user results in category. This avoid to store the
	 * result in a <tt>HashMap<FilterSearch, List<User>></tt> and use another
	 * library (jstl core maybe) to display them.
	 * 
	 * @author François Rullière
	 *
	 */
	public class SearchResultCategory {
		private FilterSearch filter;
		private List<User> userResult;

		public SearchResultCategory(FilterSearch filter, List<User> userResult) {
			this.filter = filter;
			this.userResult = userResult;
		}

		public FilterSearch getFilter() {
			return filter;
		}

		public void setFilter(FilterSearch filter) {
			this.filter = filter;
		}

		public List<User> getUserResult() {
			return userResult;
		}

		public void setUserResult(List<User> userResult) {
			this.userResult = userResult;
		}

	}

	// GETTERS AND SETTERS

	public String getSearchInput() {
		return searchInput;
	}

	public void setSearchInput(String searchInput) {
		this.searchInput = searchInput;
	}

	public List<FilterSearch> getFilters() {
		return filters;
	}

	public List<SearchResultCategory> getResearchResult() {
		return researchResult;
	}

	public void setResearchResult(List<SearchResultCategory> researchResult) {
		this.researchResult = researchResult;
	}

	public void setFilters(List<FilterSearch> filters) {
		this.filters = filters;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

}
