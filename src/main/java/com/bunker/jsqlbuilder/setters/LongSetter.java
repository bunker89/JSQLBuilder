package com.bunker.jsqlbuilder.setters;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LongSetter implements PreparedSetter {
	long data;
	
	public LongSetter(long data) {
		this.data = data;
	}
	
	public void setData(long data) {
		this.data = data;
	}
	
	@Override
	public void set(int index, PreparedStatement preparedStatement) throws SQLException {
		preparedStatement.setLong(index, data);
	}
	
	@Override
	public String toString() {
		return data + "";
	}
}