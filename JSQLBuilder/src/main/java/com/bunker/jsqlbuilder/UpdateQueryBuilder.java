package com.bunker.jsqlbuilder;

public class UpdateQueryBuilder extends WriteQueryBuilder {
	private String mQuery;
	private String mSearch;
	private boolean isFirstSet = true;

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
	
	public String build() {
		mQuery += " where ";
		return mQuery + mSearch;
	}

	@Override
	public UpdateQueryBuilder insertField(String field) {
		insertField(field, null);
		return this;
	}
}