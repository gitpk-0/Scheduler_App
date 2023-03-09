/**
 * File and package permissions for the controller
 */
module main.schedulerapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    /**
     * Files and packages the controller is allowed to use
     */
    opens main.scheduler to javafx.fxml;
    opens Controller to javafx.fxml;
    opens Model to javafx.fxml;
    opens Utility to javafx.fxml;
    opens Database to javafx.fxml;

    exports main.scheduler;
    exports Controller;
    exports Model;
    exports Utility;
    exports Database;
}