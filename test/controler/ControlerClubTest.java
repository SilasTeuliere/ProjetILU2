package controler;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import commun.Statut;
import entity.membre.Membre;

class ControlerClubTest {
	IControlerClub club = new ControlerClub();
	
	@BeforeEach
	void setUp() throws Exception {
		club.changerStatut(1, Statut.PRESIDENT);
		club.changerStatut(2, Statut.SECRETAIRE);
		club.changerStatut(3, Statut.TRESORIER);
	}

	//Exigence n°1 : L'utilisateur peut changer le statut d'un membre (hors le Président) en president
	@Test
	void changerStatutMembrePresidentTest() {
		int resultat = club.changerStatut(5, Statut.PRESIDENT);
		assertEquals(0, resultat);
		resultat = club.changerStatut(club.rechercherStatut(Statut.SECRETAIRE), Statut.PRESIDENT);
		assertEquals(1, resultat);
        int ancienSecretaire = club.rechercherStatut(Statut.TRESORIER);
		resultat = club.changerStatut(club.rechercherStatut(Statut.TRESORIER), Statut.PRESIDENT);
		assertEquals(2, resultat);
		assertEquals(ancienSecretaire, club.rechercherStatut(Statut.PRESIDENT));
		assertEquals(Statut.MEMBRE, club.rechercherStatut(5));
	}

	//Exigence n°e1 : L'utilisateur peut changer le statut d'un membre simple en president
	@Test
	void changerStatutMembreDevientPresidentTest() {
		int resultat = club.changerStatut(5, Statut.PRESIDENT);
		assertEquals(0, resultat);
		assertEquals(Statut.PRESIDENT, club.rechercherStatut(5));
	}

	//Exigence n°e2 : L'utilisateur peut changer le statut d'un secretaire en president
	@Test
	void changerStatutSecretaireDevientPresidentTest() {
        int ancienSecretaire = club.rechercherStatut(Statut.SECRETAIRE);
		int resultat = club.changerStatut(club.rechercherStatut(Statut.SECRETAIRE), Statut.PRESIDENT);
		assertEquals(1, resultat);
		assertEquals(Statut.PRESIDENT, club.rechercherStatut(ancienSecretaire));
	}

	//Exigence n°e3 : L'utilisateur peut changer le statut d'un trésorier en president
	@Test
	void changerStatutTresorierDevientPresidentTest() {
        int ancienTresorier = club.rechercherStatut(Statut.TRESORIER);
		int resultat = club.changerStatut(club.rechercherStatut(Statut.TRESORIER), Statut.PRESIDENT);
		assertEquals(2, resultat);
		assertEquals(Statut.PRESIDENT, club.rechercherStatut(ancienTresorier));
	}

	//Exigence n°e4 : L'utilisateur ne peut pas changer le statut d'un président en president
	@Test
	void changerStatutPresidentNeDevientPasPresidentTest() {
		int resultat = club.changerStatut(club.rechercherStatut(Statut.PRESIDENT), Statut.PRESIDENT);
		assertEquals(-2, resultat);
	}

	//Exigence n°2 : L'utilisateur peut changer le statut d'un membre (hors le président et le trésorier) en tresorier
	@Test
	void changerStatutMembreTresorierTest() {
		int resultat = club.changerStatut(5, Statut.TRESORIER);
		assertEquals(resultat, 0);
        int ancienSecretaire = club.rechercherStatut(Statut.SECRETAIRE);
		resultat = club.changerStatut(club.rechercherStatut(Statut.SECRETAIRE), Statut.TRESORIER);
		assertEquals(1, resultat);
		// impossible de ne plus avoir de président
		resultat = club.changerStatut(club.rechercherStatut(Statut.PRESIDENT), Statut.TRESORIER);
		assertEquals(-3, resultat);
		assertEquals(ancienSecretaire, club.rechercherStatut(Statut.TRESORIER));
		assertEquals(Statut.MEMBRE, club.rechercherStatut(5));
	}


	//Exigence n°f1 : L'utilisateur ne peut changer de statut d'un membre simple en Tresorier
	@Test
	void changerStatutMembreDevientTresorierTest() {
		int resultat = club.changerStatut(5, Statut.TRESORIER);
		assertEquals(0, resultat);
		assertEquals(Statut.TRESORIER, club.rechercherStatut(5));
	}

	//Exigence n°f2 : L'utilisateur peut changer le statut d'un secretaire en Trésorier
	@Test
	void changerStatutSecretaireDevientTresoriereTest() {
        int ancienSecretaire = club.rechercherStatut(Statut.SECRETAIRE);
		int resultat = club.changerStatut(club.rechercherStatut(Statut.SECRETAIRE), Statut.TRESORIER);
		assertEquals(1, resultat);
		assertEquals(Statut.TRESORIER, club.rechercherStatut(ancienSecretaire));
	}

