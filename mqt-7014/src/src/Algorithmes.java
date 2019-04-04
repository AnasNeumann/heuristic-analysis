package src;

/**
 * Classe de résolution du problème
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 09/03/2019
 * @version 1.0
 */
public class Algorithmes {
	
	/**
	 * Constantes
	 */
	private final static Integer TOTAL = 100;

	/**
	 * Méthode de création d'une solution par construction
	 * 
	 * Calcul de la complexité  : 
	 * -> On a vu dans le cours que l'ajout dans un tableau dynamique est au pire des cas O(1)
	 * -> Ainsi cette méthode est d'une complexité au pire des cas de O(n) vu que l'on pourrait ajouter tous les items
	 * 
	 * @param p
	 * @return
	 */
	public static Solution construction(Probleme p) {
		Solution s = new Solution();
		for(Item i : p.getItems()) {
			if(s.getSomme_poids() + i.getPoids() > p.getCapacite()) {
				System.out.println("Nouvelle solution de valeur : "+s.getSomme_valeurs()+" et un poids de "+s.getSomme_poids());
				return s;
			}
			s.ajouter(i);
		}
		return s;
	}

	/**
	 * Remplacer un élément
	 * 
	 * -> La présence des deux boucles sur ce qui pourrait être au pire des cas l'ensemble des objets provoque O(n^2) bien que valeur n'est jamais atteinte
	 * -> Cependant on a vu dans le cours que le retrait d'une élement provoque O(n)
	 * -> La méthode contient est également de O(n)
	 * -> Cette méthode est donc d'une compléxité de O(n^3)
	 * 
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
	 * 
	 * -> La présence des deux boucles sur ce qui pourrait être au pire des cas l'ensemble des objets provoque O(n^2) bien que valeur n'est jamais atteinte
	 * -> Cependant on a vu dans le cours le retrait d'une élement provoque o(n)
	 * -> La méthode contient est également de O(n)
	 * -> Cette méthode est donc d'une compléxité de O(n^3)
	 * 
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
	 * Amélioration d'une solution
	 * 
	 * -> les méthodes de remplacement sont d'une compléxité de O(n^3)
	 * -> elles sont appellées pour chaque items donc en O(n^4)
	 * -> Ce traitement est fait tant qu'il y a des améliorations possibles ce qui au pire des cas revient à n (valeur jamais atteinte)
	 * -> La méthode est donc d'une complexité O(n^5)
	 * 
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
		System.out.println("-> Amélioration à une valeur de : "+s.getSomme_valeurs()+" et un poids de "+s.getSomme_poids());
		return s;
	}

	/**
	 * Construction selon différents ordres
	 * -> le tri est d'une compléxité n x log(n)
	 * -> le mélange est d'une compléxité de n x la génération d'un nombre aléatoire (on va se limiter à dire n)
	 * -> cest traitements sont fait 100 fois (une constante) O(1)
	 * -> donc la compléxité finale de la méthode est de = O(n^5)
	 * @param p
	 * @return
	 */
	public static Solution constructionMulti(Probleme p) {
		p.trier();
		Solution bestOne = amelioration(p, construction(p));
		for(int i=0; i<TOTAL; i++) {
			p.melanger();
			Solution s = amelioration(p, construction(p)); 
			if(s.getSomme_valeurs() > bestOne.getSomme_valeurs()) {
				bestOne = (Solution) s.clone();
			}
		}
		return bestOne;
	}
}
