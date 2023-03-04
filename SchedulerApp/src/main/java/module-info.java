module main.schedulerapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens main.scheduler to javafx.fxml;
    exports main.scheduler;
    exports Controller;
    opens Controller to javafx.fxml;
}