/* Index page */


//add row links to the table of medicines
document.addEventListener("DOMContentLoaded", () => {
    const rows = document.querySelectorAll("li[data-href]");
    rows.forEach(row => {
        row.addEventListener("click", () => {
            window.location.href = row.dataset.href;
        });
    });
});


/* Medicine product */


//add medicine product to user's shopping cart
for (let form of document.getElementsByClassName('addToCartForm')) {
    form.addEventListener('submit', async function (event) {
        if (this.elements['isLoggedIn'].value === 'true') {
            event.preventDefault();

            let csrfToken = document.querySelector('meta[name=_csrf]').content;
            let csrfTokenName = document.querySelector('meta[name=_csrf_header]').content;

            await fetch(this.action, {
                method: 'POST',
                headers: new Headers([[csrfTokenName, csrfToken]])
            });

//            await fetch(this.action);
        }
    });
}


/* Various pages */


//allows only zero or positive integer in input field
for (let input of document.getElementsByClassName('integerInput')) {
    input.addEventListener('input', function () {
        this.value = this.value.replace(/[^0-9]/g, '');
    });
}

//allows only zero or positive decimal in input field
for (let input of document.getElementsByClassName('decimalInput')) {
    input.addEventListener('keypress', function (event) {
        let charCode = (event.which) ? event.which : event.keyCode;
        //Check if the text already contains the '.' character
        if (charCode === 46) {
            if (this.value.indexOf('.') !== -1) {
                event.preventDefault();
            }
        } else {
            if (charCode > 31 && (charCode < 48 || charCode > 57)) {
                event.preventDefault();
            }
        }
    });
}
