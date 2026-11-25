# ATM Simulation System



## Introduction



The ATM Simulation System is a Java-based desktop application that simulates the basic operations of an Automated Teller Machine (ATM).



The application provides a graphical user interface using Java Swing. It allows users to log in using their account number and PIN and perform banking operations such as checking balance, depositing money, withdrawing money, viewing transaction history, and changing their PIN.



Account and transaction information is stored in CSV files using Java file handling.



## Objectives



The main objectives of this project are:



1. To develop a simple ATM simulation using Java.

2. To understand Object-Oriented Programming concepts.

3. To implement a graphical user interface using Java Swing.

4. To perform file handling operations using CSV files.

5. To provide basic banking operations.

6. To maintain transaction records.

7. To understand the interaction between different Java classes.



## Features



\* \*\*User Login:\*\* Login using account number and PIN.

\* \*\*Check Balance:\*\* Display the current account balance.

\* \*\*Deposit Money:\*\* Add money to an account.

\* \*\*Withdraw Money:\*\* Withdraw money from an account after balance validation.

\* \*\*Mini Statement:\*\* View recent transaction details.

\* \*\*Change PIN:\*\* Update the account PIN.

\* \*\*Logout:\*\* Exit the user dashboard safely.

\* \*\*CSV Storage:\*\* Store account and transaction information in CSV files.



\## Technologies Used



| Technology    | Purpose                    |

| ------------- | -------------------------- |

| Java          | Core programming language  |

| Java Swing    | Graphical User Interface   |

| Java AWT      | GUI components and layouts |

| File Handling | Reading and writing files  |

| CSV Files     | Data storage               |

| VS Code       | Development environment    |

| Git           | Version control            |

| GitHub        | Source code hosting        |



\## Project Modules



\### Main Module



Starts the ATM application and opens the login screen.



\### Account Module



Stores account information such as account number, customer name, PIN, and balance.



\### CSV Manager Module



Reads account records, updates account details, and manages transaction data using CSV files.



\### ATM Service Module



Contains the main ATM operations, including login, balance checking, deposits, withdrawals, and PIN changes.



\### Login Module



Provides the login interface and validates account credentials.



\### Dashboard Module



Displays the available ATM operations after successful login.



\### Mini Statement Module



Displays the transaction history of the logged-in account.



\### Change PIN Module



Allows users to change their existing PIN.



\## Project Structure



```text

ATM\_Simulation/

│

├── src/

│   ├── Main.java

│   ├── Account.java

│   ├── CSVManager.java

│   ├── ATMService.java

│   ├── Login.java

│   ├── Dashboard.java

│   ├── MiniStatement.java

│   └── ChangePin.java

│

├── data/

│   ├── accounts.csv

│   └── transactions.csv

│

├── .gitignore

└── README.md

```



\## Execution Steps



\### Prerequisites



\* Java JDK installed.

\* VS Code or another Java-supported IDE.

\* Windows operating system or a compatible terminal.



\### Step 1: Clone the Repository



```bash

git clone YOUR\_GITHUB\_REPOSITORY\_URL

```



\### Step 2: Open the Project



Open the ATM\_Simulation folder in VS Code.



\### Step 3: Compile the Java Files



Open the terminal in the project directory and execute:



```powershell

javac -d out src\\\*.java

```



\### Step 4: Run the Application



```powershell

java -cp out Main

```



\### Step 5: Login



Enter a valid account number and PIN from the sample account dataset.



\### Step 6: Perform ATM Operations



After successful login, use the dashboard to access the available ATM operations.



\## Data Storage



The application uses two CSV files:



\* \*\*accounts.csv:\*\* Stores account number, customer name, PIN, and account balance.

\* \*\*transactions.csv:\*\* Stores transaction ID, account number, transaction type, amount, and date.



\## Future Enhancements



\* Add an admin dashboard.

\* Generate downloadable transaction statements.

\* Improve input validation and error handling.

\* Introduce a database such as MySQL.

\* Improve application security.



