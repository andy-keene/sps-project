package com.google.sps.servlets;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.FullEntity;
import com.google.cloud.datastore.KeyFactory;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import java.security.NoSuchAlgorithmException;

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

        // Salt hash user password
        try {
            // message digest instance using SHA256 hash function
            MessageDigest md;
            md = MessageDigest.getInstance("SHA-256");

            // generate psuedo random numbers/salt
            SecureRandom randomGen = new SecureRandom();
            byte[] salt = new byte[16];
            randomGen.nextBytes(salt);

            // pass generated salt to messageDigest instance
            md.update(salt);

            // hash salted userPassword
            byte[] hash = md.digest(userPassword.getBytes(StandardCharsets.UTF_8));
            StringBuilder hashedPassword = new StringBuilder();
            for (byte b : hash) {
                hashedPassword.append(String.format("%02x", b));
            }

            System.out.println("array to string: " + Arrays.toString(salt));
            System.out.println("save pw: " + hashedPassword + " save salt:" + salt);

            // Set kind and build user entity. note: datastore doesn't store byte arrays
            Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
            KeyFactory keyFactory = datastore.newKeyFactory().setKind("User");
            FullEntity userEntity = Entity.newBuilder(keyFactory.newKey()).set("signupUsername", userName)
                    .set("signupEmail", userEmail).set("signupPassword", userPassword).set("salt", salt.toString())
                    .set("hashedPassword", hashedPassword.toString()).build();
            datastore.put(userEntity);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        // Redirects back to dashboard
        response.sendRedirect("/dashboard-summary.html");
    }
}