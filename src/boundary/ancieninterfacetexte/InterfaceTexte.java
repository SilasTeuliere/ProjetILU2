
package boundary.ancieninterfacetexte;

import boundary.club.BoundaryClub;
import boundary.club.Registre;
import boundary.menu.Menu;
import controler.ControlerClub;
import controler.IControlerClub;

/**
 * @title 3eme mi-temps
 * @author TEULIERE Silas 22003086
 * @version 1.0.0 30 Decemebre 2022
 */
public class InterfaceTexte {

/*
 * ancien interface de saisie en mode texte - gard� pour test */
// 	public static void main(String[] args) {
//		
//		// Pour l'instant, j'instantie le club avec les informations du constructeur - la persistance de ces informations sera faite ult�rieurement
//		final IControlerClub club = new ControlerClub();
//		String saisie;
//		do {
//			saisie = Menu.choisirAction();
//			if (!saisie.equals("")) {
//				testerAction(club, saisie);
//			}
//		} while (!saisie.equals(""));
//	
//		System.out.println("Fin de saisie - Au revoir");
//		
//		Menu.fermeScanner();
//	}

	/**
	 * Teste et ex�cute les diff�rentes action possible - ajouter des actions au besoin comme ajouter membre, changer bureau, supprimer membre, �venement ou inscrit
	 * @param club
	 * @param saisie
	 */
	private static void testerAction(final IControlerClub club, String saisie) {
		switch (saisie.toUpperCase().charAt(0)) {
		case 'T':
			// appel de toutes les m�thodes en test sans persistance
			Registre.test(club);
			break;
		case 'B':
			// saisie du bureau et de membres - ne faire qu'une fois si pas pass� par test
			Menu.saisirMembres(club, 'I');
			break;
		case 'M':
			// saisie des membres
			Menu.saisirMembres(club, 'A');
			break;
		case 'E':
			// Saisir �v�nements
			Menu.saisirEvenements(club);
			break;
		case '1':
			// Envoyer mail de commande par le secr�taire pour un �v�nement
			Menu.ecrireMailMembreParSecretaire(club);
			break;
		case 'I':
			// Saisir inscriptions
			Menu.saisirInscriptions(club);
			break;
		case '2':
			// Envoyer mail de commande par le secr�taire pour un �v�nement
			Menu.ecrireMailInscritParSecretaire(club);
			break;
		case '3':
			// reste � faire par le tr�sorier pour le premier �v�nement
			Menu.listerAchatRestantLocationParTresorier(club);
			break;
		case 'L':
			// liste diverses - le deuxi�me caract�re donne la liste � obtenir 
			testerListe(club, saisie);
			break;
		case 'S':
			// suprimer un membre 
			Menu.supprimerMembre(club);
			break;
		case 'C':
			// changer le statut d'un membre 
			Menu.changerStatut(club);
			break;
		}
		
	}

	/**
	 * s�lection de la liste � afficher
	 * @param club
	 * @param saisie
	 */
	private static void testerListe(final IControlerClub club, String saisie) {
		switch (saisie.toUpperCase().charAt(1)) {
		case '1':
			BoundaryClub.afficherMembres(club);
			break;
		case '2':
			BoundaryClub.afficherEvenements(club);
			break;
		}
	}


}
