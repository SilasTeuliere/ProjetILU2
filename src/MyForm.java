import javax.swing.*;

import commun.Statut;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyForm extends JFrame {
    private JTextField nameField;
    private JTextField addressField;
    private JTextField emailField;
    private JTextField phoneField;
    private JComboBox<String> statutField;

    public MyForm() {
        // Configuration de la fenêtre
        setTitle("Formulaire");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        // Création des composants
        nameField = new JTextField();
        addressField = new JTextField();
        emailField = new JTextField();
        phoneField = new JTextField();

        statutField = new JComboBox<>(new String[]{Statut.PRESIDENT.toString(), Statut.SECRETAIRE.toString(), Statut.TRESORIER.toString(), Statut.MEMBRE.toString()});

        JButton cancelButton = new JButton("Annuler");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Action pour le bouton Annuler (retour au menu par exemple)
                // TODO: Ajoutez votre code ici
            }
        });

        JButton confirmButton = new JButton("Confirmer");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Action pour le bouton Confirmer (validation du formulaire)
                String name = nameField.getText();
                String address = addressField.getText();
                String email = emailField.getText();
                String phone = phoneField.getText();
                String status = (String) statutField.getSelectedItem();

                if (!name.isEmpty() && !address.isEmpty() && !email.isEmpty() && !phone.isEmpty()) {
                    // Si tous les champs sont remplis, faire quelque chose
                    // TODO: Ajoutez votre code ici
                } else {
                    // Afficher un message d'erreur si tous les champs ne sont pas remplis
                    JOptionPane.showMessageDialog(MyForm.this, "Veuillez remplir tous les champs", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Configuration du layout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Ajout des composants à la fenêtre
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Nom et Prénom:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        add(new JLabel("Adresse:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(addressField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        add(new JLabel("Email:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        add(new JLabel("Téléphone:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(phoneField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        add(new JLabel("Statut:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(statutField);

        gbc.gridx = 2;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        add(cancelButton, gbc);

        gbc.gridx = 3;
        gbc.gridy = 5;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        add(confirmButton, gbc);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MyForm().setVisible(true);
            }
        });
    }
}

