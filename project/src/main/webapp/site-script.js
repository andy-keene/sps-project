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
 * On button click, show more details about the workout
 */
async function showWorkoutDetails(elementContainer, ID) {
    // Associate ID with specific routine, hard coded for now
    /**
     * Cardio - 0
     * Arms - 1
     * Legs - 2
     * Abs - 3
     * Glutes - 4
     * Back/Shoulder - 5
     */
    const responseFromServer = await fetch('/showRoutine');
    const routineList = await responseFromServer.json();

    const routineContainer = document.getElementById(elementContainer);

    var routinesWithDetails = "";
/*
    for (var i = 0; i < routineList.length; i++) {
        if (routineList[i].routineType == ID) {

            var moveSet = "";

            for (var k = 0; k < routineList[i].moveSet.length; k++) {
                moveSet = moveSet + routineList.moveSet[k] + "\n";
            }
            routinesWithDetails = routinesWithDetails + "Title: " + routineList[i].title + "\nDescription: " + routineList[i].description + "\nSets: " + routineList[i].sets + "\nMove Set:\n" + moveSet + "\n\n";
        }
    } */

    console.log(routineList);

    routineContainer.innerText = routinesWithDetails;
}

/**
 * On button click, hide extra details about the workout
 */

function hideWorkoutDetails(elementContainer) {
    const routineContainer = document.getElementById(elementContainer);
    routineContainer.innerText = "";
}