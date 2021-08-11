package com.google.sps.servlets;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.FullEntity;
import com.google.cloud.datastore.KeyFactory;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

@WebServlet("/Create-Account")
public class createAccountServlet<User> extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // get parameters/user input for account creation
        String userEmail = request.getParameter("Email Address");
        String userName = request.getParameter("Username");
        String userPassword = request.getParameter("password");
      
        // clean inputs
        if (userEmail != null) {
            userEmail = Jsoup.clean(userEmail, Whitelist.none());
        }
         if (userName != null) {
            userName = Jsoup.clean(userName, Whitelist.none());
        }
        if (userPassword != null) {
            userPassword = Jsoup.clean(userPassword, Whitelist.none());
        }
        
        // Sets kind and build User Entity
        Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
        KeyFactory keyFactory = datastore.newKeyFactory().setKind("User");
        FullEntity userEntity = Entity.newBuilder(keyFactory.newKey())
            .set("Email Address", userEmail)
            .set("Username", userName)
            .set("password", userPassword)
            .build();
        datastore.put(userEntity);
    }
}