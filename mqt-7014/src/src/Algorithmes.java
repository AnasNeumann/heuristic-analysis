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
		boolean found = true;
		do {
			for(Item i : p.getItems()) {
				if(!s.contient(i)) {
					found = remplaceDeuxParUn(p,s,i);
				} else {
					found = remplaceUnParDeux(p,s,i);
				}
			}
		} while(found);
		return s;
	}

	/**
	 * Remplacer un élément
	 * @param p
	 * @param s
	 * @param oldItem
	 * @return
	 */
	public static boolean remplaceUnParDeux(Probleme p, Solution s, Item oldItem) {
		for(int i=0; i<p.getNbItems(); i++) {
			Item newItem1 = p.getItem(i);
			if(!s.contient(newItem1)) {
				for(int j=i+1; j<p.getNbItems(); j++) {
					Item newItem2 = p.getItem(i);
					if(!s.contient(newItem2)) {
						if((s.getSomme_poids() + newItem1.getPoids() +newItem2.getPoids() - oldItem.getPoids() <= p.getCapacite()) 
								&& (newItem1.getValeur() + newItem2.getValeur() > oldItem.getValeur())) {
							s.ajouter(newItem1);
							s.ajouter(newItem2);
							s.retirer(oldItem);
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	/**
	 * Remplacer Deux élements de la solution par un qui n'y est pas
	 * @param p
	 * @param s
	 * @param newItem
	 * @return
	 */
	public static boolean remplaceDeuxParUn(Probleme p, Solution s, Item newItem) {
		for(int i=0; i<s.getNbItems(); i++) {
			Item old1 = s.getItem(i);
			for(int j=i+1; j<s.getNbItems(); j++) {
				Item old2 = s.getItem(i);
				if((s.getSomme_poids() - old1.getPoids() - old2.getPoids() + newItem.getPoids() <= p.getCapacite()) 
						&& (old1.getValeur() + old2.getValeur() < newItem.getValeur())) {
					s.retirer(old1);
					s.retirer(old2);
					s.ajouter(newItem);
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Construction selon différents ordres
	 * @param p
	 * @return
	 */
	public static Solution constructionMulti(Probleme p) {
		// TODO
		return null;
	}
}
