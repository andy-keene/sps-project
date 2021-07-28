// Copyright 2020 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

/**
 * On button click, show/hide specific workouts in each section
 */

async function showWorkoutDetails(elementContainer, ID) {

    const responseFromServer = await fetch('/showRoutine');
    const routineList = await responseFromServer.json();

    const routineContainer = document.getElementById(elementContainer);

    routineContainer.innerText = "";

    var routineTitles = "";

    for (var i = 0; i < routineList.length; i++) {
        if (routineList[i].type == ID) {

            // var moveSet = "";

            // for (var k = 0; k < routineList[i].moveSet.length; k++) {
            //    moveSet = moveSet + routineList[i].moveSet[k] + "\n";
            // }

            var routineButton = document.createElement("button");

            routineButton.innerText = routineList[i].title;
            routineButton.setAttribute("type", "button");
            routineButton.setAttribute("class", "butn btn-info");

            routineButton.onclick = function () { return startWorkout(this) };

            routineContainer.appendChild(routineButton);

            var emptySpace = document.createElement("p");
            emptySpace.innerText = "\n\n";

            routineContainer.appendChild(emptySpace);
        }
    }

    var showHideWorkoutButton = document.getElementById(elementContainer+"-button");

    if (routineContainer.style.display === "block") {
        showHideWorkoutButton.innerText = "Show Routines";
        routineContainer.style.display = "none";
    } else {
        showHideWorkoutButton.innerText = "Hide Routines";
        routineContainer.style.display = "block";
    }

    console.log(routineList);
}

/**
 * Start a predefined workout routine by redirecting to a separate workout page to view all details/gifs of moves
 */
function startWorkout(routine) {

    localStorage.setItem("routine_title", routine.innerText);
    window.location.href = "start-workout.html";

}

function generateWorkout() {
    console.log(localStorage.getItem("routine_title"));

    var workoutContainer = document.getElementById('workout-container');
    const routineTitle = localStorage.getItem("routine_title");

    console.log(workoutContainer);

    const params = new URLSearchParams();
    params.append('routineTitle', routineTitle);

    fetch('/generateRoutine', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(routineTitle)
    }).then(response => response.json())
        .then((routine) => {
            console.log(routine);

            var headerTitle = document.createElement("h3");
            headerTitle.innerText = routine.routineObj.title + "\n";

            var headerDescription = document.createElement("h5");
            headerDescription.innerText = routine.routineObj.description + "\n";

            var headerSets = document.createElement("h5");
            headerSets.innerText = "Sets: " + routine.routineObj.sets + "\n";

            console.log(headerTitle);
            console.log(headerDescription);
            console.log(headerSets);

            workoutContainer.appendChild(headerTitle);
            workoutContainer.appendChild(headerDescription);
            workoutContainer.appendChild(headerSets);

            for (var i = 0; i < routine.routineObj.moveSet.length; i++) {

                var container = document.createElement("div");
                container.setAttribute("class", "container");

                var row = document.createElement("div");
                row.setAttribute("class", "row");

                var columnOne = document.createElement("div");
                columnOne.setAttribute("class", "col-sm");

                var columnTwo = document.createElement("div");
                columnTwo.setAttribute("class", "col-sm");

                var move = document.createElement("p");
                move.innerText = routine.routineObj.moveSet[i];

                var moveGif = document.createElement("img");
                moveGif.setAttribute("src", "/images/" + routine.gifs[i]);
                moveGif.setAttribute("width", "80");
                moveGif.setAttribute("height", "80");

                columnOne.appendChild(move);
                columnTwo.appendChild(moveGif);
                row.appendChild(columnOne);
                row.appendChild(columnTwo);
                container.appendChild(row);

                workoutContainer.appendChild(container);

            }

            var startWorkoutButton = document.getElementById("start-workout-button");
            startWorkoutButton.style.display = "none";

            var endButtonDiv = document.createElement("div");
            endButtonDiv.style.margin = "auto";

            var endWorkoutButton = document.createElement("button");
            endWorkoutButton.setAttribute("type", "button");
            endWorkoutButton.setAttribute("class", "butn btn-info");
            endWorkoutButton.innerText = "End";

            endWorkoutButton.onclick = function () { return toExploreRoutines() };

            endButtonDiv.appendChild(endWorkoutButton);

            workoutContainer.appendChild(endButtonDiv);

        });


}

// Redirect to Explore Routines page
function toExploreRoutines() {
    window.location.href = "explore-routines.html";
}
