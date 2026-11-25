const loginForm = document.getElementById("loginForm");

if (loginForm) {

    loginForm.addEventListener("submit", async function(event) {

        event.preventDefault();

        const accountNo = document.getElementById("accountNo").value;
        const pin = document.getElementById("pin").value;

        const message = document.getElementById("message");

        try {
            const response = await fetch("/api/login", {
                method: "POST",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded"
                },
                body: new URLSearchParams({
                    accountNo: accountNo,
                    pin: pin
                })
            });

            const result = await response.json();

            if (response.ok) {

                sessionStorage.setItem("accountNo", accountNo);
                sessionStorage.setItem("customerName", result.name);

                window.location.href = "dashboard.html";

            } else {
                message.style.color = "red";
                message.textContent = result.message;
            }

        } catch (error) {
            message.style.color = "red";
            message.textContent = "Unable to connect to Java server.";
        }
    });
}


// DASHBOARD WELCOME MESSAGE
const welcomeMessage = document.getElementById("welcomeMessage");

if (welcomeMessage) {

    const customerName = sessionStorage.getItem("customerName");

    if (!customerName) {
        window.location.href = "login.html";
    } else {
        welcomeMessage.textContent = "Welcome, " + customerName + "!";
    }
}

async function checkBalance() {

    const accountNo = sessionStorage.getItem("accountNo");
    const resultDiv = document.getElementById("result");

    try {
        const response = await fetch("/api/balance", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: new URLSearchParams({
                accountNo: accountNo
            })
        });

        const result = await response.json();

        if (response.ok) {
            resultDiv.style.color = "green";
            resultDiv.textContent =
                "Available Balance: ₹" + result.balance;
        } else {
            resultDiv.style.color = "red";
            resultDiv.textContent = result.message;
        }

    } catch (error) {
        resultDiv.textContent = "Unable to connect to Java server.";
    }
}

async function depositMoney() {

    const amount = prompt("Enter deposit amount:");

    if (amount === null) return;

    const accountNo = sessionStorage.getItem("accountNo");
    const resultDiv = document.getElementById("result");

    if (amount.trim() === "" || !Number.isFinite(Number(amount)) ||
        Number(amount) <= 0) {
        resultDiv.style.color = "red";
        resultDiv.textContent = "Please enter a valid amount.";
        return;
    }

    try {
        const response = await fetch("/api/deposit", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: new URLSearchParams({
                accountNo: accountNo,
                amount: amount
            })
        });

        const result = await response.json();

        resultDiv.style.color = response.ok ? "green" : "red";
        resultDiv.textContent = result.message;

    } catch (error) {
        resultDiv.style.color = "red";
        resultDiv.textContent = "Unable to connect to Java server.";
    }
}