package com.bunker.jsqlbuilder;

import java.util.LinkedList;
import java.util.List;

public class JoinQueryBuilder {
	private String query;
	private String read;
	private String term;
	private List<String> on = new LinkedList<>();

	public JoinQueryBuilder(String joinType, String table, String name) {
		query = joinType + " " + table + " ";
		if (name != null && !name.equals("")) {
			query += "as " + name + " ";
		}
	}
	
	public JoinQueryBuilder insertRead(String read) {
		this.read = read;
		return this;
	}

	public JoinQueryBuilder insertTerm(String seletermct) {
		this.term = seletermct;
		return this;
	}

	public JoinQueryBuilder insertOn(String on) {
		this.on.add(on);
		return this;
	}

	public JoinQueryBuilder insertEqualOn(String field, String name1, String name2) {
		return insertOn(name1 + "." + field + "=" + name2 + "." + field);
	}
	
	public String getRead() {
		return read;
	}

	public String getTerm() {
		return term;
	}

	public String getJoinQuery() {
		if (on.size() > 0)
			query += "on " + on.remove(0) + " ";
		
		for (String s : on) {
			query += "and " + s + " ";
		}
		return query;
	}
	
	public static void main(String args[]) {
		
	}
}