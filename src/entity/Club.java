
package entity;

import java.util.List;

import entity.dao.AccesDonnees;
import entity.evenement.Evenement;
import entity.membre.Membre;

/**
 * Classe decrivant le club : son adresse et ses membres...
 */
public class Club {
	public static String FICHIER_MEMBRE = "membre";
	private String nomClub = "La 3eme Mi-Temps Toulousaine";
	private String emailClub = "3emeMiTempsTls@gmail.com";
	private String numTelClub = "06.10.14.01.01";
	private String adresse = "1 All. Gabriel Bicnus, 31000 Toulouse";
	private Membre membres[] = null;
	private Evenement[] evenements = null;

	private int NumeroIDNouveau = 0;
	
	public Club() {
		// vérifier s'il existe un fichier de membres et si oui le charger
		List<String> listeMembres = AccesDonnees.LireDonnees(FICHIER_MEMBRE);
		if (listeMembres != null) {
			Membre.transcoDeCSV(this, listeMembres);
		}
		
		// reste à faire : charger les événements - charger les inscrits - un fichier par événement avec la date dans le nom de fichier
	}
	
	public Membre[] getMembres() {
		return membres;
	}
	
	public void setMembres(Membre[] pMembres) {
		membres = pMembres;
	}

	public String getNomClub() {
		return nomClub;
	}

	public String getEmailClub() {
		return emailClub;
	}

	public String getNumTelClub() {
		return numTelClub;
	}

	public String getAdresse() {
		return adresse;
	}

	public String Publicite() {
		return nomClub + emailClub + numTelClub + adresse;
	}
	
	public Evenement[] getEvenements() {
		return evenements;
	}

	public void setEvenements(Evenement[] evenements) {
		this.evenements = evenements;
	}

	public int getNumeroIDNouveau() {
		return NumeroIDNouveau;
	}

	public void setNumeroIDNouveau(int numeroIDNouveau) {
		NumeroIDNouveau = numeroIDNouveau;
	}

}