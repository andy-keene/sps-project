package com.google.sps.servlets;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.FullEntity;
import com.google.cloud.datastore.KeyFactory;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

@WebServlet("/form-handler")
public class formHandlerServlet<User> extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // Get the value entered in the form.
        String userName = request.getParameter("signupUsername");
        String userEmail = request.getParameter("signupEmail");
        String userPassword = request.getParameter("signupPassword");

        // Clean the values from user input
        if (userName != null & userEmail != null & userPassword != null) {
            userEmail = Jsoup.clean(userEmail, Whitelist.none());
            userName = Jsoup.clean(userName, Whitelist.none());
            userPassword = Jsoup.clean(userPassword, Whitelist.none());
        }

        // Set kind and build user entity
        Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
        KeyFactory keyFactory = datastore.newKeyFactory().setKind("User");
        FullEntity userEntity = Entity.newBuilder(keyFactory.newKey())
            .set("signupUsername", userName)
            .set("signupEmail", userEmail)
            .set("signupPassword", userPassword)
            .build();
        datastore.put(userEntity);

        //Redirects back to dashboard
        response.sendRedirect("/dashboard-summary.html");
    }
}