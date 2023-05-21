
package entity.evenement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import entity.membre.Membre;

/**
 * classe d'un inscrit à un evenement
 */
/**
 * @author silas
 *
 */
public class InscritEven {
	private Membre membre;
	private double budjetPrevisionnel;
	private double budjetReel;
	private FournitureInscrit[] fournitures;
	
	public InscritEven(Membre membre, double budjetPrevisionnel) {
		super();
		this.membre = membre;
		this.budjetPrevisionnel = budjetPrevisionnel;
	}

	public double getBudjetReel() {
		return budjetReel;
	}

	public void setBudjetReel(double budjetReel) {
		this.budjetReel = budjetReel;
	}

	public FournitureInscrit[] getFournitures() {
		return fournitures;
	}

	public void setFournitures(FournitureInscrit[] fournitures) {
		this.fournitures = fournitures;
	}

	public Membre getMembre() {
		return membre;
	}

	public double getBudjetPrevisionnel() {
		return budjetPrevisionnel;
	}
	
	/**
	 * ajout d'un inscrit à un événement
	 * @param evenement
	 * @param membre
	 * @param budjetPrevisionnel
	 */
	public static void ajoutInscrit(Evenement evenement, Membre membre, double budjetPrevisionnel) {
		List<InscritEven> listeInscrit = new ArrayList<>();
		if (evenement.getInscrits() != null) {
			listeInscrit = new ArrayList<>(Arrays.asList(evenement.getInscrits()));
		}
	    listeInscrit.add(new InscritEven(membre, budjetPrevisionnel));
	    InscritEven[] inscrits = new InscritEven[listeInscrit.size()];
	    evenement.setInscrits(listeInscrit.toArray(inscrits));
	}
	
	/**
	 * supprimer l'inscrit transmis de la liste des Inscrits
	 * @param club
	 * @param dateEven date et heure de l'événement
	 */
	public static void supprimerInscrit(Evenement evenement, Membre membre) {
		InscritEven[] inscrits = evenement.getInscrits();
		if (inscrits != null) {
			List<InscritEven> listeInscrits = new ArrayList<>(Arrays.asList(inscrits));
           
			for (InscritEven inscrit : listeInscrits) {
				if (inscrit.getMembre().getId() == membre.getId()) {
					listeInscrits.remove(inscrit);
				}
			}
			InscritEven[] inscritsSortie = new InscritEven[listeInscrits.size()];
			evenement.setInscrits(listeInscrits.toArray(inscritsSortie));
		}
	}

	/**
	 * liste les inscrits d'un Evenement
	 * @param club
	 */
	public static InscritEven[] listerInscrits(Evenement evenement) {
		return evenement.getInscrits();
	}

}
