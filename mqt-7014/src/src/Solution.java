package src;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe de données représentant une solution à un problème de sac à dos
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 09/03/2019
 * @version 1.0
 */
public class Solution {
	private List<Item> items = new ArrayList<Item>();
	private Integer somme_poids;
	private Integer somme_valeurs;

	/**
	 * Obtenir le nombre d'objets dans le sac
	 * @return
	 */
	public int getNbItems() {
		return this.items.size();
	}

	/**
	 * Obtenir un item à la position i
	 * @param i
	 * @return
	 */
	public Item getItem(int i) {
		return this.items.get(i);
	}

	/**
	 * Verifier si l'objet est présent dans le sac
	 * @param it
	 * @return
	 */
	public boolean contient(Item it) {
		return this.items.contains(it);
	}

	/**
	 * Ajouter un nouvel item dans le sac
	 * @param it
	 */
	public void ajouter(Item it) {
		this.somme_poids += it.getPoids();
		this.somme_valeurs += it.getValeur();
		this.items.add(it);
	}

	/**
	 * Retirner un item du sac
	 * @param it
	 */
	public void retirer(Item it) {
		this.somme_poids -= it.getPoids();
		this.somme_valeurs -= it.getValeur();
		this.items.remove(it);
	}
	
	/**
	 * Afficher les détails de la solution
	 */
	public String toString() {
		String result = "Solution = { total valeur : "+somme_valeurs+", total poids : "+somme_poids+", contenu : [ \n";
		for(Item i : items) {
			result += i.toString()+"\n";
		}
		return result+"]};";
	}
	
	/**
	 * Clonner en profondeur la solution
	 */
	public Object clone() {
		Solution s = new Solution()
				.setSomme_poids(this.somme_poids)
				.setSomme_valeurs(this.somme_valeurs);
		for(Item i : this.items) {
			s.ajouter(new Item().setId(i.getId()).setPoids(i.getPoids()).setValeur(i.getValeur()));
		}
		return s;
	}

	/**
	 * @return the items
	 */
	public List<Item> getItems() {
		return items;
	}

	/**
	 * @param items
	 *            the items to set
	 */
	public Solution setItems(List<Item> items) {
		this.items = items;
		return this;
	}

	/**
	 * @return the somme_poids
	 */
	public Integer getSomme_poids() {
		return somme_poids;
	}

	/**
	 * @param somme_poids
	 *            the somme_poids to set
	 */
	public Solution setSomme_poids(Integer somme_poids) {
		this.somme_poids = somme_poids;
		return this;
	}

	/**
	 * @return the somme_valeurs
	 */
	public Integer getSomme_valeurs() {
		return somme_valeurs;
	}

	/**
	 * @param somme_valeurs
	 *            the somme_valeurs to set
	 */
	public Solution setSomme_valeurs(Integer somme_valeurs) {
		this.somme_valeurs = somme_valeurs;
		return this;
	}
}
