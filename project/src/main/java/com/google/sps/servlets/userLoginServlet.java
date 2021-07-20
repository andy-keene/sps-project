package com.google.sps.servlets;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.StructuredQuery.PropertyFilter;
import com.google.gson.Gson;
import com.google.sps.data.User;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/user-login")
public class userLoginServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Retrieve user input data from js file
        User inputUser = new Gson().fromJson(request.getReader(), User.class);
        String userInput = inputUser.getuserName();
        String passwordInput = inputUser.getuserPassword();

        // Search input for @ symbol and change input type to email if necessary.
        // This will affect the query builder.
        String inputType = "signupUsername";
        if (userInput.contains("@"))
            inputType = "signupEmail";

        Datastore dataStore = DatastoreOptions.getDefaultInstance().getService();
        Query<Entity> query = Query.newEntityQueryBuilder().setKind("User")
                .setFilter(PropertyFilter.eq(inputType, userInput)).build();
        QueryResults<Entity> queryResults = dataStore.run(query);

        // Checks query results to see if an entity exists based on the username or
        // email given.
        // It then checks if the input password matches the password associated with the
        // found entity.
        if (queryResults.hasNext()) {
            String responseId;
            Entity user = queryResults.next();
            String pass = user.getString("signupPassword").replaceAll("\"", "");
            String userId = user.getKey().getId().toString();

            // Checks if input and datastore passwords match.
            if (pass.equals(passwordInput)) {
                response.setContentType("application/json;");
                responseId = "{\"userId\":" + userId + " }";
                response.getWriter().println(responseId);
            }
        }
    }
}
