package isen.contactapp.view;

import java.io.IOException;

import isen.contactapp.App;
import javafx.fxml.FXML;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}