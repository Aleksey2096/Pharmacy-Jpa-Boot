/* Cart */


let ordersNum = 0;
let tableCarts = document.getElementById("tableCarts");
let tableOrders = document.getElementById("tableOrders");
let tableOrdersDiv = document.getElementById("tableOrdersDiv");
let tableCartsDiv = document.getElementById("tableCartsDiv");
let emptyOrdersContent = document.getElementById("emptyOrdersContent");
let emptyCartsContent = document.getElementById("emptyCartsContent");
let shoppingCartSize = document.getElementById('shoppingCartSize');
let orderItemsSize = document.getElementById('orderItemsSize');
let totalPriceSpan = document.getElementById('totalPriceSpan');
let mainForm = document.getElementById('mainForm');
let medicineProductIdSet = new Set();

processShoppingCart();

function processShoppingCart() {
    addDeleteFromCartFormsEventListeners();
    addAddToOrdersEventListeners();
}

//delete medicine product from user's shopping cart
function addDeleteFromCartFormsEventListeners() {
    for (let form of document.getElementsByClassName('deleteFromCartForm')) {
        form.addEventListener('submit', async function (event) {
            event.preventDefault();

            editShoppingCartSize();
            tableCarts.removeChild(form.closest('li'));
            editRowsNumbering(tableCarts);

            let csrfToken = document.querySelector('meta[name=_csrf]').content;
            let csrfTokenName = document.querySelector('meta[name=_csrf_header]').content;

            await fetch(this.action, {
                method: 'DELETE',
                headers: new Headers([[csrfTokenName, csrfToken]])
            });

        });
    }
}

function editShoppingCartSize() {
    let itemsArray = shoppingCartSize.textContent.split(' ');
    let cartSize = Number(itemsArray[0]) - 1;
    if (cartSize === 0) {
        emptyCartsContent.style.display = 'block';
        tableCartsDiv.style.display = 'none';
    } else {
        shoppingCartSize.textContent = String(cartSize) + ' ' + itemsArray[1];
    }
}

function editOrderItemsSize(value) {
    let itemsArray = orderItemsSize.textContent.split(' ');
    orderItemsSize.textContent = value + ' ' + itemsArray[1];
}

//add medicine storage to orders
function addAddToOrdersEventListeners() {
    for (let form of document.getElementsByClassName('addToOrderForm')) {
        form.addEventListener('submit', async function (event) {
            event.preventDefault();
            let medicineProductId = this.elements['medicine_product_id'].value;
            if (!medicineProductIdSet.has(medicineProductId)) {
                medicineProductIdSet.add(medicineProductId);

                let price = this.elements['price'].value;
                let amount = this.elements['amount'].value;
                let title = this.elements['title'].value;
                buildOrdersTableRow(medicineProductId, price, amount, title);
                if (ordersNum === 0) {
                    makeOrdersTableVisible();
                }
                ++ordersNum;
                editOrderItemsSize(ordersNum);
            }
        });
    }
}

function makeOrdersTableVisible() {
    emptyOrdersContent.style.display = 'none';
    tableOrdersDiv.style.display = 'block';
}

function makeEmptyOrdersContentVisible() {
    emptyOrdersContent.style.display = 'block';
    tableOrdersDiv.style.display = 'none';
}

function buildOrdersTableRow(medicineProductId, price, amount, title) {
    let currentRow = tableOrders.getElementsByTagName("li").length;
    let li = document.createElement("li");
    li.classList.add("table-row");
    li.setAttribute('data-id', medicineProductId);
    li.setAttribute('data-price', price);
    li.setAttribute('data-amount', amount);
    let numberDiv = document.createElement("div");
    numberDiv.classList.add('rowNumbering');
    numberDiv.classList.add('col');
    numberDiv.classList.add('col-1');
    numberDiv.innerText = String(currentRow);
    li.appendChild(numberDiv);

    let idDiv = document.createElement("div");
    idDiv.classList.add('col');
    idDiv.classList.add('col-2');
    idDiv.innerText = medicineProductId;
    li.appendChild(idDiv);

    let titleDiv = document.createElement("div");
    titleDiv.classList.add('col');
    titleDiv.classList.add('col-3');
    titleDiv.innerText = title;
    li.appendChild(titleDiv);

    let quantityDiv = document.createElement("div");
    quantityDiv.classList.add('col');
    quantityDiv.classList.add('col-4');
    quantityDiv.insertAdjacentHTML("beforeend", `
    <button type="button" class="minusButton">
        <svg class="minusSign" viewBox="0 0 100 100">
            <line x1="0" y1="50" x2="100" y2="50" stroke-width="10"></line>
        </svg>
    </button>
    <label for="amount"></label><input type="text" id="amount" name="amount"
                                       class="medicineAmountInput" maxlength="4" size="1"
                                       value="1">
    <button type="button" class="plusButton">
        <svg class="plusSign" viewBox="0 0 100 100">
            <line x1="0" y1="50" x2="100" y2="50" stroke-width="10"></line>
            <line x1="50" y1="0" x2="50" y2="100" stroke-width="10"></line>
        </svg>
    </button>
    `);
    li.appendChild(quantityDiv);

    let priceDiv = document.createElement("div");
    priceDiv.classList.add('col');
    priceDiv.classList.add('col-5');
    priceDiv.classList.add('sumCol');
    priceDiv.innerText = price;
    li.appendChild(priceDiv);

    let deleteDiv = document.createElement("div");
    deleteDiv.classList.add('col');
    deleteDiv.classList.add('col-6');
    let deleteFromOrdersForm = createDeleteFromOrdersForm();
    addDeleteFromOrdersFormEventListener(deleteFromOrdersForm);
    deleteDiv.appendChild(deleteFromOrdersForm);
    li.appendChild(deleteDiv);

    tableOrders.appendChild(li);

    addMedicineAmountInputEventListener(quantityDiv, price, amount);
    addMinusButtonEventListener(quantityDiv, price);
    addPlusButtonEventListener(quantityDiv, price, amount);
    countTotalPrice();
}

