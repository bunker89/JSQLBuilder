package com.bunker.jsqlbuilder.binder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONBinder {
	public JSONObject bindNew(ResultSet resultSet, List<Binder> binders) throws JSONException, SQLException {
		JSONObject json = new JSONObject();
		bind(resultSet, binders, json);
		return json;
	}
	
	public void bind(ResultSet resultSet, List<Binder> binders, JSONObject json) throws JSONException, SQLException {
		for (Binder binder : binders) {
			json.put(binder.bindName, binder.bind(resultSet));
		}
	}
}