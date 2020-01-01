package com.bunker.jsqlbuilder;

import java.util.LinkedList;
import java.util.List;

public class JoinQueryBuilder {
	private String query;
	private List<String> reads = new LinkedList<>();
	private List<String> terms = new LinkedList<>();
	private List<String> on = new LinkedList<>();

	public JoinQueryBuilder(String joinType, String table, String name) {
		query = " " + joinType + " " + table + " ";
		if (name != null && !name.equals("")) {
			query += "as " + name + " ";
		}
	}
	
	public JoinQueryBuilder insertRead(String read) {
		reads.add(read);
		return this;
	}

	public JoinQueryBuilder insertTerm(String term) {
		this.terms.add(term);
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

	public String getTerm() {
		String term = null;
		if (terms.size() > 0) {
			term = terms.get(0);
		}
		for (int i = 1; i < terms.size(); i++) {
			term += " and " + terms.get(i);
		}
		return term;
	}

	public String getJoinQuery() {
		String query = this.query;
		if (on.size() > 0)
			query += "on " + on.get(0);
		
		for (int i = 1; i < on.size(); i++) {
			query += " and " + on.get(i);
		}
		return query;
	}
	
	public static void main(String args[]) {
		StringBuilder builder = new StringBuilder();
		System.out.println(builder.toString());
	}
}