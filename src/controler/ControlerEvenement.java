/**
 * 
 */
package controler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import entity.evenement.Evenement;
import entity.evenement.FournitureEven;
import entity.evenement.FournitureInscrit;
import entity.evenement.InscritEven;
import entity.evenement.Produit;
import entity.evenement.Salle;

/**
 * @author silas
 *
 */
public class ControlerEvenement {
	
	/**
	 * etablissement de la liste des fournitures necessaires par rapport au nombres d'inscrit
	 * prevoit pour chaque membre ce qu'il doit fournir 
	 * @param evenement
	 * @return
	 */
	public static boolean etablissementTabFourniture(Evenement evenement) {
		InscritEven[] inscrits = InscritEven.listerInscrits(evenement);
		if (inscrits == null)  {
			return false;
		}
		int nbInscrit = inscrits.length;
		// détermination de la salle et de son prix
		if(!choixSalle(evenement, nbInscrit)) {
			return false;
		}
		evenement.setBudgetEven(evenement.getLieu().getPrix());
		constituerListeFournituresEvenement(evenement, nbInscrit);
		
		// détermination du budget à prévoir par chaque inscrits
		double budgetParPersonne = evenement.getBudgetEven() / nbInscrit;
		constituerListeFournituresParInscrit(evenement, budgetParPersonne);
		
		return true;
	}

	/**
	 * effectue automatiquement le choix de la salle en fonction du nombre d'inscrit
	 * @param evenement
	 * @param nbInscrit
	 * @return true si salle trouvé false si l'evenement trop ou trop peu de participant (à ameliorer)
	 */
	public static boolean choixSalle(Evenement evenement, int nbInscrit) {
		boolean bool = true;
		if (Salle.PETITE_SALLE.getNbPersonneMin() <= nbInscrit && nbInscrit <= Salle.PETITE_SALLE.getNbPersonneMax()) {
			evenement.setLieu(Salle.PETITE_SALLE);
		}
		else if (Salle.MOYENNE_SALLE.getNbPersonneMin() <= nbInscrit && nbInscrit <= Salle.MOYENNE_SALLE.getNbPersonneMax()) {
			evenement.setLieu(Salle.MOYENNE_SALLE);
		}
		else if (Salle.GRANDE_SALLE.getNbPersonneMin() <= nbInscrit && nbInscrit <= Salle.GRANDE_SALLE.getNbPersonneMax()) {
			evenement.setLieu(Salle.GRANDE_SALLE);
		}
		else {
			bool = false;
		}
		return bool;
	}
	
	/**
	 * constitue la liste des fournitures pour chaque inscrit
	 * @param budgetParPersonne
	 */
	private static void constituerListeFournituresParInscrit(Evenement evenement, double budgetParPersonne) {
		double budgetIndividuRestant;
		InscritEven[] inscrits = evenement.getInscrits();
		// Traité dans l'ordre des différents produits triés par ordre décroissants des prix
		for (InscritEven inscrit: inscrits) {
			budgetIndividuRestant = Math.round(budgetParPersonne * 100.00) / 100.00;
			final List<FournitureInscrit> tableauFourniture = new ArrayList<FournitureInscrit>();
			budgetIndividuRestant = determinerFournitureAAcheter(evenement, budgetIndividuRestant, tableauFourniture);
			
			//Si tout le budget d'une personne n'est pas utilisé le reste sert à payer la salle
			if (budgetIndividuRestant > 0) {
				final FournitureInscrit fournitureInscrit = 
						new FournitureInscrit (evenement.getLieu(), null, 1, Math.round(budgetIndividuRestant * 100.00) / 100.00);
				tableauFourniture.add(fournitureInscrit);
			}
			FournitureInscrit[] fournituresTab = new FournitureInscrit[tableauFourniture.size()];
			inscrit.setFournitures(tableauFourniture.toArray(fournituresTab));
		}
	}

	/**
	 * determine pour un inscrit ce qu'il doit acheter
	 * @param budgetIndividuRestant
	 * @param tableauFourniture
	 * @return
	 */
	private static double determinerFournitureAAcheter(Evenement evenement, double budgetIndividuRestant,
			final List<FournitureInscrit> tableauFourniture) {
		int nbProd;
		for (FournitureEven fourniture: evenement.getFournitures()) {
			nbProd = 1;
			while (nbProd <= (fourniture.getNbrTotal() - fourniture.getNbrAchete()) &&
					fourniture.getProduit().getPrix() * nbProd <= budgetIndividuRestant) {
				// il reste au moins nbprod de ce produit à acheter 
				// et l'achat de nbprod de ce produit reste dans mon budget restant
				nbProd++;
			}
			nbProd--;
			if (nbProd > 0) {
				// J'achète nbprod de ce produit là
				final FournitureInscrit fournitureInscrit = new FournitureInscrit (null, fourniture.getProduit(), nbProd, nbProd * fourniture.getProduit().getPrix());
				tableauFourniture.add(fournitureInscrit);
				budgetIndividuRestant -= nbProd * fourniture.getProduit().getPrix();
				fourniture.setNbrAchete(fourniture.getNbrAchete() + nbProd);
			}
		}
		return budgetIndividuRestant;
	}

