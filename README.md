\# ATM Simulation System



A simple ATM Simulation project developed using \*\*Java\*\*. This project demonstrates basic ATM operations using Java Swing for the graphical interface and CSV files for data storage.



\## Features



\* User Login using Account Number and PIN

\* Check Account Balance

\* Deposit Money

\* Withdraw Money

\* Mini Statement

\* Change PIN

\* Logout



\## Technologies Used



\* Java

\* Java Swing

\* File Handling

\* CSV Database

\* Git and GitHub



\## Project Structure



```text

ATM\_Simulation/

├── src/

│   ├── Main.java

│   ├── Account.java

│   ├── CSVManager.java

│   ├── ATMService.java

│   ├── Login.java

│   ├── Dashboard.java

│   ├── MiniStatement.java

│   └── ChangePin.java

├── data/

│   ├── accounts.csv

│   └── transactions.csv

├── out/

└── README.md

│

└── .gitignore





```



\## How to Run



1\. Clone or download this repository.

2\. Open the project folder in VS Code.

3\. Open the terminal.

4\. Compile the Java files:



```powershell

javac -d out src\\\*.java

```



5\. Run the application:



```powershell

java -cp out Main

```



\## Sample Login



Use the fictional sample account included in the demo dataset.



\* Account Number: `1000000001`

\* PIN: `1001`

\* Account Number: `1000000002`

\* PIN: `1122`





\## Project Purpose



This project was created for learning Java programming concepts, including:



\* Object-Oriented Programming

\* Java Swing GUI

\* File Handling

\* CSV Data Management

\* Basic Authentication

\* Event Handling



