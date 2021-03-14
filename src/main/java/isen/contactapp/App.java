package isen.contactapp;

import isen.contactapp.daos.PersonDao;
import isen.contactapp.model.Person;
import isen.contactapp.service.PersonService;
import isen.contactapp.util.ExportVCard;
import isen.contactapp.util.ImportVCard;
import isen.contactapp.view.PersonController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static BorderPane mainlayout;

    private PersonDao personDao = new PersonDao();

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Contact");
        mainlayout = loadFXML("MainLayout");

        scene = new Scene(mainlayout, 640, 480);
        stage.setScene(scene);
        stage.show();

        // create
        final FileChooser fileChooser = new FileChooser();
        final DirectoryChooser directoryChooser = new DirectoryChooser();
        final MenuItem importButton = new MenuItem("Import");
        final MenuItem backupButton = new MenuItem("Backup");

        importButton.setOnAction(actionEvent -> {
            List<File> list = fileChooser.showOpenMultipleDialog(stage);
            if (list != null) {
                for (File file : list) {
                    System.out.println(file.getPath());
                    try {
                        // import vcard file
                        ImportVCard importer = new ImportVCard(file.getPath());
                        Person newPerson = importer.importFromVcard();
                        PersonService.addPerson(newPerson);
                        personDao.addPerson(newPerson);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        backupButton.setOnAction(actionEvent -> {
            File file = directoryChooser.showDialog(stage);
            if (file != null) {
                try {
                    System.out.println(file.getPath());
                    // export vcard file
                    ExportVCard exporter = new ExportVCard(file.getPath());
                    for (Person person: personDao.listPersons()) {
                        exporter.exportToVcard(person);
                    }
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        });

        MenuBar menuBar = (MenuBar) mainlayout.getTop();
        Menu menu = menuBar.getMenus().get(0);

        // add MenuItems to Menu
        menu.getItems().add(importButton);
        menu.getItems().add(backupButton);

        showView("DetailsPerson");
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static <T> T loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/isen/contactapp/view/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    private static <T> T loadFXML(String fxml, Object... params) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/isen/contactapp/view/" + fxml + ".fxml"));
        T element = fxmlLoader.load();

        PersonController controller = fxmlLoader.getController();
        Person person = (Person) params[0];
        controller.initController(person);

        return element;
    }

    public static void main(String[] args) {
        launch();
    }

    /**
     * @param rootElement updates the center of our layout with the @rootElement
     *                    passed in parameter
     */
    public static void showView(String rootElement) {
        try {
            mainlayout.setCenter(loadFXML(rootElement));
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public static void showView(String rootElement, Object... params) {
        try {
            mainlayout.setCenter(loadFXML(rootElement, params));
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}