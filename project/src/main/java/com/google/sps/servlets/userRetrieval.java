package com.google.sps.servlets;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.StructuredQuery.CompositeFilter;
import com.google.cloud.datastore.StructuredQuery.PropertyFilter;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.sps.data.User;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/user-retrieval")
public class userRetrieval extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User inputUser = new Gson().fromJson(request.getReader(), User.class);
        String user = inputUser.getuserName();
        String password = inputUser.getuserPassword();

        Datastore db = DatastoreOptions.getDefaultInstance().getService();

        Query<Entity> query = Query
                .newEntityQueryBuilder()
                .setKind("User")
                .setFilter(CompositeFilter
                    .and(PropertyFilter.eq("signupUsername", user), PropertyFilter.eq("signupPassword", password)))
                .build();
        QueryResults<Entity> queryResult = db.run(query);

        if (queryResult.hasNext()) {
            Entity entity = queryResult.next();
            long id = entity.getKey().getId();
            JsonObject retUser = new JsonObject();
            response.setContentType("application/json;");
            retUser.addProperty("id", id);
            response.getWriter().println(retUser);
        } else {
            response.setContentType("application/json;");
            response.getWriter().println("{\"user\": \"not in the database\"}");
        }
    }
}
