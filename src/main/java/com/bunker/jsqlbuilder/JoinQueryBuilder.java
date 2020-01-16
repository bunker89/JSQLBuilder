package com.bunker.jsqlbuilder;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.Nullable;

import com.bunker.jsqlbuilder.binder.Binder;
import com.bunker.jsqlbuilder.setters.PreparedSetter;

public class JoinQueryBuilder {
	private String query;
	private List<String> reads = new LinkedList<>();
	private List<PreparedPair> terms = new LinkedList<>();
	private List<PreparedPair> on = new LinkedList<>();
	private List<Binder> binders = new LinkedList<>();

	public JoinQueryBuilder(String joinType, String table, String name) {
		query = " " + joinType + " " + table + " ";
		if (name != null && !name.equals("")) {
			query += "as " + name + " ";
		}
	}
	
	public JoinQueryBuilder insertRead(String read, @Nullable Binder...binders) {
		reads.add(read);
		for (Binder binder : binders) {
			this.binders.add(binder);
			reads.add(binder.getFieldName());
		}
		return this;
	}

	public JoinQueryBuilder insertTerm(String term, PreparedSetter...setters) {
		this.terms.add(new PreparedPair(term, Arrays.asList(setters)));
		return this;
	}
	
	public JoinQueryBuilder insertOn(String on, PreparedSetter...setters) {
		this.on.add(new PreparedPair(on, Arrays.asList(setters)));
		return this;
	}
	
	/**
	 * this method assume sql injection safe.
	 * @param field
	 * @param name1
	 * @param name2
	 * @return
	 */
	public JoinQueryBuilder insertEqualOn(String field, String name1, String name2) {
		return insertOn(name1 + "." + field + "=" + name2 + "." + field);
	}
	
	/**
	 * this method assume sql injection safe.
	 * @return
	 */
	public String getRead() {
		StringBuilder builder = new StringBuilder();
		if (reads.size() > 0) {
			builder.append(reads.get(0));
		}
		
		for (int i = 1; i < reads.size(); i++) {
			builder.append(",");
			builder.append(reads.get(i));
		}
		return builder.toString();
	}
	
	public List<Binder> getBinders() {
		return binders;
	}

	public PreparedPair getTerm() {
		String term = null;
		List<PreparedSetter> newSetters = new LinkedList<>();
		
		if (terms.size() > 0) {
			PreparedPair pair = terms.get(0);
			
			for (PreparedSetter setter : pair.setters)
				newSetters.add(setter);
			term = pair.query;
		}
		
		for (int i = 1; i < terms.size(); i++) {
			PreparedPair pair = terms.get(i);
			term += " and " + pair.query;
			for (PreparedSetter setter : pair.setters)
				newSetters.add(setter);
		}
		return new PreparedPair(term, newSetters);
	}

	public PreparedPair getJoinQuery() {
		String query = this.query;
		List<PreparedSetter> newSetters = new LinkedList<>();
		
		if (on.size() > 0) {
			PreparedPair pair = on.get(0);
			query += "on " + pair.query;
			
			for (PreparedSetter s : pair.setters) {
				newSetters.add(s);
			}
		}
		
		for (int i = 1; i < on.size(); i++) {
			PreparedPair pair = on.get(i);
			query += " and " + pair.query;
			
			for (PreparedSetter s : pair.setters) {
				newSetters.add(s);
			}
		}
		return new PreparedPair(query, newSetters);
	}
}