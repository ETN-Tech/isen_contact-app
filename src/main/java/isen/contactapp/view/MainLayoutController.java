package isen.contactapp.view;

import isen.contactapp.App;
import javafx.application.Platform;

public class MainLayoutController {

    public void closeApplication() {
        Platform.exit();
    }

    public void gotoHome() {
        App.showView("PrimaryController");
    }
}
