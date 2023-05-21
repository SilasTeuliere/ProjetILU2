package boundary.menu;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import controler.AchatAffichage;
import controler.IControlerClub;
import controler.membre.ControlerPresident;
import controler.membre.ControlerSecretaire;
import controler.membre.ControlerTresorier;
import commun.Statut;

/**
 * Classe presentant la selection des actions que l'on peut faire dans l'appli
 */
public class Menu {
	private static Scanner sc = new Scanner(System.in);
	
	public static void fermeScanner() {
		sc.close();
	}
	
	/**
	 * Saisie des �v�nements
	 * @param data.club
	 * @param sc
	 */
	public static String choisirAction() {
		
		System.out.println("Action � r�aliser : \n - 'T' pour test\n - 'B' pour initialiser la liste du bureau et des membres  - ne faire qu'une fois si pas pass� par test\n"
				+ " - 'M' pour ajouter des membres\n"
				+ " - 'E' pour cr�er des �v�nements\n"
				+ " - '1' pour pr�parer mail d'information sur l'�v�nement\n"
				+ " - 'I' pour inscrire des membres � l'�v�nement\n"
				+ " - '2' pour pr�parer mail d'information sur les fournitures � amener\n"
				+ " - '3' pour finaliser la pr�paration - check list reste � faire pour le tr�sorier\n"
				+ " - 'L1' pour lister les membres\n"
				+ " - 'L2' pour lister les �v�nements\n"			
				+ " - 'S' pour lister supprimer un membre\n"			
				+ " - 'C' pour changer le statut d'un membre\n"			
				+ " - 'retour chariot' pour sortir"
				);
				
		return sc.nextLine();
	}


	/**
	 * Saisie le bureau et tous les membres de l'association
	 * @param data.club
	 * @param param
	 */
	public static void saisirMembres(IControlerClub controlerClub, char param) {
		// pour compl�ter il faudrait pr�voir aussi la gestion d'un mot de passe - en particulier pour les membres du bureau
		String nomPrenom = "";
		String email = "";
		String adresse = "";
		String numTel = "";
		String[] libelleMembre = new String[] {"Veuillez saisir le Pr�sident de l'association :", 
		           "Veuillez saisir le Secr�taire de l'association :", 
		           "Veuillez saisir le Tr�sorier de l'association :",
		           "Veuillez saisir un autre membre (\"Entr�e\" quand termin�) :"};
		int i;
		if (param == 'I') {
			i = 0;
			controlerClub.initMembres();
		} else {
			i = 3;
		}
		do {
			if (i > 3) i = 3;
			System.out.println(libelleMembre[i]);
			System.out.println("Pr�nom et nom du membre :");
			nomPrenom = sc.nextLine();
			if (i<3 || !nomPrenom.equals("")) {
				System.out.println("Adresse mail :");
				email = sc.nextLine();
				System.out.println("Adresse postale :");
				adresse = sc.nextLine();
				System.out.println("Num�ro de t�l�phone :");
				numTel = sc.nextLine();
				sauvegarderMembres(controlerClub, nomPrenom, email, adresse, numTel, i);
			}
			i++;
			// Saisie minimum un pr�sident, un secr�taire et tr�sorier - m�me si nomPr�nom est non valoris�...
		} while (i<3 || !nomPrenom.equals(""));
	}

	/**
	 * Enregistrement d'un membre de l'association
	 * @param data.club
	 * @param nomPrenom
	 * @param email
	 * @param adresse
	 * @param numTel
	 * @param i
	 */
	private static void sauvegarderMembres(IControlerClub controlerClub, String nomPrenom, String email, String adresse, String numTel,
			int i) {
		switch (i) {
		case 0:
			if (nomPrenom.equals("")) {
				nomPrenom = Statut.PRESIDENT.toString();
			}
			controlerClub.ajoutMembre(nomPrenom, email, adresse, numTel, Statut.PRESIDENT);
			break;
		case 1:
			if (nomPrenom.equals("")) {
				nomPrenom = Statut.SECRETAIRE.toString();
			}
			controlerClub.ajoutMembre(nomPrenom, email, adresse, numTel, Statut.SECRETAIRE);
			break;
		case 2:
			if (nomPrenom.equals("")) {
				nomPrenom = Statut.TRESORIER.toString();
			}
			controlerClub.ajoutMembre(nomPrenom, email, adresse, numTel, Statut.TRESORIER);
			break;
		default:
			controlerClub.ajoutMembre(nomPrenom, email, adresse, numTel);
		}
	}

