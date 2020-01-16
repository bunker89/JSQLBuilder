package com.bunker.jsqlbuilder.setters;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface PreparedSetter {
	public void set(int index, PreparedStatement preparedStatement) throws SQLException ;
}
