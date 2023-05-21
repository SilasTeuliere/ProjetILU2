package entity.dao;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Classe qui permet l'ecriture et la lecture des données qu'on veut faire persister 
 */
public class AccesDonnees {
	/**
	 * ecriture dans le fichier, dont le nom est fourni, les données passées en entrées
	 * @param donnees liste de String contenant les informations à persister
	 * @param nomFichier nom du fichier à écrire
	 * @throws IOException 
	 */
	public static void ecrireDonnees(List<String> donnees, String nomFichier) {
	    // Creation du fichier dont le nom est transmis en entree - si préexiste il sera écrasé
	    Path fichier = Paths.get("C:\\data\\" + nomFichier + ".csv");
	    //La commande suivante écrit les lignes en écrasant le texte déjà présent dans le fichier
	    try {
	    	Files.write(fichier, donnees, Charset.forName("ISO-8859-1"));
	    } catch (IOException e) {
	    	e.printStackTrace();
	    	// création du répertoire à tout hazard...
	    	Path dossier = Paths.get("C:\\data");
	    	try {
	          Files.createDirectory(dossier);
	    	}  catch (IOException e1) {
	  	    	e1.printStackTrace();
	    	}
	    }
	}

    /**
     * lecture d'un fichier et restitution sous forme de liste de String de son contenu
     * @param nomFichier
     * @return liste de String du contenu du fichier
     */
	public static List<String> LireDonnees(String nomFichier) {
	    // détermination du fichier à lire
	    Path fichier = Paths.get("C:\\data\\" + nomFichier + ".csv");
	    List<String> donnees = null;
	    //La commande suivante le fichier dans son exhaustivité sous forme d'une liste de String
	    try {
	        donnees = Files.readAllLines(fichier, Charset.forName("ISO-8859-1"));
	    }
	    catch (IOException e) {
	       System.out.println("pas de fichier " + nomFichier + " trouvé");
	    }
	    return donnees;
	}
}

