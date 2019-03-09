package src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Classe de données représentant un Problème de sac à dos
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 09/03/2019
 * @version 1.0
 */
public class Probleme {
	private List<Item> items = new ArrayList<Item>();
	private Integer capacite;

	/**
	 * Obtenir le nombre d'items dans la liste
	 * @return
	 */
	public int getNbItemps() {
		return this.items.size();
	}

	/**
	 * Obtenir l'item à la position i
	 * @param i
	 * @return
	 */
	public Item getItem(int i) {
		return this.items.get(i);
	}
	
	/**
	 * Trier les objets selon la méthode définit dans la classe Item
	 */
	public void trier() {
		Collections.sort(items);
	}

	/**
	 * Afficher toutes les informations du problème
	 */
	public String toString() {
		String result = "Probleme = { capacite du sac : "+capacite+", contenu : [ \n";
		for(Item i : items) {
			result += i.toString()+"\n";
		}
		return result+"]};";
	}

	/**
	 * Charger les données à partir d'un fichier
	 * @param fichier
	 * @return
	 */
	public String charger(String fichier) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(fichier));
			String[] line = br.readLine().split(" ");
			int nbrItems = new Integer(line[0]);
			this.capacite = new Integer(line[1]);
			for(int l=1; l<=nbrItems; l++) {
				line = br.readLine().split(" ");
				this.items.add(new Item()
			    		.setId(new Long(line[0]))
			    		.setValeur(new Integer(line[1]))
			    		.setPoids(new Integer(line[2])));
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this.toString();
	}
	
	/**
	 * Mélanger les items avec la méthode de Fisher-Yates
	 */
	public void melanger() {
		//TODO
	}

	/**
	 * @return the items
	 */
	public List<Item> getItems() {
		return items;
	}

	/**
	 * @param items the items to set
	 */
	public Probleme setItems(List<Item> items) {
		this.items = items;
		return this;
	}

	/**
	 * @return the capacite
	 */
	public Integer getCapacite() {
		return capacite;
	}

	/**
	 * @param capacite the capacite to set
	 */
	public Probleme setCapacite(Integer capacite) {
		this.capacite = capacite;
		return this;
	}

}
