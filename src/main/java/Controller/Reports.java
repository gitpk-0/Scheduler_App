package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Reports {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}