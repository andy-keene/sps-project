package com.google.sps.servlets;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.FullEntity;
import com.google.cloud.datastore.KeyFactory;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.StructuredQuery.OrderBy;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/** Handles requests sent to the /showRoutine URL */
@WebServlet("/showRoutine")
public class predefinedRoutineServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // For the time being, the Routines are hard coded into the servlet file
        // Can be improved upon

        ArrayList<Routine> routines = new ArrayList<Routine>();

        Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
        Query<Entity> query = Query.newEntityQueryBuilder().setKind("Predefined-Routine").setOrderBy(OrderBy.desc("routineTitle"))
                .build();
        QueryResults<Entity> results = datastore.run(query);

        while (results.hasNext()) {
            Entity entity = results.next();

            String title = entity.getString("routineTitle");
            String type = entity.getString("routineType");
            String description = entity.getString("routineDescription");
            int sets = convertStringToInt(entity.getString("setNumber"));
            ArrayList<String> moves = convertStringItemsToArrayList(entity.getString("moveSet"));

            Routine currentRoutine = new Routine(title, description, type, moves, sets);
            routines.add(currentRoutine);
        }

        String json = convertToJson(routines);

        response.setContentType("application/json;");
        response.getWriter().println(json);
    }

    private String convertToJson(ArrayList<Routine> data) {
        Gson gson = new Gson();
        return gson.toJson(data); // return a json object
    }

    private int convertStringToInt(String s) {
        int num = Integer.parseInt(s);
        return num;
    }

    private ArrayList<String> convertStringItemsToArrayList(String s) {
        ArrayList<String> convertedString = new ArrayList<String>(Arrays.asList(s.split(",")));
        return convertedString;
    }
}
