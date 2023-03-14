# Appointment Scheduler

<h3>An interactive Scheduling GUI desktop application that integrates with a MySQL Relational Database.</h3>

<ul>
<li> Title: Appointment Scheduler
<li> Author: Patrick Kell
<li> Contact: pkell@wgu.edu
<li> Application Version: 0.1
<li> Date: March 14, 2023
<li> IDE Version: IntelliJ IDEA 2022.3.1 Ultimate Edition
<li> JDK Version: JDK-17.0.6
<li> JavaFX Version: JavaFX-SDK-17.0.6
<li> MySQL Connector Driver Version: mysql-connector-java-8.0.32
</ul>

<h4>Prerequisites Applications to Install:</h4>
<ul>
<li>MySQL Workbench</li>
<li>Intellij IDEA</li>
</ul>


<h4>Directions for running the program:</h4>
<ul>
<li>Download ZIP File of Project</li>
<li>Extract the ZIP to a usable location on your machine</li>
<li>Select File > Open in Intellij IDEA</li>
<li>Select the 'Scheduler_App-master' folder where the ZIP was extracted to</li>
<li>Select File > Project Structure > Libraries</li>
<li>Click '+' symbol to add a 'New Project Library' from Maven</li>
<li>Search for 'mysql-connector-java-8.0.32' and click 'OK' once found</li>
<li>Execute the 'C195_db_ddl.txt' code in the database that was created in MySQL Workbench</li>
<li>Execute the 'C195_db_dml.txt' code in the database that was created in MySQL Workbench</li>
<li>Ensure location, database name, username and password match those of the variables declared in the "Utility/JDBC" file of this project</li>
<li>Ensure the proper JDK and compatible JavaFX SDK is in place</li>
<li>Run the program from the "main.scheduler/MainProgram" file</li>
<li>Enter username "test" and password "test" to log into the application</li>
</ul>


<h4>List of Customers by Country Report:</h4>
<li>I chose to create another Table View table which allows the user to filter customer by the country they are associated with in the database. The user can choose a country to filter the results by with the 'Select a Country' combo box. Upon the country selection being made the user will see the customers which reside in the country that was selected.</li>