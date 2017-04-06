/*
 * QueryServlet.java
 * 1.0
 * 05 March 2017
 * Copyright (c) Ped'ko Volodymyr
 */
package com.textfileAPI;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

/**
 * QueryServlet is general servlet which get parameters from user, send them to
 * Searcher and then render the results in the response.
 *
 * @version 1.0 30 January 2017
 *
 * @author Ped'ko Volodymyr
 * 
 * @since 1.8
 */
@SuppressWarnings({ "unchecked", "serial" })
@WebServlet("/textfileAPI")
public class QueryServlet extends HttpServlet {

	/**
	 * Parse parameters from html form, check them and send to the Searcher's
	 * methods. If parameters are empty, set default values. Result put in
	 * JSONObject, send them to JsonParser for pretty view and render the
	 * response page.
	 *
	 * @param request
	 *            contains parameters from HTML form
	 * @param response
	 *            render page with JSON content type
	 * @exception
	 * 
	 *                @throws
	 *                ServletException a general exception a servlet can throw
	 *                when it encounters difficulty.
	 * 
	 * @throws IOException
	 *             exceptions produced by failed or interrupted I/O operations.
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {

			String searchText = "";
			int charLimit = 10000;
			int stringLength = 0;
			boolean metaData = false;

			JSONObject json = new JSONObject();

			String queryText = request.getParameter("q");
			if (!queryText.isEmpty() && queryText.trim().length() > 0)
				searchText = queryText;

			if (!request.getParameter("limit").isEmpty()) {
				int queryLimit = Integer
				        .parseInt(request.getParameter("limit"));
				if (queryLimit != 0)
					charLimit = queryLimit;
			}
			if (!request.getParameter("length").isEmpty()) {
				int queryLength = Integer
				        .parseInt(request.getParameter("length"));
				if (queryLength != 0)
					stringLength = queryLength;
			}

			if (request.getParameter("includeMetaData").equals("true"))
				metaData = true;

			json.put("text", Searcher.search(searchText.toLowerCase(),
			        charLimit, stringLength));

			if (metaData) {
				json.put("metaData", Searcher.getMetaData());
			}

			response.setContentType("application/json");
			response.getWriter().write(JsonParser.parse(json.toJSONString()));
	}
}