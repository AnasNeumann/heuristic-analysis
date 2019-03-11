package src;

/**
 * Classe de résolution du problème
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 09/03/2019
 * @version 1.0
 */
public class Algorithmes {

	/**
	 * Méthode de création d'une solution par construction
	 * @param p
	 * @return
	 */
	public static Solution Construction(Probleme p) {
		Solution s = new Solution();
		for(Item i : p.getItems()) {
			if(s.getSomme_poids() > p.getCapacite()+i.getPoids()) {
				return s;
			}
			s.ajouter(i);
		}
		return s;
	}

	/**
	 * Amélioration d'une solution
	 * @param p
	 * @param s
	 * @return
	 */
	public static Solution amelioration(Probleme p, Solution s) {
		
		return s;
	}

}
