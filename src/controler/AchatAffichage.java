/**
 * 
 */
package controler;

import java.util.List;

/**
 * @author silas
 *
 */
public class AchatAffichage {
	private List<String> fournitures;
	private String prixSalle;
	private String disponibiliteLiquide;
	private String soldeEvenement;
	
	public List<String> getFournitures() {
		return fournitures;
	}
	public void setFournitures(List<String> fournitures) {
		this.fournitures = fournitures;
	}
	public String getPrixSalle() {
		return prixSalle;
	}
	public void setPrixSalle(String prixSalle) {
		this.prixSalle = prixSalle;
	}
	public String getDisponibiliteLiquide() {
		return disponibiliteLiquide;
	}
	public void setDisponibiliteLiquide(String disponibiliteLiquide) {
		this.disponibiliteLiquide = disponibiliteLiquide;
	}
	public String getSoldeEvenement() {
		return soldeEvenement;
	}
	public void setSoldeEvenement(String soldeEvenement) {
		this.soldeEvenement = soldeEvenement;
	}
	
}
