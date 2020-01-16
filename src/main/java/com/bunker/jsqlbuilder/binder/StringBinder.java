package com.bunker.jsqlbuilder.binder;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StringBinder extends Binder {
	public StringBinder(String field) {
		super(field);
	}

	public StringBinder(String fieldName, String bindName) {
		super(fieldName, bindName);
	}

	@Override
	public String bind(ResultSet set) throws SQLException {
		return set.getString(getFieldName());
	}
}