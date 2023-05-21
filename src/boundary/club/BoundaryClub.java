package boundary.club;

import controler.IControlerClub;

public class BoundaryClub {
	
	/**
	 * restitue la liste de tout les membres faisant parties du club en indiquant quand ils appartiennent au bureau du Club
	 * @param controlerClub
	 */
	public static void afficherMembres (IControlerClub controlerClub) {
		for(String membre : controlerClub.listerMembres()) {
			System.out.println(membre);
		}
		System.out.println("-----------------");
	}
	
	/**
	 * affiche le tableau des evenements
	 * @param controlerClub
	 */
	public static void afficherEvenements(IControlerClub controlerClub) {
		for(String evenement : controlerClub.listerEvenement()) {
			System.out.println(evenement);
			System.out.println("\n");
		}
		System.out.println("-----------------");
	}
}
