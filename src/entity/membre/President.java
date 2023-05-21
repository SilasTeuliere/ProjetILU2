
package entity.membre;

import commun.Statut;

/**
 * Classe heritant de membre concernant le president du club
 */
public class President extends Membre {
	public President(int id, String nomPrenom, String email, String adresse, String numTel, int anneeInscr, int derAnneeParticipation) {
		super(id, nomPrenom, email, adresse, numTel, Statut.PRESIDENT, anneeInscr, derAnneeParticipation);
		
	}
	
	/**
	 * Methode inutile faisant de la redefinition
	 */
	public String suppressionMembrePossible() {
		return "Suppression membre Pr�sident impossible sans remplacement.";
	}
	
	/**
	 * V�rifie qu'on ne change pas le status du pr�sident
	 * @return
	 */
	protected int verifierStatusExistant() {	
		// On ne peut pas changer le status du pr�sident directement sans en avoir d�sign� un autre
		return -3;
	}
	
	/**
	 * Change le status en membre 
	 * @param status
	 * @return
	 */
	protected Membre testerChangeStatus(Statut status) {
		if (status.equals(Statut.PRESIDENT)) {
			return super.changerStatut(Statut.MEMBRE);
		}
		return null;
	}
}
