/*
 * JsonParser.java
 * 1.0
 * 05 March 2017
 * Copyright (c) Ped'ko Volodymyr
 */
package com.textfileAPI;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Parse JSON string with help GSON library and return string as a result.
 *
 * @version 1.0 05 March 2017
 *
 * @author Ped'ko Volodymyr
 *
 * @since 1.7
 */
public class JsonParser {

	/**
	 * 
	 * @param s
	 *            input string
	 * 
	 * @return return string as a result.
	 * 
	 * @exception ParseException
	 *                explains why and where the error occurs in source JSON
	 *                text
	 * 
	 */
	public static String parse(String s) {
		String result = null;

		try {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			JSONParser parser = new JSONParser();
			JSONObject jo = (JSONObject) parser.parse(s);
			result = gson.toJson(jo);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}
}