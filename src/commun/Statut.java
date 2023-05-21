
package commun;

/**
 * enumeration de tous les statuts possibles dans le club 
 */
public enum Statut {
	PRESIDENT("Pr�sident"), TRESORIER("Tr�sorier"), SECRETAIRE("Secr�taire"), MEMBRE("Membre");
	
	private String nom;
	
	private Statut(String nom) {
		this.nom = nom;
	}

	public String toString() {
		return nom;
	}
}
