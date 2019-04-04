package src;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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
	public int getNbItems() {
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
	public void charger(String fichier) {
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
	}
	
	/**
	 * Mélanger les items avec la méthode de Fisher-Yates (version moderne dans le même tableau)
	 */
	public void melanger() {
		Random generator = new Random();
		for(int i=0; i<items.size()-1; i++) {
			int k = generator.nextInt(items.size()-1);
			Item itemI = items.remove(i);
			Item itemK = items.remove(k);
			items.add(i, itemK);
			items.add(k, itemI);
		}
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
