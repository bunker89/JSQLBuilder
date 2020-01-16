package com.bunker.jsqlbuilder.binder;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StringBinder extends Binder {
	public StringBinder(String field) {
		super(field);
	}

	@Override
	public String bind(ResultSet set) throws SQLException {
		return set.getString(getFieldName());
	}
}