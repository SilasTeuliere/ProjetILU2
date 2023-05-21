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
	 * Trie le tableau Id du membre(Quand c'est pas tri� de base)
	 * @return
	 */
	Membre[] trieTableauParId();

	// polymorphisme : un m�me nom de fonction avec des param�tres diff�rents
	/**
	 * inscription d'un nouveau membre � la date du jour
	 * @param nomPrenom
	 * @param email
	 * @param adresse
	 * @param numTel
	 * @param statut
	 */
	int ajoutMembre(String nomPrenom, String email, String adresse, String numTel, Statut statut);

	/**
	 * inscription d'un nouveau membre � la date du jour - sans transmettre le statut => membre simple
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
	 * trouve le membre dont l'id est mis en entr�e 
	 * @param id
	 * @return
	 */
	String trouverMembre(int id);

	/**
	 * Recherche de la premi�re personne ayant un statut donn�
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
	 * r�initialise la liste des membres
	 */
	void initMembres();

	/**
	 * v�rifie l'existence d'un �v�nement � la date donn�e
	 * @param dateEven date et heure de l'�v�nement
	 * @return bool�en indiquant s'il existe un �v�nement � cette date
	 */
	boolean existeEvenement(LocalDateTime dateEven);

	/**
	 * restitue un libell� 
	 * @param id identifiant
	 * @return libell�
	 */
	String suppressionMembrePossible(int id);

}