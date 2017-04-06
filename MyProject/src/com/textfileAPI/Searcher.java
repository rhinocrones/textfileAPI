/*
 * Searcher.java
 * 1.0
 * 05 March 2017
 * Copyright (c) Ped'ko Volodymyr
 */
package com.textfileAPI;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;

/**
 * Searcher is general class which contains two methods for text search and
 * extract meta data from file.
 *
 * @version 1.0 30 January 2017
 *
 * @author Ped'ko Volodymyr
 * 
 * @since 1.8
 */
@SuppressWarnings("unchecked")
public class Searcher {

	/**
	 * @return File file from resources.
	 */
	public static File getFile() {
		//Proper path
		// return new File("C:/Program Files/Apache Software Foundation/Tomcat 9.0/wtpwebapps/textfileAPINew/WEB-INF/classes/testfile.txt");
		
		/*return new File(Searcher.class.getResource("/testfile.txt").getPath());*/
		//Spike :)
		return new File(Searcher.class.getResource("/testfile.txt").getPath().replaceAll("%20", " "));
	}

	/**
	 * Basic method which get parameters from QueryServlet.
	 * 
	 * @param queryText
	 *            string which represents text to search in file. If parameter
	 *            is blank or missing - method return all text from file.
	 * @param queryCharLimit
	 *            integer which represents max number of chars in text that
	 *            method return.
	 * @param queryStringLength
	 *            integer which represents max string length. Method return
	 *            string which. doesn’t exceed that number.
	 * 
	 * @exception exceptions
	 *                produced by failed or interrupted I/O operations.
	 * 
	 * @return JSONArray list in which were added strings after search.
	 */
	public static JSONArray search(String queryText, int queryCharLimit,
	        int queryStringLength) {

		JSONArray result = new JSONArray();

		try {
			BufferedReader reader = new BufferedReader(
			        new FileReader(getFile()));
			int totalCharSum = 0;

			if (!queryText.isEmpty()) {
				while (reader.ready()) {
					String str = reader.readLine();

					if (queryStringLength > 0
					        && str.length() > queryStringLength
					        || str.isEmpty())
						continue;

					if (str.toLowerCase().contains(queryText)) {
						totalCharSum += str.length();
						if (totalCharSum > queryCharLimit)
							break;
						result.add(str);
					}
				}
			} else {
				while (reader.ready()) {
					String str = reader.readLine();

					if (queryStringLength > 0
					        && str.length() > queryStringLength
					        || str.isEmpty())
						continue;

					totalCharSum += str.length();
					if (totalCharSum > queryCharLimit)
						break;
					result.add(str);
				}
			}
			reader.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * Method extracts File Name, File Size, Creation Data.
	 * 
	 * @exception exceptions
	 *                produced by failed or interrupted I/O operations.
	 * 
	 * @return JSONObject in which were added elements of meta data from file.
	 */
	protected static JSONObject getMetaData() {

		JSONObject result = new JSONObject();

		Path file = getFile().toPath();
		String fileName = file.getFileName().toString();
		BasicFileAttributes attributes;
		SimpleDateFormat date = new SimpleDateFormat(
		        "MMMM d, yyyy 'at' HH:mm aaa");
		int kilobytes;

		try {
			attributes = Files.readAttributes(file, BasicFileAttributes.class);
			if (attributes.size() < 1000)
				kilobytes = 1;
			else
				kilobytes = (int) (attributes.size() / 1024);

			result.put("fileName", fileName);
			result.put("fileSize", kilobytes + "KB");
			result.put("metaData",
			        date.format(attributes.creationTime().toMillis()));

		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}