	/**
	 * Saisie des �v�nements
	 * @param data.club
	 */
	public static void saisirEvenements(IControlerClub controlerClub) {
		System.out.println("---------------------------------------------------------");
		
		// Seul le pr�sident est habilit� � saisir un �venement
		// Pour l'instant je n'ai pas g�r� la gestion de mot de passe aussi la saisie de seulement l'identifiant du pr�sident est demand�e en pr�alable
		System.out.println(" Num�ro identifiant du pr�sident : (en cas de non saisie ou de saisie erron�e retour au menu principal)");
		String saisie = sc.nextLine();
		int numeroId = 0;
		
		try {
		   numeroId = Integer.parseInt(saisie);
		} catch (NumberFormatException e) {
		   saisie = "";
		}
		
		if (saisie != "" && ControlerPresident.estPresident(controlerClub, numeroId)) {
			LocalDateTime dateEvenement;
			String description = "";
			String dateHeure = "";
			
			do {
				System.out.println("Ajout d'un nouvel �v�nement :");
				System.out.println("Description (retour chariot pour sortir):");
				description = sc.nextLine();
				if (!description.equals("")) {
					System.out.println("Date de l'�venement au format 'aaaa-mm-jj :");
					dateHeure = sc.nextLine();
					System.out.println("Heure de l'�venement au format 'hh:mm' :");
					dateHeure += "T" + sc.nextLine() + ":00";
					try {
					  dateEvenement = LocalDateTime.parse(dateHeure);
					  ControlerPresident.creerEvenement(controlerClub, dateEvenement, description, numeroId) ;
					} catch (DateTimeParseException e) {
					  System.out.println("Erreur sur la date ou l'heure saisie - ressaisir l'�v�nement");
					}
								}
			} while (!description.equals(""));
		}
	}


	/**
	 * Saisie des inscrits pour un �v�nement
	 * @param data.club
	 */
	public static void saisirInscriptions(IControlerClub controlerClub) {
		System.out.println("---------------------------------------------------------");
		
		// Seul le Secretaire est habilit� � saisir une inscription
		int numeroIdSecretaire = saisirIdSecretaire(controlerClub);
		
		if (numeroIdSecretaire != -1) {
		 
			LocalDateTime dateEvenement = saisieDateHeureEvenement(controlerClub);
				
	        if (dateEvenement != null) {
	        	int numeroIdMembre = 0;
	    		double montantPrevu = 0;
				String saisie = "";
				do {
					System.out.println("Ajout d'un nouvel inscrit pour l'�v�nement du " 
				+ dateEvenement.getDayOfMonth() + "/" + (dateEvenement.getMonthValue()) + "/" + dateEvenement.getYear() + " :");
					System.out.println("Num�ro membre (retour chariot pour sortir) :");
					saisie = sc.nextLine();
					try {
					    numeroIdMembre = Integer.parseInt(saisie);
					} catch (NumberFormatException e) {
						System.out.println("Saisir un nombre entier Positif :");
						numeroIdMembre = 999999;
					}
					if (numeroIdMembre >= 0 && numeroIdMembre < 999999) {
						final String nomPrenom = controlerClub.trouverMembre(numeroIdMembre); 
						if (nomPrenom == null) {
							System.out.println("Personne non trouv�e saisir un autre n�");
						} else {
							System.out.println("Membre concern� : " + nomPrenom + " (Confirmer en tapant 'o' ou 'O')");
							saisie = sc.nextLine();
							if (saisie.length() > 0 && saisie.toUpperCase().charAt(0) == 'O') {
								System.out.println("Montant pr�vu :");
								try {
								  montantPrevu = Double.parseDouble(sc.nextLine());
								  ControlerSecretaire.ajouterInscrit(controlerClub, dateEvenement, numeroIdMembre, montantPrevu, numeroIdSecretaire);
								} catch (Exception e) {
								  System.out.println("erreur sur le montant, ressaisir l'inscription...");
								  numeroIdMembre = 999999;
								}
							}
						}				
					}
				} while (!saisie.equals(""));
	        }
        }
	}


	/**
	 * Mail aux membres
	 * @param data.club
	 */
	public static void ecrireMailMembreParSecretaire(IControlerClub controlerClub) {
		System.out.println("---------------------------------------------------------");
		
		// Seul le Secretaire est habilit� � �crire mail aux membres
		int numeroIdSecretaire = saisirIdSecretaire(controlerClub);
		
		if (numeroIdSecretaire != -1) {
			LocalDateTime dateEvenement = saisieDateHeureEvenement(controlerClub);
			
	        if (dateEvenement != null) {
				final List<String> mailsMembre = ControlerSecretaire.ecrireMailMembre(controlerClub, dateEvenement, numeroIdSecretaire);
				
				if (mailsMembre.size() == 0) {
					System.out.println("Pas de Membre dans l'association");
					return;
				}
				
				for(String mailMembre : mailsMembre) {
					System.out.println(mailMembre);
					System.out.println("-------------------");
				}
	        }
		}
	}

