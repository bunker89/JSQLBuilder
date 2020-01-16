package com.bunker.jsqlbuilder.binder;

import java.sql.ResultSet;
import java.sql.SQLException;

abstract public class Binder {
	String fieldName, bindName;
	
	Binder(String fieldName) {
		this(fieldName, fieldName);
	}
	
	Binder(String fieldName, String bindName) {
		this.fieldName = fieldName;
		this.bindName = bindName;
	}
	
	abstract public Object bind(ResultSet set) throws SQLException ;

	public String getFieldName() {
		return fieldName;
	}
	
	public String getBindName() {
		return bindName;
	}
	
	@Override
	public String toString() {
		return fieldName + " to " + bindName; 
	}
}