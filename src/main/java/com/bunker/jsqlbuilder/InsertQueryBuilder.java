package com.bunker.jsqlbuilder;

import java.util.ArrayList;
import java.util.List;

public class InsertQueryBuilder extends WriteQueryBuilder {
	private String query;
	private List<Data> list = new ArrayList<Data>();
	private SelectQueryBuilder selectBuilder;

	private String last;

	private class Data {
		String field, value;
		Data (String field, String value) {
			this.field = field;
			this.value =value;
		}
	}

	public InsertQueryBuilder(String table) {
		this(table, false);
	}
	
	public InsertQueryBuilder(String table, boolean ignore) {
		if (ignore) {
			query = "insert ignore into " + table + " ";
		} else {
			query = "insert into " + table + " ";
		}
	}

	public InsertQueryBuilder insertField(String field, String value) {
		if (field != null && value != null)
			list.add(new Data(field, value));
		return this;
	}

	public InsertQueryBuilder insertSelectBuilder(SelectQueryBuilder selectBuilder) {
		this.selectBuilder = selectBuilder;
		return this;
	}

	public String build() {
		if (list.size() <= 0)
			return null;
		query += "(";
		for (int i = 0; i < list.size() - 1; i++) {
			query += list.get(i).field + ",";
		}

		query += list.get(list.size() - 1).field;
		query += ") ";

		if (selectBuilder != null) {
			query += selectBuilder.build();
		} else {
			query += "values (";
			for (int i = 0; i < list.size() - 1; i++) {
				query += list.get(i).value + ",";
			}

			query += list.get(list.size() - 1).value;
			query += ")";
		}

		if (last != null) {
			query += " " + last;
		}

		return query;
	}

	@Override
	public InsertQueryBuilder insertField(String field) {
		insertField(field, "");
		return this;
	}

	public void setLast(String []fields, String additional) {
		if (fields.length == 0) {
			return;
		}
		StringBuilder lastBuilder = new StringBuilder("ON DUPLICATE KEY UPDATE ");
		for (int i = 0; i < fields.length; i++) {
			if (i != 0) {
				lastBuilder.append(",");
			}
			lastBuilder.append(fields[i] + "=VALUES(" + fields[i] + ")");
		}
		if (additional != null) {
			lastBuilder.append("," + additional);
		}
		last = lastBuilder.toString();
	}

	public void setLast(String last) {
		this.last = last;
	}
}
