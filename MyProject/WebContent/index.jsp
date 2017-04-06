<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
  <head>
    <title>Data Handler</title>
      <link rel="stylesheet" type="text/css" href="style.css">
  </head>
  <body bgcolor="#f8f8ff">
      <div class="parent">
          <form action="textfileAPI" method="get">
              <table class="out">
                  <tr>
                      <td bgcolor="teal" align="center">
                        Data Handler
                      </td>
                  </tr>
                  <tr>
                      <td>
                          <table class="inner">
                              <tr>
                                  <td>Search:</td>
                                  <td><input type="text" name="q" value="" title=""></td>
                              </tr>
                              <tr>
                                  <td>Chars Limit:</td>
                                  <td><input type="number" name="limit" min="0" title=""></td>
                              </tr>
                              <tr>
                                  <td>String Length:</td>
                                  <td><input type="number" name="length" min="0" title=""></td>
                              </tr>
                              <tr>
                                  <td>Include Meta Data?</td>
                                  <td align="center">
                                      <select name="includeMetaData" title="">
                                          <option value="false">No</option>
                                          <option value="true">Yes</option>
                                      </select>
                                  </td>
                              </tr>
                              <tr>
                                  <td colspan="2" align="center">
                                      <input id="button" type="submit" value="Serach">
                                  </td>
                              </tr>
                          </table>
                      </td>
                  </tr>
              </table>
          </form>
      </div>
  </body>
</html>