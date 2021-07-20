// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

//Selects error message within login form
function setFormMessage(formEmement, type, message) {
    const messageElement = formEmement.querySelector(".form__message");

    messageElement.textContent = message;
    messageElement.classList.remove("form__message--success", "form__message--error");
    messageElement.classList.add(`form__message--${type}`);
}

//Input Error for individual fields
function setInputError(inputElement, message) {
    inputElement.classList.add("form__input--error");
    inputElement.parentElement.querySelector(".form__input-error-message").textContent = message;
}
//Clears Input Error for individual fields
function clearInputError(inputElement) {
    inputElement.classList.remove("form__input--error");
    inputElement.parentElement.querySelector(".form__input-error-message").textContent = "";
}

//Function links from the create account to the login page and vice versa
document.addEventListener("DOMContentLoaded", () => {
    const loginForm = document.querySelector("#login");
    const createAccountForm = document.querySelector("#createAccount");

    //Hides login form and shows create account form
    document.querySelector("#linkCreateAccount").addEventListener("click", e => {
        e.preventDefault();
        loginForm.classList.add("form--hidden");
        createAccountForm.classList.remove("form--hidden");
    });

    // Hides create account form and shows login form
    document.querySelector("#linkLogin").addEventListener("click", e => {
        e.preventDefault();
        loginForm.classList.remove("form--hidden");
        createAccountForm.classList.add("form--hidden");
    });

    loginForm.addEventListener("submit", e => {
        e.preventDefault();
 
        //Perform Fetch Login.
        const data = {
            userName: document.getElementById('loginUser').value,
            userPassword: document.getElementById('loginPassword').value
        };
        
        fetch('/user-login', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json'},
            body: JSON.stringify(data),
        })
        .then(response => response.json())
        .then(data => {
            console.log('Success:', data);
            localStorage.setItem("user_id", data.userId);            
            window.location.href = "/dashboard-summary.html"
        })
        .catch((error) => {
            console.log(error);
            setFormMessage(loginForm, "error", "Invalid username or password");
        }); 
    });
    

    // error checking for username value
    document.querySelectorAll(".form__input").forEach(inputElement => {
        inputElement.addEventListener("blur", e => {
            if (e.target.id === "signupUsername" && e.target.value.length > 0 && e.target.value.length < 6) {
                setInputError(inputElement, "Username must be at least 6 characters long");
            }
        });

        inputElement.addEventListener("input", e => {
            clearInputError(inputElement);
        });
    });

    // error checking for password value
    document.querySelectorAll(".form__input").forEach(inputElement => {
        inputElement.addEventListener("blur", e => {
            if (e.target.id === "signupPassword" && e.target.value.length > 0 && e.target.value.length < 8) {
                setInputError(inputElement, "Password must be at least 8 characters long");
            }
        });

        inputElement.addEventListener("input", e => {
            clearInputError(inputElement);
        });
    });
});