	/**
	 * Mail aux inscrits
	 * @param data.club
	 */
	public static void ecrireMailInscritParSecretaire(IControlerClub controlerClub) {
		System.out.println("---------------------------------------------------------");
		
		// Seul le Secretaire est habilit� � �crire mail aux inscrits
		int numeroIdSecretaire = saisirIdSecretaire(controlerClub);
		
		if (numeroIdSecretaire != -1) {
			LocalDateTime dateEvenement = saisieDateHeureEvenement(controlerClub);
			
	        if (dateEvenement != null) {
				final List<String> mailsInscrit = ControlerSecretaire.ecrireMailInscrit(controlerClub, dateEvenement, numeroIdSecretaire);
				
				if (mailsInscrit.size() == 0) {
					System.out.println("Evenement annul�");
					return;
				}
				
				for(String mailInscrit : mailsInscrit) {
					System.out.println(mailInscrit);
					System.out.println("-------------------");
				}
	        }
		}
	}

	/**
	 * Saisie de l'identifiant du secr�taire
	 * @param controlerClub
	 * @return identifiant du secr�taire
	 */
	private static int saisirIdSecretaire(IControlerClub controlerClub) {
		// Pour l'instant je n'ai pas g�r� la gestion de mot de passe aussi la saisie de seulement l'identifiant du Secretaire est demand�e en pr�alable
		System.out.println(" Num�ro identifiant du secretaire : (en cas de non saisie ou de saisie erron�e retour au menu principal)");
		String saisie = sc.nextLine();
		int numeroIdSecretaire = -1;
		
		try {
		   numeroIdSecretaire = Integer.parseInt(saisie);
		   if (ControlerSecretaire.estSecretaire(controlerClub, numeroIdSecretaire)) {
			   numeroIdSecretaire = -1;
		   }
		} catch (NumberFormatException e) {
			numeroIdSecretaire = -1;
		}
		return numeroIdSecretaire;
	}

	/**
	 * @param controlerClub
	 * @return
	 */
	private static LocalDateTime saisieDateHeureEvenement(IControlerClub controlerClub) {
		LocalDateTime dateEvenement = null;;
		boolean existeEvent = false;
		String dateHeure = "";
		
		System.out.println("Inscription � un �v�nement - pr�ciser :");
		do {
			System.out.println("Date de l'�venement au format 'aaaa-mm-jj :");
			dateHeure = sc.nextLine();
			if (dateHeure == "") {
				dateEvenement = null;
			} else {
				System.out.println("Heure de l'�venement au format 'hh:mm' :");
				dateHeure += "T" + sc.nextLine() + ":00";
				try {
					  dateEvenement = LocalDateTime.parse(dateHeure);
					  existeEvent = controlerClub.existeEvenement(dateEvenement) ;
					} catch (DateTimeParseException e) {
					  dateEvenement = null;
					}
                    if (!existeEvent) {
                    	System.out.println("Erreur sur la date ou l'heure saisie - ressaisir l'�v�nement");	
                    }
			}
		} while (!dateHeure.equals("") && !existeEvent);
		return dateEvenement;
	}

	/**
	 * supprime un membre du club
	 * @param club
	 */
	public static void supprimerMembre(IControlerClub club) {
		String saisie = "";
		int numeroId;
		System.out.println("Suppression d'un membre");
		System.out.println("Num�ro membre (retour chariot pour sortir) :");
		saisie = sc.nextLine();
		try {
			numeroId = Integer.parseInt(saisie);
		} catch (NumberFormatException e) {
			System.out.println("Saisir un nombre entier Positif :");
			return;
		}
		if (numeroId >= 0 ) {
			String nomPrenom = club.trouverMembre(numeroId); 
			if (nomPrenom == null) {
				System.out.println("Personne non trouv�e saisir un autre n�");
			} else {
				System.out.println("Membre concern� : " + nomPrenom + " (Confirmer en tapant 'o' ou 'O')");
				saisie = sc.nextLine();
				if (saisie.length() > 0 && saisie.toUpperCase().charAt(0) == 'O') {
				  club.suppMembre(numeroId);
				}
			}				
		}		
	}

