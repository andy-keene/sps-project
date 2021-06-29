package com.google.sps.servlets;

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
public class HelloWorldServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // For the time being, the Routines are hard coded into the servlet file
        // Can be improved upon

        ArrayList<String> cardioMoves = new ArrayList<String>(
                Arrays.asList("Dumbbell Swings", "Dumbbell Jack", "Burpee Front Raise", "Plank Jack",
                        "Skater Dumbbell Toss", "Weighted Squat to Lunge", "Plank Jack Row"));
        Routine cardio = new Routine("Head to Toe Cardio",
                "Do each move for 45 seconds and rest after each move for 10 seconds.", cardioMoves, 3);

        ArrayList<String> armsMoves = new ArrayList<String>(
                Arrays.asList("Shoulder Press With Lunge (Right Leg)", "Elevated Bicep Curls With Lunge (Left Leg)",
                        "Reverse Flyes", "Tricep Pressbacks", "Plank Row", "Prayer Pulse", "Upright Row"));
        Routine arms = new Routine("Extreme Arm Toner",
                "Do each move for 45 seconds and rest after each move for 15 seconds.", armsMoves, 3);

        ArrayList<String> legsMoves = new ArrayList<String>(
                Arrays.asList("Side Lunges (Right Leg)", "Releve Chairs", "Side Lunges (Left Leg)",
                        "Releve Plie Pulses", "Squat Lunges", "Leg Circles (Right Leg)", "Leg Circles (Left Leg)"));
        Routine legs = new Routine("Leg Shape and Tone",
                "Do each move for 1 minute and rest after each move for 15 seconds.", legsMoves, 3);

        ArrayList<String> abMoves = new ArrayList<String>(Arrays.asList("One Arm Rollup", "Weighted Russian Twists",
                "Weighted Toe Reach", "Weighted Knee Drives (Left Leg)", "Weighted Knee Drives (Right Leg)",
                "Side Plank Twists (Left Leg)", "Side Plank Twists (Right Leg)"));
        Routine abs = new Routine("Extreme Ab Toner",
                "Do each move for 1 minute and rest for 15 seconds after each move.", abMoves, 3);

        ArrayList<String> glutesMoves = new ArrayList<String>(Arrays.asList("Crab Walks",
                "Straight Leg Lift (Right Leg)", "Pointed Butt Lift (Right Leg)", "Fire Hydrant (Right Leg)",
                "Straight Leg Lift (Left Leg)", "Pointed Butt Lift (Left Leg)", "Fire Hydrant (Left Leg)"));
        Routine glutes = new Routine("Glute Shape and Tone",
                "Do each move for 1 minute and rest for 30 seconds after each set", glutesMoves, 3);

        ArrayList<String> backShoulderMoves = new ArrayList<String>(Arrays.asList("Walnut Crushers", "Bird Dog Pulses",
                "Parachuter", "Flying Bird Dog", "Breast Stroke", "Grasshopper"));
        Routine backShoulder = new Routine("Ultimate Back/Shoulder Workout",
                "Do each move for 45 seconds and rest for 15 seconds between each move.", backShoulderMoves, 5);

        ArrayList<Routine> routines = new ArrayList<Routine>(Arrays.asList(cardio, arms, legs, glutes, backShoulder));

        String json = convertToJson(routines);

        response.setContentType("application/json;");
        response.getWriter().println(json);
    }

    private String convertToJson(ArrayList<Routine> data) {
        Gson gson = new Gson();
        return gson.toJson(data); // return a json object
    }
}
