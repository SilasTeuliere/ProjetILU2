package controler;

import java.time.LocalDateTime;
import java.util.List;

import commun.Statut;
import entity.Club;
import entity.membre.Membre;

public interface IControlerClub {

	Club getClub();

	void setClub(Club club);

	/**
	 * Restitue la liste de tous les membres avec leur statut
	 * @return
	 */
	List<String> listerMembres();

	/**
	 * Restitue la liste de tous les evenements
	 * @return
	 */
	List<String> listerEvenement();

	/**
	 * Trie le tableau Id du membre(Quand c'est pas trié de base)
	 * @return
	 */
	Membre[] trieTableauParId();

	// polymorphisme : un même nom de fonction avec des paramètres différents
	/**
	 * inscription d'un nouveau membre à la date du jour
	 * @param nomPrenom
	 * @param email
	 * @param adresse
	 * @param numTel
	 * @param statut
	 */
	int ajoutMembre(String nomPrenom, String email, String adresse, String numTel, Statut statut);

	/**
	 * inscription d'un nouveau membre à la date du jour - sans transmettre le statut => membre simple
	 * @param nomPrenom
	 * @param email
	 * @param adresse
	 * @param numTel
	 */
	void ajoutMembre(String nomPrenom, String email, String adresse, String numTel);

	/**
	 * suppression d'un membre
	 * @param id - identifiant du membre
	 */
	void suppMembre(int id);

	/**
	 * rend la nombre de membres 
	 * @return nombre de membre du club
	 */
	int nombreMembre();

	/**
	 * trouve le membre dont l'id est mis en entrée 
	 * @param id
	 * @return
	 */
	String trouverMembre(int id);

	/**
	 * Recherche de la première personne ayant un statut donné
	 * @param statut
	 * @return identifiant
	 */
	int rechercherStatut(Statut statut);

	/**
	 * creer programme Ocaml liste des membres
	 * @return
	 */
	String extraireInstructionsCamlMembre();

	/**
	 * change le status d'un membre du club
	 * @param idMembre
	 * @param statut
	 * @return indicateur message d'information
	 */
	int changerStatut(int idMembre, Statut statut);

	/**
	 * réinitialise la liste des membres
	 */
	void initMembres();

	/**
	 * vérifie l'existence d'un événement à la date donnée
	 * @param dateEven date et heure de l'événement
	 * @return booléen indiquant s'il existe un événement à cette date
	 */
	boolean existeEvenement(LocalDateTime dateEven);

	/**
	 * restitue un libellé 
	 * @param id identifiant
	 * @return libellé
	 */
	String suppressionMembrePossible(int id);

}