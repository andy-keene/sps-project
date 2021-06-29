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
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

@WebServlet("/<nameOfCall>")
public class DataStoreServlet<Contact> extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String nameValue = request.getParameter("member-name");
        String emailValue = request.getParameter("member-email");

        String timestamp = LocalTime.now(ZoneId.of("America/Los_Angeles")).truncatedTo(ChronoUnit.SECONDS).toString();

        // clean inputs
        if (nameValue != null) {
            nameValue = Jsoup.clean(nameValue, Whitelist.none());
        }
        if (emailValue != null) {
            emailValue = Jsoup.clean(emailValue, Whitelist.none());
        }
     

        Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
        KeyFactory keyFactory = datastore.newKeyFactory().setKind("Member");
        FullEntity MemberEntity = Entity.newBuilder(keyFactory.newKey())
            .set("member-name", nameValue)
            .set("member-email", emailValue)
            .set("timestamp", timestamp)
            .build();
        datastore.put(memberEntity);

        // Print value in server logs.
        System.out.println("You submitted: " + nameValue + "\nEmail: " + emailValue);

        // Write value to the response for user.
        response.getWriter().println("print: " + nameValue);
        response.sendRedirect("member.html");
    }
}