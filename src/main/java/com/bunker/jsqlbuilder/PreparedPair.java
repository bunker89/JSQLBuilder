package com.bunker.jsqlbuilder;

import java.util.List;

import com.bunker.jsqlbuilder.setters.PreparedSetter;

public class PreparedPair {
	String query;
	List<PreparedSetter> setters;
	
	PreparedPair(String query, List<PreparedSetter> setters) {
		this.query = query;
		this.setters = setters;
	}
	
	public int setterLength() {
		if (setters == null)
			return 0;
		return setters.size();
	}
	
	@Override
	public String toString() {
		return query;
	}
}
