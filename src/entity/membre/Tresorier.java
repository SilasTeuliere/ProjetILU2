
package entity.membre;

import commun.Statut;

/**
 * Classe heritant de membre concernant le tresorier du club
 */
public class Tresorier extends Membre{

	public Tresorier(int id, String nomPrenom, String email, String adresse, String numTel, int anneeInscr, int derAnneeParticipation) {
		super(id, nomPrenom, email, adresse, numTel, Statut.TRESORIER, anneeInscr, derAnneeParticipation);
	}
	
	/**
	 * Methode inutile faisant de la redefinition
	 */
	public String suppressionMembrePossible() {
		return "Suppression membre Tresorier impossible sans remplacement.";
	}
	
	/**
	 * Change le status en membre 
	 * @param status
	 * @return
	 */
	protected Membre testerChangeStatus(Statut status) {
		if (status.equals(Statut.TRESORIER)) {
			return super.changerStatut(Statut.MEMBRE);
		}
		return null;
	}

	/**
	 * Vérifie qu'on ne change pas le status
	 * @return
	 */
	protected int verifierStatusExistant() {	
		// On informe qu'il faut trouver un autre tresorier...
		return 2;
	}

}
