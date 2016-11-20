package com.tm.forms.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

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
	/**
	 * The list of used filters fetch from the URL with get parameter
	 * <tt>?f</tt>
	 */
	private String validatedFiltersFromURL;

	@EJB
	private CRUDint crud;

	@PostConstruct
	public void init() {
		// Initiate filters
		filters = FilterInitiator.initFilters();

	}

	/**
	 * Method that will be process every time the page is refresh/load
	 */
	public void actionOnLoad() {
		// clear the search bar
		searchInput = "";

		if (validatedFiltersFromURL != null) {
			setAllFiltersActivityFromName(validatedFiltersFromURL.split(","));
		} else {
			setAllFiltersActivityFromName(null);
		}

		// if any research, process
		if (tags != null) {
			if (!getSeparatedTags().isEmpty()) {
				search();
			}
		}
	}

	/**
	 * If a filter has his title in the list it will be set active, inactive
	 * otherwise. Note that if an unknown filter is the list it will have no
	 * effect on the result.</br>
	 * If the list is null, all filter will be active
	 * 
	 * @param split
	 */
	private void setAllFiltersActivityFromName(String[] list) {
		boolean allTrue = false;
		boolean allFalse = false;
		List<String> a = new ArrayList<String>();

		if (list == null) {
			allTrue = true;
		} else {
			if (list.length == 0) {
				allFalse = false;
			}
			a = Arrays.asList(list);
		}
		boolean activity;
		for (FilterSearch f : filters) {
			activity = a.contains(f.getTitle());
			activity = activity & !allFalse;
			activity = activity | allTrue;
			f.setActive(activity);
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
			if (f.isActive()) {
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
		// add tags
		if (!getSeparatedTags().isEmpty()) {
			res = res + "&amp;s=";
			res = res + tags;
		}

		// add filters
		String resFilter = "";
		for (FilterSearch f : filters) {
			if (f.isActive()) {
				resFilter = resFilter + f.getTitle() + ",";
			}
		}
		if (!resFilter.equals("")) {
			resFilter = resFilter.substring(0, resFilter.length() - 1);
			res = res + "&amp;f=" + resFilter;
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

	public String getValidatedFiltersFromURL() {
		return validatedFiltersFromURL;
	}

	public void setValidatedFiltersFromURL(String validatedFiltersFromURL) {
		this.validatedFiltersFromURL = validatedFiltersFromURL;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

}
