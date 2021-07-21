package com.google.sps.servlets;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import com.google.gson.Gson;

/**
 * Handles requests sent to the /hello URL. Try running a server and navigating
 * to /hello!
 */
@WebServlet("/custom-routine")
public class customRoutineServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ArrayList<String> arr = new ArrayList<String>(); // Creates an arrayList
        // Add Elements to the list
        arr.add("sample workout 1");
        arr.add("Isample workout 2");
        arr.add("sample workout 3");

        // convert to json string
        String json = convertToJsonUsingGson(arr);

        response.setContentType("application/json;");
        response.getWriter().println(json);
    }

    private String convertToJsonUsingGson(ArrayList<String> arr) {
        Gson gson = new Gson();
        String json = gson.toJson(arr);
        return json;
    }
}