package fr.unilim.iut.spaceinvaders.moteurjeu;

/**
 * represente un jeu un jeu est caracterise par la methode evoluer a redefinir
 */
public interface Jeu {

	/**
	 * methode qui contient l'evolution du jeu en fonction de la commande
	 * 
	 * @param commandeUser
	 *            commande utilisateur
	 */
	public void evoluer(Commande commandeUser);
	
	public boolean etreFini();
}
