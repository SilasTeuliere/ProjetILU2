
package controler.membre;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import commun.Statut;
import controler.ControlerEvenement;
import controler.IControlerClub;
import entity.evenement.Evenement;
import entity.evenement.FournitureInscrit;
import entity.evenement.InscritEven;
import entity.membre.Membre;

/**
 * Classe  des actions possibles pour le secretaire du club
 */
public class ControlerSecretaire {

	/**
	 * indique si l'identifiant donnée en entrée correspond à un membre Secretaire
	 * @param controlerClub
	 * @return
	 */
	public static boolean estSecretaire(IControlerClub controlerClub,int iD) {
		Membre membre = Membre.trouverMembre(controlerClub.getClub(), iD);
		if (membre != null && Statut.SECRETAIRE.equals(membre.getStatut())) {
		   return true;
		}
		return false;
		
	}

		
	/**
	 * ajoute un inscrit à un événement donné par sa date
	 * @param controlerClub club
	 * @param dateEvenement date de l'événement
	 * @param numeroIdMembre identifiant du membre a inscrire
	 * @param montantPrevu montant que prévoit de verser le membre pour son inscription
	 * @param iD
	 */
	public static void ajouterInscrit(IControlerClub controlerClub, LocalDateTime dateEvenement, int numeroIdMembre, double montantPrevu, int iD) {
		if (estSecretaire(controlerClub, iD)) {
		    Membre membre = Membre.trouverMembre(controlerClub.getClub(), numeroIdMembre);
		    Evenement evenement = Evenement.rechercherEvenement(controlerClub.getClub(), dateEvenement);
		    InscritEven.ajoutInscrit(evenement, membre, montantPrevu);
		}
	}

	/**
	 * ecrit les mails à envoyer à chaque membre pour savoir s'il souhaite participer à l'evenement organisé
	 * @param controlerClub 
	 * @param evenement
	 * @param iD identifiant secrétaire
	 * @return liste des mails à transmettre
	 */
	public static List<String> ecrireMailMembre(IControlerClub controlerClub, LocalDateTime dateEvenement, int iD){
		final List<String> mailsMembre = new ArrayList<>();
		
		if (estSecretaire(controlerClub, iD)) {
			Membre[] membres = Membre.listerMembres(controlerClub.getClub());
			Evenement evenement = Evenement.rechercherEvenement(controlerClub.getClub(), dateEvenement);
			String nomPrenomSecretaire = Membre.trouverMembre(controlerClub.getClub(), iD).getNomPrenom();
			int nbMembre = membres.length;
			for(int i = 0; i < nbMembre ; i++) {
				final String mailMembre = "Bonjour " + membres[i].getNomPrenom() + ", \n "
						+ "nous vous invitons à vous inscrire si vous le souhaitez à l'événement du " + evenement.getDate() + ". \n"
								+ "Cet événement sera " + evenement.getDescription() + ". \n"
										+ "Si vous souhaitez vous inscrire renvoyez un mail d'ici maximum une semaine pour que nous puissions reserver la salle. \n"
										+ "Et nous indiquer le budget que vous souhaitez y mettre. \n"
										+ "Cordialement \n"
										+ nomPrenomSecretaire;
				mailsMembre.add(mailMembre);
			}
		}
		
		return mailsMembre;
	}
	
	/**
	 * ecrit les mails à envoyer à chaque membre pour qu'il sache ce qu'ils doivent acheter pour l'evenement
	 * @param controlerClub 
	 * @param evenement
	 * @param iD identifiant secrétaire
	 * @return liste des mails à transmettre
	 */
	public static List<String> ecrireMailInscrit(IControlerClub controlerClub, LocalDateTime dateEvenement, int iD) {
		final List<String> mailsInscrit = new ArrayList<>();
		
		if (estSecretaire(controlerClub, iD)) {
			
			Evenement evenement = Evenement.rechercherEvenement(controlerClub.getClub(), dateEvenement);
			
			if (ControlerEvenement.etablissementTabFourniture(evenement))  {

				InscritEven[] inscrits = InscritEven.listerInscrits(evenement);
				String nomPrenomSecretaire = Membre.trouverMembre(controlerClub.getClub(), iD).getNomPrenom();
			
				int nbInscrit = inscrits.length;
				for(int i = 0; i < nbInscrit ; i++) {
					String mailInscrit = "Bonjour " + inscrits[i].getMembre().getNomPrenom() + ", \n"
							+ "Notre troisième mi-temps pour " + evenement.getDescription() 
							+ " aura lieu à la salle " + evenement.getLieu().getNom() + " " + evenement.getLieu().getAdresse() +"\n"
							+ "Merci d'amener les fournitures suivantes :  \n";
						for(FournitureInscrit fournitureInscrit: inscrits[i].getFournitures()) {
							if (fournitureInscrit.getSalle() == null) {
								mailInscrit += " - " + fournitureInscrit.getNbr() + " de " + fournitureInscrit.getProduit().getNom() + 
										" pour un prix de " + fournitureInscrit.getPrix() + " euros \n";
							} else {
								mailInscrit += " - " + "participation aux frais de location de la salle pour " + fournitureInscrit.getPrix() + " euros \n";
							}
						}
					mailInscrit += "Cordialement \n" + nomPrenomSecretaire;
					mailsInscrit.add(mailInscrit);
				}
			}

		}
		return mailsInscrit;
	}

}
