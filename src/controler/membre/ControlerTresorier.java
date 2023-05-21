
package controler.membre;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import commun.Statut;
import controler.AchatAffichage;
import controler.IControlerClub;
import entity.evenement.Evenement;
import entity.evenement.FournitureEven;
import entity.evenement.FournitureInscrit;
import entity.evenement.InscritEven;
import entity.membre.Membre;

/**
 * Classe heritant de membre concernant le tresorier du club
 */
public class ControlerTresorier{

	/**
	 * indique si l'identifiant donnée en entrée correspond à un membre Secretaire
	 * @param controlerClub
	 * @return
	 */
	public static boolean estTresorier(IControlerClub controlerClub,int iD) {
		Membre membre = Membre.trouverMembre(controlerClub.getClub(), iD);
		if (membre != null && Statut.TRESORIER.equals(membre.getStatut())) {
		   return true;
		}
		return false;
		
	}
	
	
	/**
	 * Lister les actions que doit réaliser le tresorier avant l'evenement (Les achats manquants, Louer la salle et verifier le solde de l'evenement)
	 * @param dateEvenement date de l'événement
	 * @param numeroIdMembre identifiant du membre a inscrire
	 * @param montantPrevu montant que prévoit de verser le membre pour son inscription
	 * @param iD
	 */
	public static AchatAffichage listerAchatRestantLocation(IControlerClub controlerClub, LocalDateTime dateEvenement, int iD) {
		if (!estTresorier(controlerClub, iD)) {
			return null;
		}

		// Recherche de ce qui reste à acheter
		final AchatAffichage achat = new AchatAffichage();
		Evenement evenement = Evenement.rechercherEvenement(controlerClub.getClub(), dateEvenement);
	    
		double prixManquant;
		double totalPrixManquant = 0;
		final List<String> fournitures = new ArrayList<String>();
		for (FournitureEven fourniture : evenement.getFournitures()) {
			int resteAcheter = fourniture.getNbrTotal() - fourniture.getNbrAchete();
			prixManquant = Math.round(resteAcheter * fourniture.getProduit().getPrix() * 100.00) / 100.00;
			if (resteAcheter > 0) {
				final String libelleAchat = resteAcheter + " " + fourniture.getProduit().getNom()
						+ " manquent pour " + prixManquant + " euros";
				fournitures.add(libelleAchat);
			}
			totalPrixManquant += prixManquant;
		}
		achat.setFournitures(fournitures);
		
		//prix de la salle
		achat.setPrixSalle(Double.toString(evenement.getLieu().getPrix()));
		
		// Total des liquidités pour payer la salle
		double cumulLiquide = 0;
		for (InscritEven inscrit : evenement.getInscrits()) {
			for (FournitureInscrit fourniture : inscrit.getFournitures()) {
				if (fourniture.getSalle() != null) {
					cumulLiquide += fourniture.getPrix();
					
				}
			}
		}
		
		achat.setDisponibiliteLiquide(Double.toString(Math.round(cumulLiquide * 100.00) / 100.00));
		//solde de l'evenement
		achat.setSoldeEvenement(Double.toString(Math.round((cumulLiquide - evenement.getLieu().getPrix() - totalPrixManquant) * 100.00) / 100.00));
		return achat;
	}
}
