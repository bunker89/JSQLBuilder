package com.bunker.jsqlbuilder;

import java.util.ArrayList;
import java.util.List;

public class InsertQueryBuilder extends WriteQueryBuilder {
	private String query;
	private List<Data> list = new ArrayList<Data>();
	private SelectQueryBuilder selectBuilder;

	private class Data {
		String field, value;
		Data (String field, String value) {
			this.field = field;
			this.value =value;
		}
	}

	public InsertQueryBuilder(String queryBase) {
		query = queryBase;
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

		return query;
	}

	@Override
	public InsertQueryBuilder insertField(String field) {
		insertField(field, "");
		return this;
	}
}
