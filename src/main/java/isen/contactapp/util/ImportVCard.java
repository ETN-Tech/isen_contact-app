package isen.contactapp.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import isen.contactapp.model.Person;
import isen.contactapp.model.VCard;

public class ImportVCard {

    private Path path;// chemin du fichier
    private String extension;// extension lue


    public ImportVCard(String path) throws IOException {
        this.path = Paths.get(path);// enregistrement du chemin et de l'extension
        this.extension = "vcard";
    }

    private boolean checkExtension(String fileName) { // vérification de l'extension: regarder si l'extension du fichier
        // du chemin et identique à celle du type de fichier importé
        String[] stringDecomposition = fileName.split("\\.");
        String extension = stringDecomposition[1];

        return extension.equals(this.extension);
    }

    public boolean checkFile() {// méthode publique qui doit être appelé avant une importation
        if (Files.exists(this.path)) {// existence du chemin
            if (Files.isRegularFile(this.path)) { // verification s'il s'agit bien du chemin d'un fichier et non d'un
                // dossier
                return this.checkExtension(this.path.getFileName().toString());// verification de l'extension
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public Person importFromVcard() throws IOException {// importation

        List<String> lines = Files.readAllLines(this.path, StandardCharsets.UTF_8);// récupération des lignes du fichier
        VCard newVcard = new VCard(lines);// découpage des lignes en vcard
        return newVcard.toPerson();// transformation du vcard en Person

    }

}

