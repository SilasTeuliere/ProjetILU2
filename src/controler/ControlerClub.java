
package controler;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import commun.Statut;
import entity.Club;
import entity.evenement.Evenement;
import entity.membre.Membre;

/**
 * Classe decrivant le club : son adresse et ses membres, les événements proposés...
 */
/**
 * @author silas
 *
 */
/**
 * @author silas
 *
 */
public class ControlerClub implements IControlerClub {
	private Club club;
	public ControlerClub() {
		this.club = new Club();
	}
	

	@Override
	public Club getClub() {
		return club;
	}


	@Override
	public void setClub(Club club) {
		this.club = club;
	}


	/**
	 * Restitue la liste de tous les membres avec leur statut
	 * @return
	 */
	@Override
	public List<String> listerMembres() {
		StringBuilder str;
		final Membre[] membres = club.getMembres();
		int nbMembres = membres.length;
		final List<String> liste = new ArrayList<String>() ;
		for(int i  = 0; nbMembres > i; i++) {
			str = new StringBuilder();
			str.append("- Id : ").append(membres[i].getId()).append(", Nom Prenom : ").append(membres[i].getNomPrenom());
			if(membres[i].getStatut().equals(Statut.PRESIDENT) 
					|| membres[i].getStatut().equals(Statut.SECRETAIRE)
					|| membres[i].getStatut().equals(Statut.TRESORIER)) {
				str.append("	- ").append(membres[i].getStatut().toString()).append(" du club");
			}
			liste.add(str.toString());
		}
		return liste;
	}
	
	
	/**
	 * Restitue la liste de tous les evenements
	 * @return
	 */
	@Override
	public List<String> listerEvenement(){
		StringBuilder str;
		final Evenement[] evenements = club.getEvenements();
		final List<String> liste = new ArrayList<String>() ;
		for(int i  = 0; evenements.length > i; i++) {
			str = new StringBuilder();
			str.append("- Date : ").append(evenements[i].getDate()).append(",  Description : ").append(evenements[i].getDescription());
			liste.add(str.toString());
		}
		return liste;
	}

	
	

	/**
	 * Trie le tableau Id du membre(Quand c'est pas trié de base)
	 * @return
	 */
	@Override
	public Membre[] trieTableauParId() {
		int i = 0;
		final Membre[] membres = club.getMembres();
		int nbMembres = membres.length;
		
		int nbMemb = nbMembres;
        Membre aux = membres[0];
        boolean trie = true;
        while(trie){
            trie = false;
            i = 0;
            while(i < nbMemb - 1){
                if(membres[i].getId() > membres[i + 1].getId()){
                    trie = true;
                    aux = membres[i];
                    membres[i] = membres[i + 1];
                    membres[i + 1] = aux;
                }
                i++;
            }
            nbMemb--;
        }
        return membres;
	}

	// polymorphisme : un même nom de fonction avec des paramètres différents
	/**
	 * inscription d'un nouveau membre à la date du jour
	 * @param nomPrenom
	 * @param email
	 * @param adresse
	 * @param numTel
	 * @param statut
	 * @return identifiant de l'individu créé
	 */
	@Override
	public int ajoutMembre(String nomPrenom, String email, String adresse, String numTel, Statut statut) {
		Membre membre = Membre.ajoutMembre(club, nomPrenom, email, adresse, numTel, statut);
		return membre.getId();
	}

	/**
	 * inscription d'un nouveau membre à la date du jour - sans transmettre le statut => membre simple
	 * @param nomPrenom
	 * @param email
	 * @param adresse
	 * @param numTel
	 */
	@Override
	public void ajoutMembre(String nomPrenom, String email, String adresse, String numTel) {
		Membre.ajoutMembre(club, nomPrenom, email, adresse, numTel);
	}

	/**
	 * suppression d'un membre
	 * @param id - identifiant du membre
	 */
	@Override
	public void suppMembre(int id){
		Membre.suppMembre(club, id);
	}
	
	/**
	 * trouve le membre dont l'id est mis en entrée 
	 * @param id
	 * @return
	 */
	@Override
	public String trouverMembre(int id) {
		Membre membre = Membre.trouverMembre(club, id);
		if (membre == null) {
			return null;
		}
		return membre.getNomPrenom();
	}

	/**
	 * rend la nombre de membres 
	 * @return nombre de membre du club
	 */
	@Override
	public int nombreMembre() {
		return club.getNumeroIDNouveau();
	}

	/**
	 * Recherche de la première personne ayant un statut donné
	 * @param statut
	 * @return identifiant
	 */
	@Override
	public int rechercherStatut(Statut statut) {
		Membre membre = Membre.rechercherStatut(club, statut);
		if (membre == null) {
			return -1;
		}
		return membre.getId();
	}

	/**
	 * creer programme Ocaml liste des membres
	 * @return
	 */
	@Override
	public String extraireInstructionsCamlMembre() {
		final Membre[] membres = club.getMembres();
		int nbMembres = membres.length;
		String texte = " let membres = [\n";
		for(int i = 0; i < nbMembres; i++) {
			if(i == nbMembres-1) {
				texte = texte  + membres[i].getId() +", \"" + membres[i].getNomPrenom() +"\", \"" + membres[i].getEmail() 
						+ "\", \"" + membres[i].getAdresse() +"\", \"" + membres[i].getNumTel() +"\", \"" + membres[i].getStatut()+ "\"\n";
			}
			else {
				texte = texte + membres[i].getId() +", \"" + membres[i].getNomPrenom() +"\", \"" + membres[i].getEmail() 
						+ "\", \"" + membres[i].getAdresse() +"\", \"" + membres[i].getNumTel() +"\", \"" + membres[i].getStatut()+ "\";\n";
			}
		}
		texte = texte + "]\n";
		return texte;
	}
	
	/**
	 * change le status d'un membre du club
	 * @param idMembre
	 * @param statut
	 * @return indicateur message d'information
	 */
	@Override
	public int changerStatut(int idMembre, Statut statut) {
		return Membre.changerStatut(club, idMembre, statut);
	}


	/**
	 * réinitialise la liste des membres
	 */
	@Override
	public void initMembres() {
		Membre.initMembres(club);
	}
	
	
	/**
	 * vérifie l'existence d'un événement à la date donnée
	 * @param dateEven date et heure de l'événement
	 * @return booléen indiquant s'il existe un événement à cette date
	 */
	@Override
	public boolean existeEvenement(LocalDateTime dateEven) {
		if (Evenement.rechercherEvenement(club, dateEven) == null) {
			return false;
		} else {
		    return true;
		}
	}
	
	
	/**
	 * restitue un libellé 
	 * @param id identifiant
	 * @return libellé
	 */
	@Override
	public String suppressionMembrePossible(int id) {
		Membre membre = Membre.trouverMembre(club, id);
		return membre.suppressionMembrePossible();
	}
		
}