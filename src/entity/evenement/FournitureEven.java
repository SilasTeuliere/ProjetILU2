
package entity.evenement;

/**
 * classe d'une fourniture necessaire a un evenement
 */
public class FournitureEven {
	private Produit produit;
	private int nbrTotal;
	private double prixTotal;
	private int nbrAchete;

	public FournitureEven(Produit produit, int nbrTotal, double prixTotal) {
		this.produit = produit;
		this.nbrTotal = nbrTotal;
		this.prixTotal = prixTotal;
	}

	public int getNbrAchete() {
		return nbrAchete;
	}
	public void setNbrAchete(int nbrAchete) {
		this.nbrAchete = nbrAchete;
	}
	public Produit getProduit() {
		return produit;
	}
	public int getNbrTotal() {
		return nbrTotal;
	}
	public double getPrixTotal() {
		return prixTotal;
	}
	
}
