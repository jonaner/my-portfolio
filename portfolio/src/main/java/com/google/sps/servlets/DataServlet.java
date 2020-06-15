// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {
  private final Gson gson = new Gson();
  private final DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
  private static final String TIME = "time";
  private static final String NEWEST_MESSAGE = "newest-message";
  private static final String COMMENT_LIMIT = "comment-limit";

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String limitString = request.getParameter("numComments");
    int limit = convertToInt(limitString);

    Query query = new Query("Message").addSort("time", SortDirection.DESCENDING);

    PreparedQuery results = datastore.prepare(query);

    List<String> messages = new ArrayList<>();
    for (Entity entity : results.asIterable()) {
      String fullMessage = (String) entity.getProperty(NEWEST_MESSAGE);
      long timestamp = (long) entity.getProperty(TIME);

      messages.add(fullMessage);
    }

    List<String> limitedList =
        Lists.reverse(messages).stream().limit(limit).collect(Collectors.toList());

    String json = gson.toJson(limitedList);
    response.setContentType("application/json;");
    response.getWriter().println(json);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String username =
        getParameter(request, /* name= */ "username", /* defaultValue= */ "Anonymous");
    String message = getParameter(request, /* name= */ "message-data", /* defaultValue= */ "");
    String fullMessage = String.format("%s: %s", username, message);
    long timestamp = System.currentTimeMillis();

    Entity messageEntity = new Entity("Message");
    messageEntity.setProperty(TIME, timestamp);
    messageEntity.setProperty(NEWEST_MESSAGE, fullMessage);

    datastore.put(messageEntity);

    response.sendRedirect("/write-message.html");
  }

  private String getParameter(HttpServletRequest request, String name, String defaultValue) {
    String value = request.getParameter(name);
    if (value == null || value.trim().isEmpty()) {
      return defaultValue;
    }
    return value;
  }

  private int convertToInt(String beingconverted) {
    int convertee;
    try {
      convertee = Integer.parseInt(beingconverted);
    } catch (NumberFormatException e) {
      System.err.println("Could not convert to int: " + beingconverted);
      return 5;
    }

    if (convertee < 1 || convertee > 10) {
      System.err.println("Number must be between 1 and 10");
      return 5;
    }
    return convertee;
  }
}
