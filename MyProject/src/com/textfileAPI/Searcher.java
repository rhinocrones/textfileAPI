/*
 * Searcher.java
 * 1.0
 * 21 04 2017
 * (c) Pedko Volodymyr
 */
package com.textfileAPI;

import org.json.simple.JSONArray;

import java.io.*;

/**
 * Searcher is general class which contains two methods for text search and
 * extract meta data from file.
 *
 * @version 1.0 21 04 2017
 *
 * @author Pedko Volodymyr
 */
@SuppressWarnings("unchecked")
public class Searcher {
	/**
	 * Regex, that helps us to divide text into paragraphs.
	 */
	private static final String PARAGRAPH_SPLIT_REGEX = "(\\s{4})";

	/**
	 * The name of file, that contains data.
	 */
	private static final String FILE_NAME = "/testfile.txt";

	/**
	 * The encoding of given file with data.
	 */
	private static final String ENCODING_OF_FILE = "Utf-8";

	/**
	 * The minimal length of respond Json (The length of {"text":[]} )
	 */
	private static final int MIN_JSON_LENGTH = 11;

	/**
	 * We need this constant, because each data in JSON respond ends with ','
	 * and in " " (quotes). Thats way, if one needs to calculate the length of
	 * Json respond, he need to add extra because of Json respond body.
	 */
	private static final int ADD_LENGTH = 3;

	/**
	 * Basic method, which get parameters from QueryServlet.
	 * 
	 * @param queryText
	 *            string which represents text to search in file. If parameter
	 *            is blank or missing - method return all text from file.
	 * @param queryCharLimit
	 *            integer which represents max number of chars in text that
	 *            method return.
	 * @param queryStringLength
	 *            integer which represents max string length. Method return
	 *            string which. doesn�t exceed that number.
	 * 
	 * @exception exceptions
	 *                produced by failed or interrupted I/O operations.
	 * 
	 * @return JSONArray list in which were added strings after search.
	 */
	public JSONArray search(final String queryText, final int queryCharLimit, final int queryStringLength) {
		JSONArray jsonArray = new JSONArray();
		int jsonLength = MIN_JSON_LENGTH;
		for (String paragraph : getInfoFromFile()) {
			if (paragraph.contains(queryText)) {
				paragraph = paragraph.replace("\n", "").replace("\r", "");
				if (paragraph.length() <= queryStringLength && (jsonLength + paragraph.length() <= queryCharLimit)) {
					jsonArray.add(paragraph);
					jsonLength += paragraph.length() + ADD_LENGTH;
				} else if (jsonLength + queryStringLength <= queryCharLimit) {
					jsonArray.add(paragraph.substring(0, queryStringLength));
					jsonLength += queryStringLength + ADD_LENGTH;
				}
			}
			if (jsonArray.toJSONString().length() >= queryCharLimit) {
				return jsonArray;
			}
		}
		return jsonArray;
	}

	/**
	 * Returns information from the given file with data in String[].
	 * 
	 * @return String[] Information from the file in String[].
	 */
	public String[] getInfoFromFile() {
		String[] paragraphs = null;
		try (FileInputStream fis = new FileInputStream(
				new File(Searcher.class.getResource(FILE_NAME).getPath().replaceAll("%20", " ")));) {
			byte[] content = new byte[fis.available()];
			fis.read(content);
			fis.close();
			paragraphs = new String(content, ENCODING_OF_FILE).split(PARAGRAPH_SPLIT_REGEX);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return paragraphs;
	}
}