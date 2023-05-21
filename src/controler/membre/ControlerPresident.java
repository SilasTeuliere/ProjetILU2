
package controler.membre;

import java.time.LocalDateTime;

import commun.Statut;
import controler.IControlerClub;
import entity.evenement.Evenement;
import entity.membre.Membre;

/**
 * 
 * @author silas
 *
 */
public class ControlerPresident  {
	
	
	/**
	 * indique si l'identifiant donnée en entrée correspond à un membre President
	 * @param controlerClub
	 * @return
	 */
	public static boolean estPresident(IControlerClub controlerClub,int iD) {
		Membre membre = Membre.trouverMembre(controlerClub.getClub(), iD);
		if (membre != null && Statut.PRESIDENT.equals(membre.getStatut())) {
		   return true;
		}
		
		return false;
		
	}
	
	/**
	 * crée un événement à la date donnée avec les informations transmises
	 * @param controlerClub
	 * @param dateEven date et heure de l'événement
	 * @param detail information sur l'événement
	 * @param iD identifiant du président
	 */
	public static void creerEvenement(IControlerClub controlerClub, LocalDateTime dateEven, String detail, int iD) {
		if (estPresident(controlerClub, iD)) {
		   Evenement.ajouterEvenement(controlerClub.getClub(), dateEven, detail);
		}
	}
	
	/**
	 * supprimer un événement à la date transmise
	 * @param controlerClub
	 * @param dateEven date et heure de l'événement
	 * @param iD identifiant du président
	 */
	public static void supprimerEvenement(IControlerClub controlerClub, LocalDateTime dateEven, int iD) {
		if (estPresident(controlerClub, iD)) {
			Evenement.supprimerEvenement(controlerClub.getClub(), dateEven);
		}
	}
	
}
