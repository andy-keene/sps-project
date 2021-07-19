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
 * On button click, show specific workouts in each section
 */
async function showWorkoutDetails(elementContainer, ID) {

    const responseFromServer = await fetch('/showRoutine');
    const routineList = await responseFromServer.json();

    const routineContainer = document.getElementById(elementContainer);

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

            var routineName = routineList[i].title;
            routineButton.onclick = function () { return startWorkout(routineName) };
            routineContainer.appendChild(routineButton);
        }
    }

    console.log(routineList);
}

/**
 * On button click, hide extra details about the workout
 */

function hideWorkoutDetails(elementContainer) {
    const routineContainer = document.getElementById(elementContainer);
    routineContainer.innerText = "";
}

/**
 * Start a predefined workout routine by redirecting to a separate workout page to view all details/gifs of moves
 */
function startWorkout(routineTitle) {

    localStorage.setItem("routine_title", routineTitle);
    window.location.href = "start-workout.html";

}

function generateWorkout() {
    console.log(localStorage.getItem("routine_title"));

    const workoutContainer = document.getElementById('workout-container');
    const routineTitle = localStorage.getItem("routine_title");

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
        });


}