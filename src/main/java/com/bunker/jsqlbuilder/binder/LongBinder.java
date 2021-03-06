package com.bunker.jsqlbuilder.binder;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LongBinder extends Binder {
	public LongBinder(String field) {
		super(field);
	}

	public LongBinder(String fieldName, String bindName) {
		super(fieldName, bindName);
	}
	
	@Override
	public Long bind(ResultSet set) throws SQLException {
		return set.getLong(getFieldName());
	}
}