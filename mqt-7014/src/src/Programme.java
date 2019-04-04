package src;

/**
 * Classe principale du projet
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 09/03/2019
 * @version 1.0
 */
public class Programme {

	/**
	 * Méthode principale du projet
	 * 
	 * => Les calculs de complexité sont dans la classe Algorithme
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Probleme p = new Probleme();
		p.charger("Instance3.txt");
		System.out.println(p.toString());
		Solution s = Algorithmes.constructionMulti(p);
		System.out.println(s.toString());
	}
}