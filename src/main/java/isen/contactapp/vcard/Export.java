package isen.contactapp.vcard;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import isen.contactapp.model.*;;

/**
 *  * @authors Takoukam Alex, EtienneSchelfout, Nono Junior
 */
public class Export {

	private Path root; // répertoire de sortie
	private String extension; // extension du fichier

	/**
	 * 
	 */

	public Export(String path) throws IOException {
		Path newPath = Paths.get(path);// stockage provisoire du chemin

		if (Files.isDirectory(newPath)) {// on regarde s'il s'agit d'un chemin de répertoire ou d'un chemin de fichier
			this.root = newPath;
		}

		else {
			this.root = newPath.getParent();// si c'est un chemin de fichier on prend le parent pour chemin
		}

		if (Files.notExists(this.root)) {// si le répertoire n'existe pas alors on doit le créer
			Files.createDirectory(this.root);
		}
		this.extension = ".vcard";// extension .vcard
	}

	private Path returnFilePath(String fileName) { // donne le chemin du fichier qui va être créé
		Path file = this.root.resolve(fileName + this.extension);
		return file;
	}

	private boolean fileAlreadyExist(String fileName) {// vérification pour voir si un autre fichier du même nom n'est
														// pas dans le répertoire
		return Files.exists(this.returnFilePath(fileName));
	}

	private void createFile(String fileName) throws IOException {// création du fichier s'il n'existe pas
		if (this.fileAlreadyExist(fileName) == false) {
			Files.createFile(this.returnFilePath(fileName));
		}
	}

	public void exportToVcard(Person person) throws IOException {// exportation vers un fichier vcard
		String fileName = String.valueOf(person.getId()) + person.getLastname() + person.getFirstname();// création
																												// du
																												// nom
																												// de
																												// fichier
																												// id+lastName+firstname
		this.createFile(fileName);// création du fichier s'il n'existe pas
		Files.write(this.returnFilePath(fileName), new VCard(person).toPack(), StandardCharsets.UTF_8);// transformation
																										// en vcard puis
																										// en liste de
																										// string puis
																										// écriture dans
																										// le fichier
	}
}
