
package entity.evenement;

/**
 * classe d'une fourniture à amener par un inscrit à un evenement
 */
public class FournitureInscrit {
	private Salle salle;		//une salle ou un produit mais pas les 2 a la fois
	private Produit produit;
	private int nbr;
	private double prix;
	
	public FournitureInscrit(Salle salle, Produit produit, int nbr, double prix) {
		super();
		this.salle = salle;
		this.produit = produit;
		this.nbr = nbr;
		this.prix = prix;
	}

	public Salle getSalle() {
		return salle;
	}

	public Produit getProduit() {
		return produit;
	}

	public int getNbr() {
		return nbr;
	}

	public double getPrix() {
		return prix;
	}
	
}
