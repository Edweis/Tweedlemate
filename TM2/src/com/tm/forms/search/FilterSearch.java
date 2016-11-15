package com.tm.forms.search;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.tm.dao.CRUDint;
import com.tm.entities.User;
import com.tm.exceptions.WrongConstructionException;

/**
 * Instances of FilterSearch are allow
 * 
 * @author François Rullière
 *
 */
@ManagedBean
@ViewScoped
public class FilterSearch {
	private Class<?> clazz;
	private String title;
	private boolean active;
	private int nbResult;

	/**
	 * Contains all fields and their displayed label
	 */
	private List<DisplayField> fields;

	public FilterSearch(Class<?> c, boolean active) {
		this.clazz = c;
		this.title = c.getSimpleName();
		this.active = active;
		this.nbResult = 0;
		fields = new ArrayList<DisplayField>();
		;
	}

	/**
	 * Add a field annotated by @See {@link com.tm.forms.search.Searchable} to
	 * the list of field. It will also put the <tt>label</tt> attribute of the
	 * field annotation as the label name. If it hasn't be declare it will use
	 * the name of the field.
	 * 
	 * @param f
	 */
	public void addField(Field f) {
		String label = f.getAnnotation(Searchable.class).label();
		if ("".equals(label)) {
			label = f.getName();
		}
		fields.add(new DisplayField(f, label));
	}

	/**
	 * Return true if the filter has fields and thus should be considered
	 * 
	 * @return
	 */
	public boolean hasAnyField() {
		return !fields.isEmpty();
	}

	/**
	 * Search the input <tt>search</tt> in all field marked
	 * by @{@link Searchable} and return all Users found. The path(s) to reach a
	 * User in defined in <tt>Searcheable.userFetchPath()</tt>. The search
	 * matching uses <tt>*** LIKE %search%</tt>
	 * 
	 * @param search
	 * @param crud
	 * @return
	 */
	public List<User> search(String search, CRUDint crud) {
		if (hasAnyField() && active) {
			List<User> res = new ArrayList<User>();
			String query;
			for (DisplayField d : fields) {
				query = generateQueryFromSearchableField(d.getField());
				List<User> a = crud.processQuery(query, User.class, search);
				res.addAll(a);
			}
			nbResult = res.size();
			return res;

		} else {
			// I think that should never happen because the filter list in
			// SearchTool won't add a filter without any field
			return null;
		}
	}

	/**
	 * Generate the JPQL request from the <tt>@Searchable.userFetchPath</tt>.
	 * For example : </br>
	 * for <tt>fetchPath</tt> = <tt>user.myEducation.School.Country</tt></br>
	 * it will return </br>
	 * <tt>SELECT x FROM User x JOIN x.myEducation a JOIN a.School b JOIN b.Country c WHERE c.?1</tt>
	 * 
	 * @param fetchPath
	 * @return
	 */
	private String generateQueryFromSearchableField(Field field) {
		String res = "SELECT x FROM User x ";

		String className = field.getDeclaringClass().getSimpleName();
		String userFetchPath = field.getAnnotation(Searchable.class).userFetchPath();
		String fieldName = field.getName();

		if (!userFetchPath.startsWith("User") || !userFetchPath.endsWith(className)) {
			throw new WrongConstructionException(
					"The path '" + userFetchPath + "' from the field '" + fieldName + "' in the class '" + className
							+ "' doesn't starts with 'User' or doesn't ends with '" + className + "'");
		}

		String[] stones = userFetchPath.split("\\.");
		String[] argsName = { "x", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j" };
		if (stones.length > 10) {
			throw new WrongConstructionException(
					"The path '" + userFetchPath + "' from the field '" + fieldName + "' in the class '" + className
							+ "' should have a path shorter than 10 table, come look t the code bro.");
		}
		for (int i = 1; i < stones.length; i++) {
			res = res + "JOIN " + argsName[i - 1] + "." + stones[i] + " AS " + argsName[i] + " ";
		}

		res = res + "WHERE " + argsName[stones.length - 1] + "." + fieldName + " LIKE CONCAT('%', ?1, '%')";

		return res;
	}

	public class DisplayField {
		Field field;
		String name;

		public DisplayField(Field field, String name) {
			this.field = field;
			this.name = name;
		}

		public Field getField() {
			return field;
		}

		public void setField(Field field) {
			this.field = field;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

	}

	/*
	 * Getters and setters
	 */

	@Override
	public String toString() {
		return "FilterSearch [clazz=" + clazz + ", title=" + title + ", active=" + active + "]";
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public List<DisplayField> getFields() {
		return fields;
	}

	public void setFields(List<DisplayField> fields) {
		this.fields = fields;
	}

	public int getNbResult() {
		return nbResult;
	}

	public void setNbResult(int nbResult) {
		this.nbResult = nbResult;
	}

}