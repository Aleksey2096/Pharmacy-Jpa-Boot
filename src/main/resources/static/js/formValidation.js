const SIXTEEN_DIGITS_REGEX = /^(\d{16})$/
const PHONE_REGEX = /^\+(\d{11})$/;

let form = document.getElementById("mainForm");

let valueRequired = form.elements["valueRequired"].value.trim();
let valueInvalid = form.elements["valueInvalid"].value.trim();
let notEnoughFunds = form.elements["notEnoughFunds"].value.trim();
let prescriptionRequired = form.elements["prescriptionRequired"].value.trim();

async function validateCart() {
    let paymentCardNumberValid = validateRegexp(form.elements["paymentCardNumber"], valueRequired, valueInvalid, SIXTEEN_DIGITS_REGEX);
    let contactPhoneValid = validateRegexp(form.elements["contactPhone"], valueRequired, valueInvalid, PHONE_REGEX);
    let deliveryAddressValid = hasValue(form.elements["deliveryAddress"], valueRequired);

    if (paymentCardNumberValid && contactPhoneValid && deliveryAddressValid) {

        let liArray = tableOrders.getElementsByTagName("li");
        let formUrl = '/HugePharma/client/cart/rest/validateOrder?totalPrice=' + String(Number(totalPriceSpan.innerHTML.slice(0, -1)));

        for (let i = 1; i < liArray.length; ++i) {
            let medicineProductId = liArray[i].getAttribute('data-id');
            let price = liArray[i].getAttribute('data-price');
            let amount = liArray[i].getElementsByClassName('medicineAmountInput')[0].value;
            formUrl += `&product=${medicineProductId} ${price} ${amount}`;
        }

        let response = await fetch(formUrl);
        let responseJson = await response.json();

        if (responseJson.result === 'notEnoughFunds') {
            alert(notEnoughFunds);
        } else if (responseJson.result === 'prescriptionRequired') {
            alert(prescriptionRequired);
        } else {
            checkout(liArray);
        }
    }
}

// show a message with a type of the input
function showMessage(input, message, type) {
    // get the small element and set the message
    const msg = input.parentNode.querySelector("span");
    msg.innerText = message;
    // update the class for the input
    input.classList.add(type ? "success" : "error");
    input.classList.remove(type ? "error" : "success");
    return type;
}

function showError(input, message) {
    return showMessage(input, message, false);
}

function showSuccess(input) {
    return showMessage(input, "", true);
}

function hasValue(input, message) {
    if (input.value.trim() === "") {
        return showError(input, message);
    }
    return showSuccess(input);
}

function validateRegexp(input, requiredMsg, invalidMsg, regexp) {
    // check if the value is not empty
    if (!hasValue(input, requiredMsg)) {
        return false;
    }

    const value = input.value.trim();
    if (!regexp.test(value)) {
        return showError(input, invalidMsg);
    }
    return true;
}