function createDeleteFromOrdersForm() {
    let deleteFromOrdersForm = document.createElement("form");
    deleteFromOrdersForm.setAttribute("method", "post");
    deleteFromOrdersForm.setAttribute("action", "dispatcher");
    deleteFromOrdersForm.classList.add('deleteFromOrdersForm');
    deleteFromOrdersForm.insertAdjacentHTML('beforeend', `
    <button type="submit" class="deleteOrderButton">
        <svg class="deleteOrderSign" viewBox="0 0 100 100">
            <line x1="0" y1="0" x2="100" y2="100" stroke-width="10"></line>
            <line x1="0" y1="100" x2="100" y2="0" stroke-width="10"></line>
        </svg>
    </button>
	`);
    return deleteFromOrdersForm;
}

function addDeleteFromOrdersFormEventListener(deleteFromOrdersForm) {
    deleteFromOrdersForm.addEventListener('submit', async function (event) {
        event.preventDefault();
        --ordersNum;
        let row = deleteFromOrdersForm.closest('li');
        medicineProductIdSet.delete(row.getAttribute('data-id'));
        tableOrders.removeChild(row);
        if (ordersNum === 0) {
            makeEmptyOrdersContentVisible();
        } else {
            editRowsNumbering(tableOrders);
            editOrderItemsSize(ordersNum);
            countTotalPrice();
        }
    });
}

function editRowsNumbering(source) {
    let liArray = source.getElementsByClassName('rowNumbering');
    for (let i = 0; i < liArray.length; ++i) {
        liArray[i].innerText = i + 1;
    }
}

//process medicineAmountInput
function addMedicineAmountInputEventListener(inputAmountCell, price, amount) {
    let medicineAmountInput = inputAmountCell.getElementsByClassName('medicineAmountInput')[0];
    medicineAmountInput.addEventListener('input', function (event) {
        if (/^[1-9][0-9]*$/.test(medicineAmountInput.value)) {
            if (medicineAmountInput.value > Number(amount)) {
                alert('There are less goods in stock!');
                medicineAmountInput.value = amount;
            }
            countSum(inputAmountCell, price);
        } else {
            alert('Only positive integers appropriate!');
            event.preventDefault();
        }
    });
}

//process minusButton
function addMinusButtonEventListener(inputAmountCell, price) {
    let minusButton = inputAmountCell.getElementsByClassName('minusButton')[0];
    let medicineAmountInput = inputAmountCell.getElementsByClassName('medicineAmountInput')[0];
    minusButton.addEventListener('click', function () {
        if (medicineAmountInput.value === "1") {
            alert("Amount can't be less than 1!");
        } else {
            medicineAmountInput.value = medicineAmountInput.value - 1;
            countSum(inputAmountCell, price);
        }
    });
}

//process plusButton
function addPlusButtonEventListener(inputAmountCell, price, amount) {
    let plusButton = inputAmountCell.getElementsByClassName('plusButton')[0];
    let medicineAmountInput = inputAmountCell.getElementsByClassName('medicineAmountInput')[0];
    plusButton.addEventListener('click', function () {
        if (medicineAmountInput.value === amount) {
            alert('There are no more goods in stock!');
        } else {
            medicineAmountInput.value = Number(medicineAmountInput.value) + 1;
            countSum(inputAmountCell, price);
        }
    });
}

function countSum(inputAmountCell, price) {
    let medicineAmountInput = inputAmountCell.getElementsByClassName('medicineAmountInput')[0];
    let sumCell = inputAmountCell.closest('li').getElementsByClassName("col-5")[0];
    sumCell.innerHTML = (medicineAmountInput.value * price).toFixed(2);
    countTotalPrice();
}

function countTotalPrice() {
    let totalSum = 0;
    for (let sumDiv of tableOrders.getElementsByClassName("sumCol")) {
        totalSum += Number(sumDiv.innerHTML);
    }
    totalPriceSpan.innerHTML = totalSum.toFixed(2) + '$';
}

function checkout(liArray) {
    for (let i = 1; i < liArray.length; ++i) {
        let medicineProductId = liArray[i].getAttribute('data-id');
        let price = liArray[i].getAttribute('data-price');
        let amount = liArray[i].getElementsByClassName('medicineAmountInput')[0].value;

        let ordersInput = document.createElement('input');
        ordersInput.setAttribute('type', 'hidden');
        ordersInput.setAttribute('name', 'product');
        ordersInput.setAttribute('value', `${medicineProductId} ${price} ${amount}`);
        mainForm.appendChild(ordersInput);
    }

    let totalPriceInput = document.createElement('input');
    totalPriceInput.setAttribute('type', 'hidden');
    totalPriceInput.setAttribute('name', 'totalPrice');
    totalPriceInput.setAttribute('value', String(Number(totalPriceSpan.innerHTML.slice(0, -1))));
    mainForm.appendChild(totalPriceInput);

    mainForm.submit();
}

//window.onbeforeunload = function (event) {
//	event.returnValue = false;
//};
