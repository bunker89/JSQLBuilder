package com.bunker.jsqlbuilder;

public abstract class WriteQueryBuilder {

	public WriteQueryBuilder insertField(String field, int value) {
		return insertField(field, value + "");
	}

	public WriteQueryBuilder insertField(String field, long value) {
		return insertField(field, value + "");
	}

	public WriteQueryBuilder insertField(String field, boolean value) {
		int bool = 0;
		if (value)
			bool = 1;
		return insertField(field, bool + "");
	}

	public abstract WriteQueryBuilder insertField(String field);
	public abstract WriteQueryBuilder insertField(String field, String value);

	public WriteQueryBuilder insertFieldWrap(String field, String value) {
		if (value != null)
			value = value.replace("'", "\'\'");
		return insertField(field, wrapString(value));
	}

	public abstract String build(); 
	
	private String wrapString(String str) {
		if (str == null)
			return null;
		return new String("'" + str + "'");
	}
}
