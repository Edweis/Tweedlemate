package com.tm.forms.search;

import java.lang.reflect.Field;
import java.util.ArrayList;

import com.tm.entities.Country;
import com.tm.entities.Education;
import com.tm.entities.Experience;
import com.tm.entities.Scholarship;
import com.tm.entities.School;
import com.tm.entities.SuperEntity;
import com.tm.entities.User;

/**
 * Class that contains all informations related to search filter. THIS CLASS
 * SHOULD ONLY BE DONE ONCE WHEN THE SERVER STARTS NOT NEED TO DO IT AT EVERY
 * SEARCH
 * 
 * @author François Rullière
 *
 */
public abstract class FilterInitiator {

	/**
	 * Classes in witch we will make the search. Theses classes should contain
	 * field annotated with {@link Searchable}.
	 */
	private final static Class<?>[] ALL_ENTITY_CLASS = { User.class, Education.class, Scholarship.class,
			com.tm.entities.Work.class, Country.class, Experience.class, School.class };

	/**
	 * Initiate filters by looking at all children of {@link SuperEntity} that
	 * contains field annotated by <tt>@Searcheable</tt> </br>
	 * </br>
	 * 
	 * @return
	 */
	public static ArrayList<FilterSearch> initFilters() {
		ArrayList<FilterSearch> res = new ArrayList<FilterSearch>();

		// Generate filter for all these class
		for (Class<?> c : ALL_ENTITY_CLASS) {

			FilterSearch filter = new FilterSearch(c, true);
			for (Field f : c.getDeclaredFields()) {
				if (f.isAnnotationPresent(Searchable.class)) {
					filter.addField(f);
				}
			}
			if (filter.hasAnyField()) {
				res.add(filter);
			} else {
				// throw new WrongConstructionException("There is no @Searchable
				// field in " + c);
				System.out.println("There is no @Searchable field in " + c);
			}
		}

		return res;

	}

}
