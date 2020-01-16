package com.bunker.jsqlbuilder.setters;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class StringSetter implements PreparedSetter {
	String data;
	
	public StringSetter(String data) {
		this.data = data;
	}
	
	public void setData(String data) {
		this.data = data;
	}
	
	@Override
	public void set(int index, PreparedStatement preparedStatement) throws SQLException {
		preparedStatement.setString(index, data);
	}
	
	@Override
	public String toString() {
		return data + "";
	}
}