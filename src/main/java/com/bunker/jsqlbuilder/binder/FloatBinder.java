package com.bunker.jsqlbuilder.binder;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FloatBinder extends Binder {
	public FloatBinder(String field) {
		super(field);
	}

	public FloatBinder(String fieldName, String bindName) {
		super(fieldName, bindName);
	}

	@Override
	public Float bind(ResultSet set) throws SQLException {
		return set.getFloat(getFieldName());
	}
}