	//Exigence n°f3 : L'utilisateur pas changer le statut d'un Trésorier en Trésorier
	@Test
	void changerStatutTresorierNeDevientPasPresidentTest() {
		int resultat = club.changerStatut(club.rechercherStatut(Statut.TRESORIER), Statut.TRESORIER);
		assertEquals(-2, resultat);
	}

	//Exigence n°f4 : L'utilisateur ne peut changer de statut d'un président en Tresorier
	@Test
	void changerStatutPresidentNeDevientPasTresorierTest() {
		int resultat = club.changerStatut(1, Statut.TRESORIER);
		assertEquals(-3, resultat);
	}

	
	//Exigence n°3 : L'utilisateur peut changer le statut d'un membre (hors le président et le secretaire) en secretaire
	@Test
	void changerStatutMembreSecretaireTest() {
		int resultat = club.changerStatut(5, Statut.SECRETAIRE);
		assertEquals(0, resultat);
		resultat = club.changerStatut(club.rechercherStatut(Statut.PRESIDENT), Statut.SECRETAIRE);
		assertEquals(-3, resultat);
        int ancienTresorier = club.rechercherStatut(Statut.TRESORIER);
		resultat = club.changerStatut(club.rechercherStatut(Statut.TRESORIER), Statut.SECRETAIRE);
		assertEquals(2, resultat);
		assertEquals(ancienTresorier, club.rechercherStatut(Statut.SECRETAIRE));
		assertEquals(Statut.MEMBRE, club.rechercherStatut(5));
	}


	//Exigence n°g1 : L'utilisateur ne peut changer de statut d'un membre simple en Tresorier
	@Test
	void changerStatutMembreDevientSecretaireTest() {
		int resultat = club.changerStatut(5, Statut.SECRETAIRE);
		assertEquals(0, resultat);
		assertEquals(Statut.SECRETAIRE, club.rechercherStatut(5));
	}

	//Exigence n°g2 : L'utilisateur peut changer le statut d'un secretaire en Trésorier
	@Test
	void changerStatutSecretaireNeDevientPasSecretaireTest() {
		int resultat = club.changerStatut(club.rechercherStatut(Statut.SECRETAIRE), Statut.SECRETAIRE);
		assertEquals(-2, resultat);
	}

	//Exigence n°g3 : L'utilisateur pas changer le statut d'un Trésorier en Trésorier
	@Test
	void changerStatutTresorierDevientSecretaireTest() {
	    int ancienTresorier = club.rechercherStatut(Statut.TRESORIER);
		int resultat = club.changerStatut(club.rechercherStatut(Statut.TRESORIER), Statut.SECRETAIRE);
		assertEquals(2, resultat);
		assertEquals(Statut.SECRETAIRE, club.rechercherStatut(ancienTresorier));
	}

	//Exigence n°g4 : L'utilisateur ne peut changer de statut d'un président en Tresorier
	@Test
	void changerStatutPresidentNeDevientPasSecretaireTest() {
		int resultat = club.changerStatut(1, Statut.TRESORIER);
		assertEquals(-3, resultat);
	}


	//Exigence n°h1 : L'utilisateur ne peut changer de statut d'un membre simple en Membre
	@Test
	void changerStatutMembreNeDevientPasMembreTest() {
		int resultat = club.changerStatut(5, Statut.MEMBRE);
		assertEquals(-2, resultat);
	}

	//Exigence n°h2 : L'utilisateur peut changer le statut d'un secretaire en Membre
	@Test
	void changerStatutSecretaireDevientMembreTest() {
        int ancienSecretaire = club.rechercherStatut(Statut.SECRETAIRE);
		int resultat = club.changerStatut(club.rechercherStatut(Statut.SECRETAIRE), Statut.MEMBRE);
		assertEquals(1, resultat);
		assertEquals(Statut.MEMBRE, club.rechercherStatut(ancienSecretaire));
	}

	//Exigence n°h3 : L'utilisateur peut changer le statut d'un Trésorier en Membre
	@Test
	void changerStatutTresorierDevientMembreTest() {
        int ancienTresorier = club.rechercherStatut(Statut.TRESORIER);
		int resultat = club.changerStatut(club.rechercherStatut(Statut.TRESORIER), Statut.MEMBRE);
		assertEquals(2, resultat);
		assertEquals(Statut.MEMBRE, club.rechercherStatut(ancienTresorier));
	}

