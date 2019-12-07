package com.bunker.jsqlbuilder;

public class UpdateQueryBuilder extends WriteQueryBuilder {
	private String mQuery;
	private String mSearch;
	private boolean isFirstSet = true;
	private String from = null;

	public UpdateQueryBuilder(String table,String search) {
		mQuery = "update " + table + " set ";
		mSearch = search;
	}
	
	public UpdateQueryBuilder insertField(String field, String value) {
		if (field == null || value == null)
			return this;

		if (isFirstSet)
			isFirstSet = false;
		else
			mQuery += ",";
		mQuery += field  + "=" + value;
		
		return this;
	}
	
	public UpdateQueryBuilder insertFrom(SelectQueryBuilder selectBuilder) {
		this.from = "(" + selectBuilder.build() + ")";
		return this;
	}
	
	public String build() {
		if (from != null) 
			mQuery += " from " + from;
		mQuery += " where ";
		return mQuery + mSearch;
	}

	@Override
	public UpdateQueryBuilder insertField(String field) {
		insertField(field, null);
		return this;
	}
}