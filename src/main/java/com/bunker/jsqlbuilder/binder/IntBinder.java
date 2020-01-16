package com.bunker.jsqlbuilder.binder;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IntBinder extends Binder {
	public IntBinder(String field) {
		super(field);
	}

	public IntBinder(String fieldName, String bindName) {
		super(fieldName, bindName);
	}

	@Override
	public Integer bind(ResultSet set) throws SQLException {
		return set.getInt(getFieldName());
	}
}
