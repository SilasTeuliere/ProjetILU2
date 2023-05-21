
package entity.membre;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import commun.Statut;
import entity.Club;
import entity.dao.AccesDonnees;

/**
 * Description de chaque Membre du club (adresse, Nom prenom, email...)
 */
public class Membre extends Adherent {
	private int id;
	private String nomPrenom;
	private String email;
	private String adresse;
	private String numTel;
	private Statut statut;				//MEMBRE, SECRETAIRE, PRESIDENT, TRESORIER
	private int anneeInscr;
	private int derAnneeParticipation;

	public Membre(int id, String nomPrenom, String email, String adresse, String numTel, Statut statut, int anneeInscr, int derAnneeParticipation) {
		this.id = id;
		this.nomPrenom = nomPrenom;
		this.email = email;
		this.adresse = adresse;
		this.numTel = numTel;
		this.statut = statut;
		this.anneeInscr = anneeInscr;
		this.derAnneeParticipation = derAnneeParticipation;
	}
	
	public int getId() {
		return id;
	}

	public String getNomPrenom() {
		return nomPrenom;
	}

	public Statut getStatut() {
		return statut;
	}

	public String getEmail() {
		return email;
	}

	public String getAdresse() {
		return adresse;
	}

	public String getNumTel() {
		return numTel;
	}

	public int getAnneeInscr() {
		return anneeInscr;
	}

	public int getDerAnneeParticipation() {
		return derAnneeParticipation;
	}
	
	/**
	 * Changer le statut d'un membre
	 * Attention ne sauve pas le changement => à mettre dans Club...
	 * @param statut
	 */
	public Membre changerStatut(Statut statut) {
		switch (statut) {
		case PRESIDENT:
			return new President(id, nomPrenom, email, adresse, numTel, anneeInscr, derAnneeParticipation);
		case SECRETAIRE:
			return new Secretaire(id, nomPrenom, email, adresse, numTel, anneeInscr, derAnneeParticipation);
		case TRESORIER:
			return new Tresorier(id, nomPrenom, email, adresse, numTel, anneeInscr, derAnneeParticipation);
		default:
			return new Membre(id, nomPrenom, email, adresse, numTel, statut, anneeInscr, derAnneeParticipation);
		}
	}
	

	// polymorphisme : un même nom de fonction avec des paramètres différents
	/**
	 * inscription d'un nouveau membre à la date du jour
	 * @param club
	 * @param nomPrenom
	 * @param email
	 * @param adresse
	 * @param numTel
	 * @param statut
	 */
	public static Membre ajoutMembre(Club club, String nomPrenom, String email, String adresse, String numTel, Statut statut) {
		club.setNumeroIDNouveau(club.getNumeroIDNouveau() + 1);
		int NumeroIDNouveau = club.getNumeroIDNouveau();
		int annee = LocalDate.now().getYear();
		Membre membre;
		
		switch (statut) {
		case PRESIDENT:
			membre = new President(NumeroIDNouveau, nomPrenom, email, adresse, numTel, annee, annee);
			break;
		case SECRETAIRE:
			membre = new Secretaire(NumeroIDNouveau, nomPrenom, email, adresse, numTel, annee, annee);
			break;
		case TRESORIER:
			membre = new Tresorier(NumeroIDNouveau, nomPrenom, email, adresse, numTel, annee, annee);
			break;
		default:
			membre = new Membre(NumeroIDNouveau, nomPrenom, email, adresse, numTel, statut, annee, annee);
		}
		List<Membre> listeMembres = new ArrayList<>();
		if (club.getMembres() != null) {
			listeMembres = new ArrayList<>(Arrays.asList(club.getMembres()));
		}
		listeMembres.add(membre);
		Membre[] membresNouveaux = new Membre[listeMembres.size()];
		club.setMembres(listeMembres.toArray(membresNouveaux));
		AccesDonnees.ecrireDonnees(Membre.transcoEnCSV(club), Club.FICHIER_MEMBRE);
		
		return membre;
	}

	/**
	 * inscription d'un nouveau membre à la date du jour - sans transmettre le statut => membre simple
	 * @param club
	 * @param nomPrenom
	 * @param email
	 * @param adresse
	 * @param numTel
	 */
	public static void ajoutMembre(Club club, String nomPrenom, String email, String adresse, String numTel) {
		List<Membre> listeMembres = new ArrayList<>();
		Membre[] membres = club.getMembres();
		if (membres != null) {
			listeMembres = new ArrayList<>(Arrays.asList(membres));
		}
		club.setNumeroIDNouveau(club.getNumeroIDNouveau() + 1);
		int annee = LocalDate.now().getYear();
		listeMembres.add(new Membre(club.getNumeroIDNouveau(), nomPrenom, email, adresse, numTel, Statut.MEMBRE, annee, annee));
		Membre[] membresNouveaux = new Membre[listeMembres.size()];
		club.setMembres(listeMembres.toArray(membresNouveaux));		
		AccesDonnees.ecrireDonnees(Membre.transcoEnCSV(club), Club.FICHIER_MEMBRE);
	}

	/**
	 * Transcodage de la liste des membres en CSV
	 * @param club
	 * @return liste des membres transcodé en CSV
	 */
	private static List<String> transcoEnCSV(Club club) {
		List<String> sortie = new ArrayList<>();
		for (Membre membre: club.getMembres()) {
			String nouveauMembre = Integer.toString(membre.getId())+";"
		                            + membre.getNomPrenom() + ";"
		                            + membre.getEmail() + ";"
		                            + membre.getAdresse() + ";"
		                            + membre.getNumTel() + ";"
		                            + membre.getStatut().toString() + ";"
		                            + membre.getAnneeInscr() + ";"
		                            + membre.getDerAnneeParticipation();
			sortie.add(nouveauMembre);
		}
		return sortie;
	}


