// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

let modal = document.getElementById('contact-modal');
let openModal = document.getElementById('modal-open');
let closeModal = document.querySelector('.close-modal');

openModal.addEventListener('click', function () {
    modal.style.display = 'block';
})

closeModal.addEventListener('click', function () {
    modal.style.display = 'none';
})

window.addEventListener('click', function (e) {
    if (e.target == modal) {
        modal.style.display = 'none';
    }
})

var addToList = document.querySelector('#new-routine');
var listItem = document.querySelector('#inpValue');
var list = document.querySelector('#list');


addToList.addEventListener('submit', function (event) {


    // Ignore it if the list item is empty
    if (listItem.value.length < 1) return;

    // Add item to list
    list.innerHTML += '<li>' + listItem.value + '</li>';

    // Clear input
    listItem.value = '';

    // Save the list to localStorage
    localStorage.setItem('listItems', list.innerHTML);

}, false);

// Check for saved list items
var saved = localStorage.getItem('listItems');

// If there are any saved items, update our list
if (saved) {
    list.innerHTML = saved;
}

window.onload = function () {
    var myList = document.querySelectorAll(`[type*="checkbox"]`);
    myList.forEach(el => {
        var checked = JSON.parse(localStorage.getItem(el.id));
        document.getElementById(el.id).value = checked;
        list.innerHTML += '<li>' + el.value + '</li>';
    });
}
save = function () {
    var myList = document.querySelectorAll(`[type*="checkbox"]`);
    myList.forEach(el => {
        localStorage.setItem(el.id, el.value);
        console.log(el.id, el.value);
        list.innerHTML += '<li>' + el.value + '</li>';        
    })

}
/*function getSelectedCheckboxValues(name) {
    const checkboxes = document.querySelectorAll(`input[name="${name}"]:checked`);
    let values = [];
    checkboxes.forEach((checkbox) => {
        values.push(checkbox.value);
    });
    return values;
}

const btn = document.querySelector('#btn');
btn.addEventListener('click', (event) => {
    list.innerHTML += '<li>' + checkbox.value + '</li>';
    // Clear input
	listItem.value = '';
    alert(getSelectedCheckboxValues('workout'));
});   */