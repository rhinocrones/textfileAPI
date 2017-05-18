/*
 * QueryServlet.java
 * 1.0
 * 21 04 2017
 * (c) Pedko Volodymyr
 */
package com.textfileAPI;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * QueryServlet is general servlet, which gets parameters from user, sends them
 * to Searcher and then renders the results in the response Object.
 *
 * @version 1.0 21 04 2017
 *
 * @author Pedko Volodymyr
 */
@WebServlet("/textfileAPI")
public class QueryServlet extends HttpServlet {

	/**
	 * If the parameter charLimit of request Object is empty, this will be
	 * charLimit.
	 */
	public static final int DEFAULT_CHAR_LIMIT = 10000;
	
	/**
	 * A default serial version ID to the selected type.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The type of responds file.
	 */
	private static final String CONTENT_TYPE = "application/json";

	/**
	 * Name of Limit chars parameter of the request.
	 */
	private static final String LIMIT_PARAMETER = "limit";

	/**
	 * Name of String Length parameter of the request.
	 */
	private static final String LENGTH_PARAMETER = "length";

	/**
	 * Name of q (query) parameter of the request.
	 */
	private static final String Q_PARAMETER = "q";

	/**
	 * Name of include Meta Data parameter of the request.
	 */
	private static final String INCLUDEMETADATA_PARAMETER = "includeMetaData";

	/**
	 * String name for meta-date of the respond file.
	 */
	private static final String TEXT = "text";

	/**
	 * This method parses parameters from html form, checks them and sends to
	 * the Searcher's methods. If parameters are empty, method sets default
	 * values. Method puts the results in JSONObject, sends them to JsonParser
	 * for pretty view and renders the response page.
	 *
	 * @param request
	 *            contains parameters from HTML form.
	 * @param response
	 *            render page with JSON content type.
	 * @exception
	 * 
	 * 				@throws
	 *                ServletException a general exception a servlet can throw
	 *                when it encounters difficulty.
	 * 
	 * @throws IOException
	 *             exceptions produced by failed or interrupted I/O operations.
	 */
	@Override
	public void doGet(final HttpServletRequest request, HttpServletResponse response) throws ServletException {

		Searcher searcher = new Searcher();
		JSONObject json = new JSONObject();

		int charLimit = (!request.getParameter(LIMIT_PARAMETER).isEmpty()) ?
				Integer.parseInt(request.getParameter(LIMIT_PARAMETER)) : DEFAULT_CHAR_LIMIT;

		int stringLength = (!request.getParameter(LENGTH_PARAMETER).isEmpty()) ?
				Integer.parseInt(request.getParameter(LENGTH_PARAMETER)) : charLimit;

		String searchText = request.getParameter(Q_PARAMETER);

		boolean includeMetaDate = Boolean.parseBoolean(request.getParameter(INCLUDEMETADATA_PARAMETER));

		if (includeMetaDate) {
			HashMap<String, JSONArray> map = new HashMap<String, JSONArray>();
			map.put(TEXT, searcher.search(searchText, charLimit, stringLength));
			json = new JSONObject(map);
		}

		response.setContentType(CONTENT_TYPE);
		
		try {
			response.getWriter().write((new JsonParser()).parse(json.toJSONString()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}