	/**
	 * Transcodage d'une String CSV en Membre
	 * @param club
	 * @param membre formaté avec séparateur de donnée ";" (format CSV) 
	 */
	public static void transcoDeCSV(Club club, List<String> membresCSV) {
		List<Membre> listeMembres = new ArrayList<>();
		int maxId = 0;
		int id;
		String nomPrenom;
        String email;
        String adresse;
        String numTel; 
        Statut statut = null;
        int anneeInscription;
        int anneeDer;
		String[] donneeLue = null;
		for (String membreCSV: membresCSV) {
			donneeLue = membreCSV.split(";");
			id = Integer.parseInt(donneeLue[0]);
			if (id > maxId) {
				maxId = id;
			}
			nomPrenom = donneeLue[1];
	        email = donneeLue[2];
	        adresse = donneeLue[3];
	        numTel = donneeLue[4]; 
	        for (Statut statutTheo : Statut.values()) {
	        	if (statutTheo.toString().equals(donneeLue[5])) {
	        		statut = statutTheo;
	        	}
	        }
	        anneeInscription = Integer.parseInt(donneeLue[6]);
	        anneeDer = Integer.parseInt(donneeLue[7]);
	        Membre membre;
	        switch (statut) {
			case PRESIDENT:
				membre = new President(id, nomPrenom, email, adresse, numTel, anneeInscription, anneeDer);
				break;
			case SECRETAIRE:
				membre = new Secretaire(id, nomPrenom, email, adresse, numTel, anneeInscription, anneeDer);
				break;
			case TRESORIER:
				membre = new Tresorier(id, nomPrenom, email, adresse, numTel, anneeInscription, anneeDer);
				break;
			default:
				membre = new Membre(id, nomPrenom, email, adresse, numTel, statut, anneeInscription, anneeDer);
			}
			listeMembres.add(membre);
		}
		Membre[] membresNouveaux = new Membre[listeMembres.size()];
		club.setMembres(listeMembres.toArray(membresNouveaux));
		club.setNumeroIDNouveau(maxId);
	}

	/**
	 * suppression d'un membre
	 * @param club
	 * @param id - identifiant du membre
	 */
	public static void suppMembre(Club club, int id){
		//passage par une liste pour supprimer physiquement le membre concerné
		List<Membre> listeMembres = new ArrayList<>(Arrays.asList(club.getMembres()));
		listeMembres.removeIf(membreLu -> membreLu.getId() == id);
		Membre[] membresNouveaux = new Membre[listeMembres.size()];
		club.setMembres(listeMembres.toArray(membresNouveaux));
		AccesDonnees.ecrireDonnees(Membre.transcoEnCSV(club), Club.FICHIER_MEMBRE);
	}
	
	/**
	 * trouve le membre dont l'id est mis en entrée 
	 * @param club
	 * @param id
	 * @return
	 */
	public static Membre trouverMembre(Club club, int id) {
		int i = 0;
		Membre[] membres = club.getMembres();
		while (i < membres.length && membres[i].getId() != id) {
			i++;
		}
		if (i >= membres.length) {
			return null; 
		}
		return membres[i];
	}
	
	
	/**
	 * Recherche de la première personne ayant un statut donné
	 * @param club
	 * @param statut
	 * @return
	 */
	public static Membre rechercherStatut(Club club, Statut statut) {
		for (Membre membre: club.getMembres()) {
			if (membre.getStatut().equals(statut)) {
				return membre;
			}
		}
		return null;
	}

	/**
	 * changer le statut d'un membre à partir de son iD
	 * @param club
	 * @param idMembre
	 * @param statut
	 */
	public static int changerStatut(Club club, int idMembre, Statut statut) {
		
		if (!statut.equals(Statut.MEMBRE) && !statut.equals(Statut.PRESIDENT) && !statut.equals(Statut.SECRETAIRE) && !statut.equals(Statut.TRESORIER)) {
			return -9;
		}
        Membre[] membres = club.getMembres();
		int i = 0;
		while (i < membres.length && membres[i].getId() != idMembre) {
			i++;
		}
		if (i >= membres.length) {
			// "Membre inconnu";
			return -1;
		}
		
		if (membres[i].getStatut().equals(statut)) {
			// "Statut pré-existant";
			return -2;
		}
		
		int retour = membres[i].verifierStatusExistant();
        if (retour < 0) {
        	return retour;
        };
		
		membres[i] = membres[i].changerStatut(statut);
		
		// si existe un autre membre avec le même statut identique (hors membre) le changer en membre
		for (int j = 0; j < membres.length; j++) {
			if (j != i) {
				Membre membre = membres[j].testerChangeStatus(statut);
				if (membre != null) {
					membres[j] = membre;
				}
			}
		}
		AccesDonnees.ecrireDonnees(transcoEnCSV(club), Club.FICHIER_MEMBRE);
		return retour;
	}


	/**
	 * réinitialisation de la liste des membres du club
	 * @param club
	 */
	public static void initMembres(Club club) {
		club.setMembres(null);
		club.setNumeroIDNouveau(0);
	}


	/**
	 * liste les membres du club
	 * @param club
	 */
	public static Membre[] listerMembres(Club club) {
		return club.getMembres();
	}
	
	/**
	 * Methode inutile faisant de la redefinition
	 */
	public String suppressionMembrePossible() {
		return "Suppression membre impossible sans remplacement.";
	}

	/**
	 * Vérifie qu'on ne change pas le status du président
	 * @return
	 */
	protected int verifierStatusExistant() {
		return 0;
	}
	
	/**
	 * Change le status en membre 
	 * @param status
	 * @return
	 */
	protected Membre testerChangeStatus(Statut status) {
		return null;
	}
}
