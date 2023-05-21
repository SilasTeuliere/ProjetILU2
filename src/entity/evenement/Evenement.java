package entity.evenement;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import entity.Club;

/**
 * Classe decrivant les evenements qu'organise le club : la salle ou il se deroule, les membres y etant inscrit...
 */
public class Evenement {
	private LocalDateTime date ;
	private String description;
	private FournitureEven[] fournitures = new FournitureEven[Produit.getNombreProduitDifferent()];
	private Salle lieu;
	private InscritEven[] inscrits = null;
	private double budgetEven;
	
	public Evenement(LocalDateTime date, String description) {
		super();
		this.date = date;
		this.description = description;
	}
	
	public InscritEven[] getInscrits() {
		return inscrits;
	}
	
	public void setInscrits(InscritEven[] inscrits) {
		this.inscrits = inscrits;
	}
	
	public FournitureEven[] getFournitures() {
		return fournitures;
	}
	
	public void setFournitures(FournitureEven[] fournitures) {
		this.fournitures = fournitures;
	}
	
	public Salle getLieu() {
		return lieu;
	}
	
	public void setLieu(Salle lieu) {
		this.lieu = lieu;
	}
	
	public LocalDateTime getDate() {
		return date;
	}
	
	public String getDescription() {
		return description;
	}
	
	public double getBudgetEven() {
		return budgetEven;
	}

	public void setBudgetEven(double budgetEven) {
		this.budgetEven = budgetEven;
	}

	/**
	 * ajoute l'�venement transmis � la liste des �v�nements un �v�nement � la date donn�e avec les informations transmises
	 * @param club
	 * @param dateEven date et heure de l'�v�nement
	 * @param detail information sur l'�v�nement
	 */
	public static void ajouterEvenement(Club club, LocalDateTime dateEven, String detail) {
		List<Evenement> listeEvenements = new ArrayList<>();
		Evenement[] evenements = club.getEvenements();
		if (evenements != null) {
			listeEvenements = new ArrayList<>(Arrays.asList(evenements));
		}
	    listeEvenements.add(new Evenement(dateEven, detail));
	    Evenement[] evenementsSortie = new Evenement[listeEvenements.size()];
	    club.setEvenements(listeEvenements.toArray(evenementsSortie));
	}
	
	/**
	 * supprimer l'�venement transmis de la liste des �v�nements
	 * @param club
	 * @param dateEven date et heure de l'�v�nement
	 */
	public static void supprimerEvenement(Club club, LocalDateTime dateEven) {
		Evenement[] evenements = club.getEvenements();
		if (evenements != null) {
			List<Evenement> listeEvenements = new ArrayList<>(Arrays.asList(evenements));
           
			for (Evenement evenement : listeEvenements) {
				if (dateEven.equals(evenement.getDate())) {
					listeEvenements.remove(evenement);
				}
			}
			Evenement[] evenementsSortie = new Evenement[listeEvenements.size()];
			club.setEvenements(listeEvenements.toArray(evenementsSortie));
		}
	}
	
	/**
	 * Recherche d'un �v�nement � la date donn�e
	 * @param club
	 * @param dateEven date et heure de l'�v�nement
	 * @return bool�en indiquant s'il existe un �v�nement � cette date
	 */
	public static Evenement rechercherEvenement(Club club, LocalDateTime dateEven) {
		final Evenement[] evenements = club.getEvenements();
		for(int i  = 0; evenements.length > i; i++) {
			if (dateEven.equals(evenements[i].getDate())) {
				return evenements[i];
			};
		}
		return null;
	}

}
