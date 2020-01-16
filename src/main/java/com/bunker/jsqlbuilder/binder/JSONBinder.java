package com.bunker.jsqlbuilder.binder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONBinder {
	public JSONObject bind(ResultSet resultSet, List<Binder> binders) throws JSONException, SQLException {
		JSONObject json = new JSONObject();
		for (Binder binder : binders) {
			json.put(binder.bindName, binder.bind(resultSet));
		}
		return json;
	}
}