	//Exigence n°h4 : L'utilisateur peut changer de statut d'un président en Membre
	@Test
	void changerStatutPresidentNeDevientPasMembreTest() {
		int resultat = club.changerStatut(1, Statut.MEMBRE);
		assertEquals(-3, resultat);
	}

	
	//Exigence n°4 : Dans la l'association il y a un et un seul president (après initialisation du club)
	@Test
	void changerStatutUniquePresidentTest() {
		 club.changerStatut(5, Statut.PRESIDENT);

		Membre[] membres = club.getClub().getMembres();
		int nb_occ = 0;
		for(int i = 0; i<membres.length; i++) {
			if(membres[i].getStatut().equals(Statut.PRESIDENT)) nb_occ++;
		}
		assertEquals(1, nb_occ);
		
		club.changerStatut(5, Statut.TRESORIER);
		membres = club.getClub().getMembres();
		nb_occ = 0;
		for(int i = 0; i<membres.length; i++) {
			if(membres[i].getStatut().equals(Statut.PRESIDENT)) nb_occ++;
		}
		assertEquals(1, nb_occ);
	}
	//Exigence n°5 : Dans la l'association il y a au plus un unique tresorier
	@Test
	void changerStatutUniqueTresorierTest() {
		club.changerStatut(5, Statut.TRESORIER);
		Membre[] membres = club.getClub().getMembres();
		int nb_occ = 0;
		for(int i = 0; i<membres.length; i++) {
			if(membres[i].getStatut().equals(Statut.TRESORIER)) nb_occ++;
		}
		assertEquals(1, nb_occ);
		
		club.changerStatut(5, Statut.SECRETAIRE);
		membres = club.getClub().getMembres();
		nb_occ = 0;
		for(int i = 0; i<membres.length; i++) {
			if(membres[i].getStatut().equals(Statut.TRESORIER)) nb_occ++;
		}
		assertEquals(0, nb_occ);
	}
	
	//Exigence n°6 : Dans la l'association il y a au plus un secretaire
	@Test
	void changerStatutUniqueSecretaireTest() {

		// pour le test de suppression il faudrait changer le statut du dernier et le supprimer puis recréer un individu
//		club.changerStatut(club.nombreMembre(), Statut.SECRETAIRE);
//        int ancienSecretaire = club.rechercherStatut(Statut.SECRETAIRE);
//        club.suppMembre(ancienSecretaire);
//		club.ajoutMembre("Nouveau", "rap", "deu", "tre");
		club.changerStatut(5, Statut.SECRETAIRE);
		
		Membre[] membres = club.getClub().getMembres();
		int nb_occ = 0;
		for(int i = 0; i<membres.length; i++) {
			if(membres[i].getStatut().equals(Statut.SECRETAIRE)) nb_occ++;
		}
		assertEquals(1, nb_occ);

		club.changerStatut(5, Statut.TRESORIER);
		membres = club.getClub().getMembres();
		nb_occ = 0;
		for(int i = 0; i<membres.length; i++) {
			if(membres[i].getStatut().equals(Statut.SECRETAIRE)) nb_occ++;
		}
		assertEquals(0, nb_occ);
	}

	//Exigence n°7 : impossible de changer le statut d'un membre inexistant
	@Test
	void changerStatutIdentifiantInconnu() {
        int derMembre = club.nombreMembre();
        assertEquals(-1, club.changerStatut(derMembre + 1, Statut.SECRETAIRE));
	}
	
	//Exigence n°9 : impossible de ne pas mettre de statut à un membre
	@Test
	void changerStatutStatutNull() {
        assertEquals(-9, club.changerStatut(5, null));
		assertEquals(Statut.MEMBRE, club.rechercherStatut(5));
	}

	//Exigence n°10 : impossible de mettre un identifiant à 0 ou négatif
	@Test
	void changerStatutIdentifiantNull() {
        assertEquals(-1, club.changerStatut(0, Statut.SECRETAIRE));
        assertEquals(-1, club.changerStatut(-1, Statut.SECRETAIRE));
	}


	//Exigence n°13 : pour changer le statut de l'ancien président il faut avoir choisit un autre président
	@Test
	void changerStatutChangerStatutPresident() {
		// vérifie qu'on ne peut pas changer directement le statut du président
		assertEquals(-3, club.changerStatut(club.rechercherStatut(Statut.PRESIDENT), Statut.TRESORIER));
		
		// vérifier qu'on peut changer le statut de l'ancien président si une autre personne a été nommé président
		club.changerStatut(5, Statut.PRESIDENT);
        assertEquals(0, club.changerStatut(1, Statut.TRESORIER));
		assertEquals(Statut.TRESORIER, club.rechercherStatut(1));
	}

}
