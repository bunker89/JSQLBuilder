package com.bunker.jsqlbuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.bunker.jsqlbuilder.setters.PreparedSetter;

public abstract class PreparedQueryBuilder {
	public PreparedStatement setPreparedStatement(Connection connection) throws JSQLException {
		PreparedPair pair = build();
		PreparedStatement pstmt = null;
		try {
			pstmt = connection.prepareStatement(pair.query);
			
			int index = 1;
			for (PreparedSetter setter : pair.setters) {
				setter.set(index++, pstmt);
			}
		} catch (SQLException e) {
			throw new JSQLException(pstmt);
		}
		return pstmt;
	}
	
	public PreparedStatement setPreparedStatement(Connection connection, int autoGeneratedKeys) throws JSQLException {
		PreparedPair pair = build();
		PreparedStatement pstmt = null;
		try {
			pstmt = connection.prepareStatement(pair.query, autoGeneratedKeys);
			
			int index = 1;
			for (PreparedSetter setter : pair.setters) {
				setter.set(index++, pstmt);
			}
		} catch (SQLException e) {
			throw new JSQLException(pstmt);
		}
		return pstmt;
	}
	
	public PreparedStatement setPreparedStatement(Connection connection, int []columnIndexed) throws JSQLException {
		PreparedPair pair = build();
		PreparedStatement pstmt = null;
		try {
			pstmt = connection.prepareStatement(pair.query, columnIndexed);
			
			int index = 1;
			for (PreparedSetter setter : pair.setters) {
				setter.set(index++, pstmt);
			}
		} catch (SQLException e) {
			throw new JSQLException(pstmt);
		}
		return pstmt;
	}
	
	public PreparedStatement setPreparedStatement(Connection connection, String []columnNames) throws JSQLException {
		PreparedPair pair = build();
		PreparedStatement pstmt = null;
		try {
			pstmt = connection.prepareStatement(pair.query, columnNames);
			
			int index = 1;
			for (PreparedSetter setter : pair.setters) {
				setter.set(index++, pstmt);
			}
		} catch (SQLException e) {
			throw new JSQLException(pstmt);
		}
		return pstmt;
	}
	
	public PreparedStatement setPreparedStatement(Connection connection, int resultType, int resultSetConcurrency) throws JSQLException {
		PreparedPair pair = build();
		PreparedStatement pstmt = null;
		try {
			pstmt = connection.prepareStatement(pair.query, resultType, resultSetConcurrency);
			
			int index = 1;
			for (PreparedSetter setter : pair.setters) {
				setter.set(index++, pstmt);
			}
		} catch (SQLException e) {
			throw new JSQLException(pstmt);
		}
		return pstmt;
	}
	
	public PreparedStatement setPreparedStatement(Connection connection, int resultType, int resultSetConcurrency, int resultSetHoldability) throws JSQLException {
		PreparedPair pair = build();
		PreparedStatement pstmt = null;
		try {
			pstmt = connection.prepareStatement(pair.query, resultType, resultSetConcurrency, resultSetHoldability);
			
			int index = 1;
			for (PreparedSetter setter : pair.setters) {
				setter.set(index++, pstmt);
			}
		} catch (SQLException e) {
			throw new JSQLException(pstmt);
		}
		return pstmt;
	}
	
	abstract PreparedPair build();
}