
package entity.evenement;

/**
 * enumeration de l'ensemble des produits necessaires pour une 3eme mi-temps
 */
public enum Produit {
	QUICHE("Quiche rectangulaire", 20.00, 0.10), 
	PIZZA("Pizza rectangulaire", 15.00, 0.10), 
	WHISKY("Jack Daniel's", 15.00, 0.05),
	PASTIS("Ricard", 10.00, 0.07),
	SAUCISSON("Fouet Catalan", 2.00, 0.25),
	CHIPS("Chips à l'ancienne", 1.50, 0.50), 
	BIERE("Biere Leffe", 0.80, 2.00);
	
	private String nom;
	private double prix;
	private double parPersonne;
	
	private Produit(String nom, double prix, double parPersonne) {
		this.nom = nom;
		this.prix = prix;
		this.parPersonne = parPersonne;
	}
	public String getNom() {
		return nom;
	}
	public double getPrix() {
		return prix;
	}
	public double getParPersonne() {
		return parPersonne;
	}
	
	public static int getNombreProduitDifferent() {
		return values().length;
	}
	
}
