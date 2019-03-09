package src;

/**
 * Classe de données représentant un Item
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 09/03/2019
 * @version 1.0
 */
public class Item implements Comparable<Item>{
	private Long id;
	private Integer valeur;
	private Integer poids;
	
	/**
	 * Constructeur vide
	 */
	public Item() {
		
	}
	
	/**
	 * Constructeur parametré
	 * @param id
	 * @param valeur
	 * @param poids
	 */
	public Item(Long id, Integer valeur, Integer poids) {
		this.id = id;
		this.valeur = valeur;
		this.poids = poids;
	}

	/**
	 * Afficher les données de l'objet
	 */
	public String toString() {
		return new String("Item = {id : "+id+", valeur : "+valeur+", poids : "+poids+"};");
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public Item setId(Long id) {
		this.id = id;
		return this;
	}

	/**
	 * @return the valeur
	 */
	public Integer getValeur() {
		return valeur;
	}

	/**
	 * @param valeur the valeur to set
	 */
	public Item setValeur(Integer valeur) {
		this.valeur = valeur;
		return this;
	}

	/**
	 * @return the poids
	 */
	public Integer getPoids() {
		return poids;
	}

	/**
	 * @param poids the poids to set
	 */
	public Item setPoids(Integer poids) {
		this.poids = poids;
		return this;
	}

	/**
	 * Redéfinition de la méthode de comparaison
	 * @param i
	 */
	@Override
	public int compareTo(Item i) {
		if (null != i && 0 != i.getPoids() && 0 != this.getPoids()) {
			return i.getRatio().compareTo(this.getRatio());
		}
		return this.getId().compareTo(i.getId());
	}

	/**
	 * Calculer le ratio d'un item
	 * @param i
	 * @return
	 */
	public Double getRatio() {
		return new Double((this.valeur*1.0)/this.poids);
	}
}
