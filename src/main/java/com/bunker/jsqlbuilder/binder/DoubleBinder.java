package com.bunker.jsqlbuilder.binder;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DoubleBinder extends Binder {
	public DoubleBinder(String field) {
		super(field);
	}

	public DoubleBinder(String fieldName, String bindName) {
		super(fieldName, bindName);
	}
	
	@Override
	public Double bind(ResultSet set) throws SQLException {
		return set.getDouble(getFieldName());
	}
	
}