	/**
	 * Change le statut d'un membre
	 * @param club
	 */
	public static void changerStatut(IControlerClub club) {
		String saisie = "";
		int numeroId;
		System.out.println("Changement du statut d'un membre");
		System.out.println("Num�ro membre (retour chariot pour sortir) :");
		saisie = sc.nextLine();
		try {
			numeroId = Integer.parseInt(saisie);
		} catch (NumberFormatException e) {
			System.out.println("Saisir un nombre entier Positif :");
			return;
		}
		if (numeroId >= 0 ) {
			String nomPrenom = club.trouverMembre(numeroId); 
			if (nomPrenom == null) {
				System.out.println("Personne non trouv�e saisir un autre n�");
			} else {
				System.out.println("Membre concern� : " + nomPrenom + " (Confirmer en tapant 'o' ou 'O')");
				saisie = sc.nextLine();
				if (saisie.length() > 0 && saisie.toUpperCase().charAt(0) == 'O') {
					System.out.println("Nouveau statut :");
					saisie = sc.nextLine();
					// Le statut saisie doit �tre un de lib�ll� des statuts possibles
					for (Statut statutTheo : Statut.values()) {
			        	if (statutTheo.toString().equalsIgnoreCase(saisie)) {
			        		switch (club.changerStatut(numeroId, statutTheo)) {
			        		case -9:
			        			System.out.println("Status inconnu");
			        			break;
			        		case -3:
			        			System.out.println("Le status du pr�sident ne peut �tre chang� sans avoir au pr�alable choisi un autre pr�sident");
			        			break;
			        		case -2:
			        			System.out.println("Statut pr�-existant");
			        			break;
			        		case -1:
			        			System.out.println("Membre inconnu");
			        			break;
			        		case 1:
			        			System.out.println("Il faut trouver un autre secretaire");
			        			break;
			        		case 2:
			        			System.out.println("Il faut trouver un autre tr�sorier");
			        			break;
			        		
			        		};
			        		return;
			        	}
			        }
				    
				}
			}				
		}		
	}
	
	
	/**
	 * lister des achats restant � faire pour la soir�e et la location de la salle
	 * @param data.club
	 */
	public static void listerAchatRestantLocationParTresorier(IControlerClub controlerClub) {
		System.out.println("---------------------------------------------------------");
		
		// Seul le Tresorier est habilit� � l'�dition de la liste des produits manquant 
		int numeroIdSecretaire = saisirIdTresorier(controlerClub);
		
		if (numeroIdSecretaire != -1) {
			LocalDateTime dateEvenement = saisieDateHeureEvenement(controlerClub);
			
	        if (dateEvenement != null) {
		        final AchatAffichage achat = ControlerTresorier.listerAchatRestantLocation(controlerClub, dateEvenement, numeroIdSecretaire);
		        // Recherche de ce qui reste � acheter
		        affichageAchat(achat);
	        }
		}
	}

	/**
	 * @param achat
	 */
	public static void affichageAchat(final AchatAffichage achat) {
		System.out.println("Listing des fournitures manquantes :");
		for (String fourniture : achat.getFournitures()) {
			System.out.println("- " +  fourniture + "\n");
		}

		//prix de la salle
		System.out.println("- location de la salle pour " + achat.getPrixSalle() + " euros");

		// Total des liquidit�s pour payer la salle
		System.out.println("Disponibilit� en liquide : " + achat.getDisponibiliteLiquide());
		
		//solde de l'evenement
		System.out.println("Solde pour l'�v�nement : " + achat.getSoldeEvenement());
	}
		

	/**
	 * Saisie de l'identifiant du secr�taire
	 * @param controlerClub
	 * @return identifiant du secr�taire
	 */
	private static int saisirIdTresorier(IControlerClub controlerClub) {
		// Pour l'instant je n'ai pas g�r� la gestion de mot de passe aussi la saisie de seulement l'identifiant du Secretaire est demand�e en pr�alable
		System.out.println(" Num�ro identifiant du tr�sorier : (en cas de non saisie ou de saisie erron�e retour au menu principal)");
		String saisie = sc.nextLine();
		int numeroIdTresorier = -1;
		
		try {
		   numeroIdTresorier = Integer.parseInt(saisie);
		   if (ControlerTresorier.estTresorier(controlerClub, numeroIdTresorier)) {
			   numeroIdTresorier = -1;
		   }
		} catch (NumberFormatException e) {
			numeroIdTresorier = -1;
		}
		return numeroIdTresorier;
	}

}
