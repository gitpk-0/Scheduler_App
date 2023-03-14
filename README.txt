Appointment Scheduler
An interactive Scheduling GUI desktop application that integrates with a MySQL Relational Database.

Title: Appointment Scheduler
Author: Patrick Kell
Contact: pkell@wgu.edu
Application Version: 0.1
Date: March 14, 2023
IDE Version: IntelliJ IDEA 2022.3.1 Ultimate Edition
JDK Version: JDK-17.0.6
JavaFX Version: JavaFX-SDK-17.0.6
MySQL Connector Driver Version: mysql-connector-java-8.0.32

Prerequisites Applications to Install:
-MySQL Workbench
-Intellij IDEA

Directions for running the program:
-Download ZIP File of Project
-Extract the ZIP to a usable location on your machine
-Select File > Open in Intellij IDEA
-Select the 'Scheduler_App-master' folder where the ZIP was extracted to
-Select File > Project Structure > Libraries
-Click '+' symbol to add a 'New Project Library' from Maven
-Search for 'mysql-connector-java-8.0.32' and click 'OK' once found
-Execute the 'C195_db_ddl.txt' code in the database that was created in MySQL Workbench
-Execute the 'C195_db_dml.txt' code in the database that was created in MySQL Workbench
-Ensure location, database name, username and password match those of the variables declared in the "Utility/JDBC" file of this project
-Ensure the proper JDK and compatible JavaFX SDK is in place
-Run the program from the "main.scheduler/MainProgram" file
-Enter username "test" and password "test" to log into the application

List of Customers by Country Report:
-I chose to create another Table View table which allows the user to filter customer by the country they are associated with in the database. The user can choose a country to filter the results by with the 'Select a Country' combo box. Upon the country selection being made the user will see the customers which reside in the country that was selected.