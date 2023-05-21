package boundary.interfaceAppli;

import javax.swing.*;

import commun.Statut;
import controler.ControlerClub;
import controler.IControlerClub;
import controler.membre.ControlerPresident;
import entity.evenement.Evenement;
import entity.membre.Membre;

import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class Page {
    private static CardLayout cardLayout;
    private static JPanel cardPanel;
    private static JTextField nomPrenomField;
    private static JTextField adresseField;
    private static JTextField emailField;
    private static JTextField telephoneField;
    private static JComboBox<String> statutField;
    private static JComboBox<String> statutFieldAjout; 
    private static JTextField dateField;
    private static JTextArea descriptionArea;
    private static JTextField idMembreField;
    private static JTextField budgetField;
    private static JTextField idMembreSuppressionField;
  
    public static void main(String[] args) {
    	final IControlerClub club = new ControlerClub();
        // Cr�ation de la fen�tre
        JFrame fenetre = new JFrame("Ma Interface Swing");
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setLayout(new BorderLayout());

        // Cr�ation du label
        JLabel label = new JLabel("Menu");
        label.setHorizontalAlignment(JLabel.CENTER);
        fenetre.getContentPane().add(label, BorderLayout.NORTH);

        // Cr�ation du CardLayout et du panel pour les cartes
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // Cr�ation de la premi�re carte (Menu)
        JPanel carteMenu = new JPanel(new BorderLayout());
        JTextArea zoneLecture = new JTextArea(5, 20);
        zoneLecture.append("Action � r�aliser : \n"
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
        		+ " - 'retour chariot' pour sortir");
        zoneLecture.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(zoneLecture);
        carteMenu.add(scrollPane, BorderLayout.CENTER);

        // Cr�ation du panneau pour la zone de saisie et le bouton
        JPanel panneauSaisie = new JPanel(new FlowLayout());

        // Cr�ation du label "Texte � saisir"
        JLabel labelSaisie = new JLabel("Texte � saisir :");
        panneauSaisie.add(labelSaisie);

        // Cr�ation de la zone de texte pour la saisie de texte
        final JTextField zoneSaisie = new JTextField(20);
        panneauSaisie.add(zoneSaisie);

        // Cr�ation du bouton de confirmation
        JButton bouton = new JButton("Confirmer");
        bouton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String texteSaisi = zoneSaisie.getText().toUpperCase();
                zoneLecture.append(texteSaisi + "\n");
                zoneSaisie.setText("");

                if (texteSaisi.contains("M")) {
               		cardLayout.show(cardPanel, "NouveauMembre");
               		label.setText("Nouveau Membre");
                } else if (texteSaisi.contains("E")) {
                    cardLayout.show(cardPanel, "NouvelEvenement");
                    label.setText("Nouvel �v�nement");
                }else if (texteSaisi.equals("I")) {
                    cardLayout.show(cardPanel, "InscriptionEvenement");
                    label.setText("Inscription �v�nement");
                } else if (texteSaisi.contains("S")) {
                    cardLayout.show(cardPanel, "SuppressionMembre");
                    label.setText("Suppression de Membre");
                } else if (texteSaisi.equalsIgnoreCase("C")) {
                    cardLayout.show(cardPanel, "ChangerStatut");
                    label.setText("Changer de Statut");
                } else if (texteSaisi.equals("1")) {
                    JOptionPane.showMessageDialog(fenetre, "Le mail d'information a bien �t� pr�par�", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
                } else if (texteSaisi.equals("2")) {
                    JOptionPane.showMessageDialog(fenetre, "Le mail d'information sur les fournitures a bien �t� pr�par�", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
                } else if (texteSaisi.equals("3")) {
                    JOptionPane.showMessageDialog(fenetre, "Il reste tant � acheter pour tant d'euros", "Check List", JOptionPane.PLAIN_MESSAGE);
                } else if (texteSaisi.equals("L1")) {
                 	
            		// Cr�ation du texte � afficher dans la bo�te de dialogue
                    StringBuilder sb = new StringBuilder();
                    for (String membre : club.listerMembres()) {
                        sb.append(membre).append("\n");
                    }

                    // Affichage de la bo�te de dialogue avec le contenu de la liste des membres
                    JOptionPane.showMessageDialog(fenetre, sb.toString(), "Liste des Membres", JOptionPane.PLAIN_MESSAGE);
                } else if (texteSaisi.equals("L2")) {
                    // Test
                    String[] evenements = {"�v�nement 1", "�v�nement 2", "�v�nement 3"};

                    // Cr�ation du texte � afficher dans la bo�te de dialogue
                    StringBuilder sb = new StringBuilder();
                    for (String evenement : evenements) {
                        sb.append(evenement).append("\n");
                    }

                    // Affichage de la bo�te de dialogue avec le contenu de la liste des �v�nements
                    JOptionPane.showMessageDialog(fenetre, sb.toString(), "Liste des �v�nements", JOptionPane.PLAIN_MESSAGE);
                }
            }
        });
        panneauSaisie.add(bouton);

        carteMenu.add(panneauSaisie, BorderLayout.SOUTH);

        // Ajout de la premi�re carte (Menu) au panel des cartes
        cardPanel.add(carteMenu, "Menu");

        // Cr�ation deuxi�me carte (nouveau membre)
        nouveauMembre(club, fenetre, label);

        // Cr�ation de la troisi�me carte (NouvelEvenement)
        JPanel carteNouvelEvenement = new JPanel(new BorderLayout());
        JPanel evenementPanel = new JPanel(new GridLayout(3, 2));

        // Champ Date
        JLabel dateLabel = new JLabel("Date au format ssaa-mm-jj:");
        dateField = new JTextField(20);
        evenementPanel.add(dateLabel);
        evenementPanel.add(dateField);

        // Champ Description
        JLabel descriptionLabel = new JLabel("Description:");
        descriptionArea = new JTextArea(5, 20);
        descriptionArea.setLineWrap(true);
        JScrollPane scrollPane1 = new JScrollPane(descriptionArea);
        evenementPanel.add(descriptionLabel);
        evenementPanel.add(scrollPane1);

        carteNouvelEvenement.add(evenementPanel, BorderLayout.CENTER);

        // Boutons de confirmation et d'annulation
        JPanel boutonsPanelEvenement = new JPanel(new FlowLayout());

        JButton boutonConfirmerEvenement = new JButton("Confirmer");
        boutonConfirmerEvenement.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
				try {
				    LocalDateTime dateEvenement = LocalDateTime.parse(dateField.getText() + "T00:00:00");
				    String description = descriptionArea.getText();
				    if (description.isEmpty()) {
				    	throw new DateTimeParseException(description, description, 0);
				    }
				    ControlerPresident.creerEvenement(club, dateEvenement, description, club.rechercherStatut(Statut.PRESIDENT)) ;
	                cardLayout.show(cardPanel, "Menu");
	                label.setText("Menu");
	                clearFieldsEvenement();

				} catch (DateTimeParseException err) {
                    JOptionPane.showMessageDialog(fenetre, "Veuillez remplir tous les champs au bon format", "Erreur", JOptionPane.ERROR_MESSAGE);
				}

               // Traiter les informations du nouvel �v�nement ici...

            }
        });
        boutonsPanelEvenement.add(boutonConfirmerEvenement);

        JButton boutonAnnulerEvenement = new JButton("Annuler");
        boutonAnnulerEvenement.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Menu");
                label.setText("Menu");
                clearFieldsEvenement();
            }
        });
        boutonsPanelEvenement.add(boutonAnnulerEvenement);

        carteNouvelEvenement.add(boutonsPanelEvenement, BorderLayout.SOUTH);

        // Ajout de la troisi�me carte (NouvelEvenement) au panel des cartes
        cardPanel.add(carteNouvelEvenement, "NouvelEvenement");

        fenetre.getContentPane().add(cardPanel, BorderLayout.CENTER);
        
     // Cr�ation de la quatri�me carte (InscriptionEvenement)
        JPanel carteInscriptionEvenement = new JPanel(new BorderLayout());
        JPanel inscriptionPanel = new JPanel(new GridLayout(2, 2));

        // Champ Id de Membre
        JLabel idMembreLabel = new JLabel("Id de Membre:");
        idMembreField = new JTextField(20); // Utilisation de la variable statique existante
        inscriptionPanel.add(idMembreLabel);
        inscriptionPanel.add(idMembreField);

        // Champ Budget
        JLabel budgetLabel = new JLabel("Budget:");
        budgetField = new JTextField(20); // Utilisation de la variable statique existante
        inscriptionPanel.add(budgetLabel);
        inscriptionPanel.add(budgetField);

        carteInscriptionEvenement.add(inscriptionPanel, BorderLayout.CENTER);

        // Boutons de confirmation et d'annulation
        JPanel boutonsPanelInscription = new JPanel(new FlowLayout());

        JButton boutonConfirmerInscription = new JButton("Confirmer");
        boutonConfirmerInscription.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int idMembre = Integer.parseInt(idMembreField.getText());
                float budget = Float.parseFloat(budgetField.getText());
                cardLayout.show(cardPanel, "Menu");
                label.setText("Menu");
                clearFieldsInscription();
            }
        });
        boutonsPanelInscription.add(boutonConfirmerInscription);

        JButton boutonAnnulerInscription = new JButton("Annuler");
        boutonAnnulerInscription.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Menu");
                label.setText("Menu");
                clearFieldsInscription();
            }
        });
        boutonsPanelInscription.add(boutonAnnulerInscription);

        carteInscriptionEvenement.add(boutonsPanelInscription, BorderLayout.SOUTH);

        // Ajout de la 4ieme carte (InscriptionEvenement) au panel des cartes
        cardPanel.add(carteInscriptionEvenement, "InscriptionEvenement");

        // Cr�ation de la cinqui�me carte (SuppressionMembre)
        suppressionMembre(club, fenetre, label);
        
        // Cr�ation de la 6ieme carte (ChangerStatut)
        changerStatut(club, fenetre, label);

        // Affichage de la fen�tre
        fenetre.pack();
        fenetre.setVisible(true);
    }

	/**
	 * Cr�ation de la deuxi�me carte (NouveauMembre)
	 * @param fenetre
	 * @param label
	 */
	private static void nouveauMembre(IControlerClub club, JFrame fenetre, JLabel label) {
        // Champ Nom et Pr�nom
        nomPrenomField = new JTextField(20);
 
        // Champ Adresse
        adresseField = new JTextField(20);

        // Champ Email
        emailField = new JTextField(20);

        // Champ Num�ro de t�l�phone
        telephoneField = new JTextField(20);

        // Champ Statut
        statutFieldAjout = new JComboBox<>(new String[]{Statut.MEMBRE.toString(), Statut.PRESIDENT.toString(), Statut.SECRETAIRE.toString(), Statut.TRESORIER.toString()});

        // Boutons de confirmation et d'annulation
        JButton boutonConfirmer = new JButton("Confirmer");
        boutonConfirmer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nomPrenom = nomPrenomField.getText();
                String adresse = adresseField.getText();
                String email = emailField.getText();
                String telephone = telephoneField.getText();
                String statut = (String)statutFieldAjout.getSelectedItem();
                
                if (!nomPrenom.isEmpty() && !adresse.isEmpty() && !email.isEmpty() && !telephone.isEmpty()) {
                	// Traitement des informations du nouveau membre
                	Statut statutSaisie = Statut.MEMBRE;
                	for (Statut statutTheo : Statut.values()) {
			        	if (statutTheo.toString().equalsIgnoreCase(statut)) {
			        		statutSaisie = statutTheo;
			        	}
                	}
                	club.changerStatut(club.ajoutMembre(nomPrenom, email, adresse, telephone, Statut.MEMBRE), statutSaisie);
                    cardLayout.show(cardPanel, "Menu");
                    label.setText("Menu");
                } else {
                    // Afficher un message d'erreur si tous les champs ne sont pas remplis
                    JOptionPane.showMessageDialog(fenetre, "Veuillez remplir tous les champs", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
                clearFields();
            }
        });

        JButton boutonAnnuler = new JButton("Annuler");
        boutonAnnuler.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Menu");
                label.setText("Menu");
                clearFields();
            }
        });

         // Configuration du layout
        JPanel formulairePanel = new JPanel(new GridLayout(10, 20));
        formulairePanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Ajout des composants � la fen�tre
        gbc.gridx = 0;
        gbc.gridy = 0;
        formulairePanel.add(new JLabel("Nom et Pr�nom:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formulairePanel.add(nomPrenomField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        formulairePanel.add(new JLabel("Adresse:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formulairePanel.add(adresseField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        formulairePanel.add(new JLabel("Email:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formulairePanel.add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        formulairePanel.add(new JLabel("T�l�phone:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formulairePanel.add(telephoneField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        formulairePanel.add(new JLabel("Statut:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formulairePanel.add(statutFieldAjout, gbc);

        gbc.gridx = 2;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        formulairePanel.add(boutonAnnuler, gbc);

        gbc.gridx = 3;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        formulairePanel.add(boutonConfirmer, gbc);

        // Ajout de la deuxi�me carte (NouveauMembre) au panel des cartes
        JPanel carteNouveauMembre = new JPanel(new BorderLayout());
        carteNouveauMembre.add(formulairePanel, BorderLayout.CENTER);
        cardPanel.add(carteNouveauMembre, "NouveauMembre");
	}

	/**
	 * Cr�ation de la cinqui�me carte (SuppressionMembre)
	 * @param club
	 * @param fenetre
	 * @param label
	 */
	private static void suppressionMembre(final IControlerClub club, JFrame fenetre, JLabel label) {
        // Champ ID Membre
        JTextField idMembreFieldSuppression = new JTextField(10);

        // Boutons de confirmation et d'annulation
        JButton boutonConfirmerSuppressionMembre = new JButton("Confirmer");
        boutonConfirmerSuppressionMembre.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              	 int idMembre = 0;
            	 try {
            		 idMembre = Integer.parseInt(idMembreFieldSuppression.getText());
                     if (idMembre > 0 && idMembre <= club.nombreMembre()) {
                         // Traiter la suppression du membre
                    	 club.suppMembre(idMembre);
                         cardLayout.show(cardPanel, "Menu");
                         label.setText("Menu");
                         idMembreFieldSuppression.setText("");
                     } else {
                         // Afficher un message d'erreur si tous les champs ne sont pas remplis
                         JOptionPane.showMessageDialog(fenetre, "Veuillez saisir un identifiant de membre existant", "Erreur", JOptionPane.ERROR_MESSAGE);
                     }
            	 } catch (NumberFormatException err) {
                     // Afficher un message d'erreur si tous les champs ne sont pas remplis
                     JOptionPane.showMessageDialog(fenetre, "Veuillez remplir tous les champs", "Erreur", JOptionPane.ERROR_MESSAGE);               	 
                 }
            }
        });

        JButton boutonAnnulerSuppressionMembre = new JButton("Annuler");
        boutonAnnulerSuppressionMembre.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Menu");
                label.setText("Menu");
                idMembreFieldSuppression.setText("");
            }
        });
        
        JPanel formulairePanelSuppressionMembre = new JPanel(new GridLayout(1, 2));
        formulairePanelSuppressionMembre.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
       
        gbc.gridx = 0;
        gbc.gridy = 0;
        formulairePanelSuppressionMembre.add(new JLabel("ID Membre:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formulairePanelSuppressionMembre.add(idMembreFieldSuppression, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        formulairePanelSuppressionMembre.add(boutonAnnulerSuppressionMembre, gbc);

        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        formulairePanelSuppressionMembre.add(boutonConfirmerSuppressionMembre, gbc);

        JPanel carteSuppressionMembre = new JPanel(new BorderLayout());
        carteSuppressionMembre.add(formulairePanelSuppressionMembre, BorderLayout.CENTER);

        // Ajout de la 5ieme carte (SuppressionMembre) au panel des cartes
        cardPanel.add(carteSuppressionMembre, "SuppressionMembre");
	}

	/**
	 * Cr�ation de la 6ieme carte (ChangerStatut)
	 * @param club
	 * @param fenetre
	 * @param label
	 * @return panel carte changer Statut
	 */
	private static void changerStatut(final IControlerClub club, JFrame fenetre, JLabel label) {
       
        // Champs de saisie pour l'ID du membre et le nouveau statut
        idMembreField = new JTextField(10);

        statutField = new JComboBox<>(new String[]{Statut.MEMBRE.toString(), Statut.PRESIDENT.toString(), Statut.SECRETAIRE.toString(), Statut.TRESORIER.toString()});

        JButton boutonConfirmerChangerStatut = new JButton("Confirmer");
        boutonConfirmerChangerStatut.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e) {
            	 int idMembre = 0;
            	 try {
            		 idMembre = Integer.parseInt(idMembreField.getText());
                     String statut = (String)statutField.getSelectedItem();
                     
                     if (idMembre > 0 && idMembre <= club.nombreMembre()) {
                    	// Traitement du changement de statut du membre
                     	Statut statutSaisie = Statut.MEMBRE;
                     	for (Statut statutTheo : Statut.values()) {
     			        	if (statutTheo.toString().equalsIgnoreCase(statut)) {
     			        		statutSaisie = statutTheo;
     			        	}
                     	}
                     	switch (club.changerStatut(idMembre, statutSaisie)) {
    	        		case -9:
    	        			JOptionPane.showMessageDialog(fenetre, "Status inconnu");
    	        			break;
    	        		case -3:
    	        			JOptionPane.showMessageDialog(fenetre, "Le status du pr�sident ne peut �tre chang� sans avoir au pr�alable choisi un autre pr�sident");
    	        			break;
    	        		case -2:
    	        			JOptionPane.showMessageDialog(fenetre, "Membre inconnu");
    	        			break;
    	        		case -1:
    	        			JOptionPane.showMessageDialog(fenetre, "Statut pr�-existant");
    	        			break;
    	        		case 1:
    	        			JOptionPane.showMessageDialog(fenetre, "Il faut trouver un autre secretaire");
    	                 	cardLayout.show(cardPanel, "Menu");
    	                    label.setText("Menu");
    	                    clearFieldsChangerStatut();
    	        			break;
    	        		case 2:
    	        			JOptionPane.showMessageDialog(fenetre, "Il faut trouver un autre tr�sorier");
    	        		default:
    	                 	cardLayout.show(cardPanel, "Menu");
    	                    label.setText("Menu");
    	                    clearFieldsChangerStatut();
    	        		};
                     } else {
                        // Afficher un message d'erreur si tous les champs ne sont pas remplis
                        JOptionPane.showMessageDialog(fenetre, "Veuillez saisir un identifiant de membre existant", "Erreur", JOptionPane.ERROR_MESSAGE);
                     }

            	 } catch (NumberFormatException err) {
                     // Afficher un message d'erreur si tous les champs ne sont pas remplis
                     JOptionPane.showMessageDialog(fenetre, "Veuillez remplir tous les champs", "Erreur", JOptionPane.ERROR_MESSAGE);               	 
                 }
             }
         });

         JButton boutonAnnulerChangerStatut = new JButton("Annuler");
         boutonAnnulerChangerStatut.addActionListener(new ActionListener() {
             public void actionPerformed(ActionEvent e) {
                 cardLayout.show(cardPanel, "Menu");
                 label.setText("Menu");
                 idMembreField.setText("");
                 clearFieldsChangerStatut();
              }
         });

        // Ajout des composants � la fen�tre
        JPanel formulairePanelChangerStatut = new JPanel(new GridLayout(10, 20));
        formulairePanelChangerStatut.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
       
        gbc.gridx = 0;
        gbc.gridy = 0;
        formulairePanelChangerStatut.add(new JLabel("ID Membre:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formulairePanelChangerStatut.add(idMembreField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        formulairePanelChangerStatut.add(new JLabel("Statut:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formulairePanelChangerStatut.add(statutField, gbc);

        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        formulairePanelChangerStatut.add(boutonAnnulerChangerStatut, gbc);

        gbc.gridx = 3;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        formulairePanelChangerStatut.add(boutonConfirmerChangerStatut, gbc);

        JPanel carteChangerStatut = new JPanel(new BorderLayout());
        carteChangerStatut.add(formulairePanelChangerStatut, BorderLayout.CENTER);

        // Ajout de la 6ieme carte (ChangerStatut) au panel des cartes
        cardPanel.add(carteChangerStatut, "ChangerStatut");
        
	}

	private static void clearFields() {
        nomPrenomField.setText("");
        adresseField.setText("");
        emailField.setText("");
        telephoneField.setText("");
    }

    private static void clearFieldsEvenement() {
        dateField.setText("");
        descriptionArea.setText("");
    }
    
    // M�thode pour vider les champs de saisie de l'inscription � l'�v�nement
    private static void clearFieldsInscription() {
        idMembreField.setText("");
        budgetField.setText("");
    }
    
    private static void clearFieldsChangerStatut() {
        idMembreField.setText("");
    }
}
