package com.bunker.jsqlbuilder.setters;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FloatSetter implements PreparedSetter {
	float data;
	
	public FloatSetter(float data) {
		this.data = data;
	}
	
	public void setData(float data) {
		this.data = data;
	}
	
	@Override
	public void set(int index, PreparedStatement preparedStatement) throws SQLException {
		preparedStatement.setFloat(index, data);
	}
	
	@Override
	public String toString() {
		return data + "";
	}
}