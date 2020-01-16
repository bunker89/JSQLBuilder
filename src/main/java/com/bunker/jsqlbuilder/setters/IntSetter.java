package com.bunker.jsqlbuilder.setters;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class IntSetter implements PreparedSetter {
	int data;
	
	public IntSetter(int data) {
		this.data = data;
	}
	
	public void setData(int data) {
		this.data = data;
	}
	
	@Override
	public void set(int index, PreparedStatement preparedStatement) throws SQLException {
		preparedStatement.setInt(index, data);
	}
	
	@Override
	public String toString() {
		return data + "";
	}
}
