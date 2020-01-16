package com.bunker.jsqlbuilder;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JSQLException extends SQLException {
	private static final long serialVersionUID = 1L;
	public PreparedStatement pstmt;
	
	public JSQLException(PreparedStatement pstmt) {
		this.pstmt = pstmt;
	}
}
