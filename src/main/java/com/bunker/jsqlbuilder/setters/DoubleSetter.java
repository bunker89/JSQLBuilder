package com.bunker.jsqlbuilder.setters;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DoubleSetter implements PreparedSetter {
	double data;
	
	public DoubleSetter(double data) {
		this.data = data;
	}
	
	public void setData(double data) {
		this.data = data;
	}
	
	@Override
	public void set(int index, PreparedStatement preparedStatement) throws SQLException {
		preparedStatement.setDouble(index, data);
	}
	
	@Override
	public String toString() {
		return data + "";
	}
}