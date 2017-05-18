/*
 * JsonParser.java
 * 1.0
 * 21 04 2017
 * (c) Pedko Volodymyr
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
 * @version 21 04 2017
 *
 * @author Pedko Volodymyr
 */
public class JsonParser {

	/**
	 * Main parser.
	 * 
	 * @param s
	 *            input String.
	 * 
	 * @return String as a result.
	 * 
	 * @exception ParseException
	 *                explains why and where the error occurs in source JSON
	 *                text.
	 * 
	 */
	public String parse(String s) {
		String result = null;
		try {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			JSONParser parser = new JSONParser();
			result = gson.toJson((JSONObject) parser.parse(s));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}
}