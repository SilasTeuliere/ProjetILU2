package boundary.club;

import java.time.LocalDateTime;
import java.util.List;

import boundary.menu.Menu;
import commun.Statut;
import controler.ControlerEvenement;
import controler.IControlerClub;
import controler.membre.ControlerPresident;
import controler.membre.ControlerSecretaire;
import controler.membre.ControlerTresorier;

/**				
 * Classe principale pour les tests de chaque Methode et la generation du OCAML...							
 * 										DISLAIMER
 * Toutes les données utilisées pour la creation du Club ou des membres sont des données de test.
 * Que ce soit numéro de telephone, email, adresse ou autre...
 * */

public class Registre {
	
		public static void test(IControlerClub controlerClub) {
		controlerClub.initMembres();
    	int numPresident = controlerClub.ajoutMembre("George Gomez", "", "", "", Statut.PRESIDENT);
		int numSecretaire = controlerClub.ajoutMembre("Michel Polaref", "", "", "", Statut.SECRETAIRE);
		int numTresorier = controlerClub.ajoutMembre("Jonathan Paleton", "", "", "", Statut.TRESORIER);
		controlerClub.ajoutMembre("Nicolas Aliagas", "", "", "");
		controlerClub.ajoutMembre("Nicola Aliagas", "", "", "");
		controlerClub.ajoutMembre("Nicol Aliagas", "", "", "");
		controlerClub.ajoutMembre("Nico Aliagas", "", "", "");
		controlerClub.ajoutMembre("Nic Aliagas", "", "", "");
		controlerClub.ajoutMembre("Ni Aliagas", "", "", "");
		controlerClub.ajoutMembre("N Aliagas", "", "", "");
		controlerClub.ajoutMembre("Nicolas Aliaga", "", "", "");
		controlerClub.ajoutMembre("Nicolas Aliag", "", "", "");
		controlerClub.ajoutMembre("Nicolas Alia", "", "", "");
		controlerClub.ajoutMembre("Nicolas Ali", "", "", "");
		controlerClub.ajoutMembre("Nicolas Al", "", "", "");
		controlerClub.ajoutMembre("Nicolas A", "", "", "");
		controlerClub.ajoutMembre("Nicolas Bliagas", "", "", "");
		controlerClub.ajoutMembre("Nicolas Cliagas", "", "", "");
		controlerClub.ajoutMembre("Nicolas Dliagas", "", "", "");
		controlerClub.ajoutMembre("Nicolas Eliagas", "", "", "");
		controlerClub.ajoutMembre("Nicolas Fliagas", "", "", "");
		ControlerPresident.creerEvenement(controlerClub, LocalDateTime.parse("2023-02-20T12:15:00"), "Match des 1/8 de finale de la heineken cup", numPresident);
		ControlerPresident.creerEvenement(controlerClub, LocalDateTime.parse("2023-02-28T20:15:00"), "Match des 1/4 de finale de la heineken cup", numPresident);
		ControlerPresident.creerEvenement(controlerClub, LocalDateTime.parse("2023-03-08T20:30:00"), "Match des 1/2 de finale de la heineken cup", numPresident);
		ControlerPresident.creerEvenement(controlerClub, LocalDateTime.parse("2023-03-15T22:30:00"), "Match de la finale de la heineken cup", numPresident);
		List<String> listeMail = ControlerSecretaire.ecrireMailMembre(controlerClub, LocalDateTime.parse("2023-03-08T20:30:00"), numSecretaire);
		System.out.println("--------------");
		for (String mail : listeMail) {
			System.out.println(mail);
			System.out.println("--------------");
		}
		ControlerSecretaire.ajouterInscrit(controlerClub, LocalDateTime.parse("2023-03-08T20:30:00"), 1, 20, numSecretaire);
		ControlerSecretaire.ajouterInscrit(controlerClub, LocalDateTime.parse("2023-03-08T20:30:00"), 4, 20, numSecretaire);
		ControlerSecretaire.ajouterInscrit(controlerClub, LocalDateTime.parse("2023-03-08T20:30:00"), 5, 20, numSecretaire);
		ControlerSecretaire.ajouterInscrit(controlerClub, LocalDateTime.parse("2023-03-08T20:30:00"), 7, 20, numSecretaire);
		ControlerSecretaire.ajouterInscrit(controlerClub, LocalDateTime.parse("2023-03-08T20:30:00"), 8, 20, numSecretaire);
		ControlerSecretaire.ajouterInscrit(controlerClub, LocalDateTime.parse("2023-03-08T20:30:00"), 9, 20, numSecretaire);
		ControlerSecretaire.ajouterInscrit(controlerClub, LocalDateTime.parse("2023-03-08T20:30:00"), 10, 20, numSecretaire);
		ControlerSecretaire.ajouterInscrit(controlerClub, LocalDateTime.parse("2023-03-08T20:30:00"), 12, 20, numSecretaire);
		ControlerSecretaire.ajouterInscrit(controlerClub, LocalDateTime.parse("2023-03-08T20:30:00"), 11, 20, numSecretaire);
		ControlerSecretaire.ajouterInscrit(controlerClub, LocalDateTime.parse("2023-03-08T20:30:00"), 15, 20, numSecretaire);
		ControlerSecretaire.ajouterInscrit(controlerClub, LocalDateTime.parse("2023-03-08T20:30:00"), 16, 20, numSecretaire);
		List<String> listeMailIns = ControlerSecretaire.ecrireMailInscrit(controlerClub, LocalDateTime.parse("2023-03-08T20:30:00"), numSecretaire);
		System.out.println("--------------");
		for (String mail : listeMailIns) {
			System.out.println(mail);
			System.out.println("--------------");
		}
		BoundaryClub.afficherMembres(controlerClub);
		controlerClub.trieTableauParId();
		BoundaryClub.afficherMembres(controlerClub);
		controlerClub.suppMembre(4);
		BoundaryClub.afficherMembres(controlerClub);
		String nomPrenom = controlerClub.trouverMembre(2);
		System.out.println(nomPrenom);
		System.out.println(controlerClub.suppressionMembrePossible(1));
		System.out.println(controlerClub.suppressionMembrePossible(2));
		System.out.println(controlerClub.suppressionMembrePossible(3));
		System.out.println(controlerClub.suppressionMembrePossible(5));
		Menu.affichageAchat(ControlerTresorier.listerAchatRestantLocation(controlerClub, LocalDateTime.parse("2023-03-08T20:30:00"), numTresorier));
		System.out.println("code retour changement statut de 1 = " + controlerClub.changerStatut(1, Statut.MEMBRE));
		if (controlerClub.rechercherStatut(Statut.PRESIDENT) == -1) {
			System.out.println("Plus de président dans le club");
		}
		System.out.println("code retour changement statut de 15 = " + controlerClub.changerStatut(15, Statut.PRESIDENT));
		System.out.println("code retour changement statut de 2 = " + controlerClub.changerStatut(2, Statut.TRESORIER));
		
		numPresident = controlerClub.rechercherStatut(Statut.PRESIDENT);
		controlerClub.changerStatut(numPresident,Statut.PRESIDENT);
		System.out.println("Président = " + controlerClub.trouverMembre(numPresident));
		System.out.println(controlerClub.extraireInstructionsCamlMembre());
		
		System.out.println(ControlerEvenement.OcalmEve.instructionOcamlFourniture(controlerClub, LocalDateTime.parse("2023-03-08T20:30:00")));
		System.out.println(ControlerEvenement.OcalmEve.extraireInstructionsCamlInscrit(controlerClub, LocalDateTime.parse("2023-03-08T20:30:00")));
		
	}
		
	
}
