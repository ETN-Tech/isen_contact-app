package isen.contactapp.view;

import java.io.IOException;

import isen.contactapp.App;
import javafx.fxml.FXML;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