	/**
	 * constitue la liste des fournitures pour l'evenement en fonction du nombre d'inscrit
	 * @param evenement
	 * @param nbInscrit
	 */
	private static void constituerListeFournituresEvenement(Evenement evenement, int nbInscrit) {
		int i = 0;
		double produitParEven;
		double prixProduitEven;
		final FournitureEven[] fournituresTrav = new FournitureEven[evenement.getFournitures().length];

		for (Produit produit : Produit.values()) { 
			produitParEven = nbInscrit*produit.getParPersonne();
			if(produitParEven != Math.floor(produitParEven)) {
				produitParEven = Math.floor(produitParEven) + 1;
			}
			prixProduitEven = produitParEven * produit.getPrix();
			evenement.setBudgetEven(evenement.getBudgetEven() + prixProduitEven);
			final FournitureEven fourniture = new FournitureEven(produit, (int)produitParEven, prixProduitEven );
		    fournituresTrav[i] = fourniture;
		    i++;
		}
		evenement.setFournitures(fournituresTrav);
	}
	
	
	/**
	 * @author silas
	 * classe contenant les compositions Ocalm
	 */
	public class OcalmEve{

		/**
		 * creer programme Ocaml liste des inscrits
		 * @param controlerClub 
		 * @param dateEvenement
		 * @return
		 */
		public static String extraireInstructionsCamlInscrit(IControlerClub controlerClub, LocalDateTime dateEvenement) {
			String listFour;
			Evenement evenement = Evenement.rechercherEvenement(controlerClub.getClub(), dateEvenement);
			InscritEven[] inscrits = InscritEven.listerInscrits(evenement);
			int nbInscrits = inscrits.length;
			String texte = " let inscrit = [\n";
			for(int i = 0; i < nbInscrits; i++) {
				listFour = instructionOcamlFournitureInscrit(inscrits[i]);
				
				if(i == nbInscrits-1) {
					texte = texte  + inscrits[i].getMembre().getId() +", \"" + inscrits[i].getMembre().getNomPrenom() +"\", \"" + inscrits[i].getBudjetReel() 
							+ "\", \"" + inscrits[i].getBudjetReel() +"\", " + listFour + "\n";
				}
				else {
					texte = texte  + inscrits[i].getMembre().getId() +", \"" + inscrits[i].getMembre().getNomPrenom() +"\", \"" + inscrits[i].getBudjetReel() 
							+ "\", \"" + inscrits[i].getBudjetReel() +"\", " + listFour + ";\n";
				}
			}
			texte = texte + "]\n";
			return texte;
		}

		/**
		 * creer liste des fournitures en OCAML
		 * @param inscrit
		 * @return
		 */
		private static String instructionOcamlFournitureInscrit(InscritEven inscrit) {
			String listFour = "[";
			int nbFournitures = inscrit.getFournitures().length;
			for(int j = 0; j < nbFournitures; j++) {
				listFour = listFour + "\"" + inscrit.getFournitures()[j].getSalle() + "\", \"" + inscrit.getFournitures()[j].getProduit() + "\", " +
						inscrit.getFournitures()[j].getNbr() +", " + inscrit.getFournitures()[j].getPrix();
				if(j == nbFournitures-1) {
					listFour = listFour + "]";
				}
				else {
					listFour = listFour + ";";
				}
			}
			return listFour;
		}


		/**
		 * creer liste des fournitures en OCAML
		 * @param controlerClub 
		 * @param dateEvenement
		 * @return
		 */
		public static String instructionOcamlFourniture(IControlerClub controlerClub, LocalDateTime dateEvenement) {
			Evenement evenement = Evenement.rechercherEvenement(controlerClub.getClub(), dateEvenement);
			FournitureEven[] fournitures = evenement.getFournitures();
			String listFour = "let fournitures = [\n";
			int nbFournitures = fournitures.length;
			for(int j = 0; j < nbFournitures; j++) {
				listFour = listFour + "\"" + fournitures[j].getProduit().getNom() + "\", " + fournitures[j].getNbrTotal() +", " + fournitures[j].getPrixTotal() +", "
						+ fournitures[j].getNbrAchete();
				if(j == nbFournitures-1) {
					listFour = listFour + "\n]\n";
				}
				else {
					listFour = listFour + ";\n";
				}
			}
			return listFour;
		}
	}
	

}
