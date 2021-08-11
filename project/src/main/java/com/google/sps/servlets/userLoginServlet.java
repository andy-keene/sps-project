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
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

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
        // Hashes the salted input password and checks if the new hash matches the hash
        // stored in the database.
        if (queryResults.hasNext()) {

            String responseId;
            Entity user = queryResults.next();
            // String pass = user.getString("signupPassword").replaceAll("\"", "");
            String userId = user.getKey().getId().toString();
            String hashedPassword = user.getString("hashedPassword");
            String salt = user.getString("salt");

            System.out.println("load hashpw: " + hashedPassword + " load salt: " + salt);

            try {
                /*
                 * Checks if input and datastore passwords match. if
                 * (pass.equals(passwordInput)) { response.setContentType("application/json;");
                 * responseId = "{\"userId\":" + userId + " }";
                 * response.getWriter().println(responseId); }
                 */

                MessageDigest md;
                md = MessageDigest.getInstance("SHA-256");

                // use salt from database .getBytes() doesn't do what i'm thinking it does
                md.update(salt.getBytes());
                byte[] hash = md.digest(passwordInput.getBytes(StandardCharsets.UTF_8));
                StringBuilder newHashedPassword = new StringBuilder();
                for (byte b : hash) {
                    newHashedPassword.append(String.format("%02x", b));
                }

                // system out to see if salt is loading correctly (it isn't)
                System.out.println("salt array: " + salt.getBytes().toString() + "  salt getB: " + salt.getBytes());
                System.out.println("new hashpw: " + newHashedPassword.toString());

                if (hashedPassword.equals(newHashedPassword.toString())) {
                    response.setContentType("application/json;");
                    responseId = "{\"userId\":" + userId + " }";
                    response.getWriter().println(responseId);
                }

            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }

    }
}
