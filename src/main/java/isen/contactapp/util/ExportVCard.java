package isen.contactapp.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import isen.contactapp.model.*;

public class ExportVCard {

    private Path root; // répertoire de sortie
    private String extension; // extension du fichier


    public ExportVCard(String path) throws IOException {
        Path newPath = Paths.get(path);// stockage provisoire du chemin

        this.root = newPath;

        // test if the file exist
        if (Files.notExists(newPath)) {
            Files.createDirectory(newPath);
        }

        this.extension = ".vcard";// extension .vcard
    }

    private Path getFilePath(String fileName) { // donne le chemin du fichier qui va être créé
        Path file = this.root.resolve(fileName + this.extension);
        return file;
    }

    private boolean fileAlreadyExist(String fileName) {// vérification pour voir si un autre fichier du même nom n'est
        // pas dans le répertoire
        return Files.exists(this.getFilePath(fileName));
    }

    private void createFile(String fileName) throws IOException {// création du fichier s'il n'existe pas
        if (this.fileAlreadyExist(fileName) == false) {
            Files.createFile(this.getFilePath(fileName));
        }
    }

    public void exportToVcard(Person person) throws IOException {// exportation vers un fichier vcard
        String fileName = person.getId() + person.getLastname() + person.getFirstname();
        this.createFile(fileName);// création du fichier s'il n'existe pas
        Files.write(this.getFilePath(fileName), new VCard(person).toPack(), StandardCharsets.UTF_8);
    }
}
