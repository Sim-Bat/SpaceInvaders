package fr.unilim.iut.spaceinvaders.model;

import java.util.ArrayList;
import java.util.List;

import fr.unilim.iut.spaceinvaders.moteurJeu.Commande;
import fr.unilim.iut.spaceinvaders.moteurJeu.Jeu;
import fr.unilim.iut.spaceinvaders.utils.DebordementEspaceJeuException;
import fr.unilim.iut.spaceinvaders.utils.HorsEspaceJeuException;
import fr.unilim.iut.spaceinvaders.utils.MissileException;

public class SpaceInvaders implements Jeu {

	int longueur;
	int hauteur;
	boolean changerSensDeplacementEnvahisseurs;
	Vaisseau vaisseau;
	List<Missile> missiles;
	List<Envahisseur> envahisseurs;

	public SpaceInvaders(int longueur, int hauteur) {
		this.longueur = longueur;
		this.hauteur = hauteur;
		this.missiles = new ArrayList<Missile>();
		this.envahisseurs = new ArrayList<Envahisseur>();
	}
	
	public char recupererMarqueDeLaPosition(int x, int y) {
		char marque;
		if (this.aUnVaisseauQuiOccupeLaPosition(x, y))
			marque = Constante.MARQUE_VAISSEAU;
		else if (this.aUnMissileQuiOccupeLaPosition(x, y))
			marque = Constante.MARQUE_MISSILE;
		else if (this.aUnEnvahisseurQuiOccupeLaPosition(x, y))
			marque = Constante.MARQUE_ENVAHISSEUR;
		else
			marque = Constante.MARQUE_VIDE;
		return marque;
	}
	
	public boolean estDansEspaceJeu(int x, int y) {
		return ((x >= 0) && (x < longueur)) && ((y >= 0) && (y < hauteur));
	}
	
	public void initialiserJeu() {
		Position positionVaisseau = new Position(this.longueur / 2, this.hauteur - 1);
		Dimension dimensionVaisseau = new Dimension(Constante.VAISSEAU_LONGUEUR, Constante.VAISSEAU_HAUTEUR);
		positionnerUnNouveauVaisseau(dimensionVaisseau, positionVaisseau, Constante.VAISSEAU_VITESSE);

		positionnerNouvelleLigneEnvahisseurs();
	}

	@Override
	public void evoluer(Commande commandeUser) {

		if (commandeUser.gauche) {
			deplacerVaisseauVersLaGauche();
		}

		if (commandeUser.droite) {
			deplacerVaisseauVersLaDroite();
		}

		if (commandeUser.tir) {
			tirerUnMissile(new Dimension(Constante.MISSILE_LONGUEUR, Constante.MISSILE_HAUTEUR),Constante.MISSILE_VITESSE);
		}

		if (this.aUnMissile()) {
			this.deplacerMissiles();
		}
		
		if(this.aUnEnvahisseur()) {
			this.deplacerEnvahisseurs();
			
			this.eliminerEnvahisseur();
		}
	}

	public boolean etreFini() {
		if(!this.aUnEnvahisseur()) {
			return true;
		}
		return false;
	}
	
	public void eliminerEnvahisseur() {
		for (int i=0; i < missiles.size(); i++) {
			for (int j=0; j < envahisseurs.size(); j++) {
				if (Collision.detecterCollision(envahisseurs.get(j), missiles.get(i))) {
					envahisseurs.remove(j);
					missiles.remove(i);
				}
			}
		}
	}
	
	
	//Vaisseau
	
	public String recupererEspaceJeuDansChaineASCII() {
		StringBuilder espaceDeJeu = new StringBuilder();
		for (int y = 0; y < hauteur; y++) {
			for (int x = 0; x < longueur; x++) {
				espaceDeJeu.append(recupererMarqueDeLaPosition(x, y));
			}
			espaceDeJeu.append(Constante.MARQUE_FIN_LIGNE);
		}
		return espaceDeJeu.toString();
	}

	public boolean aUnVaisseauQuiOccupeLaPosition(int x, int y) {
		return this.aUnVaisseau() && vaisseau.occupeLaPosition(x, y);
	}

	public boolean aUnVaisseau() {
		return vaisseau != null;
	}

	public void deplacerVaisseauVersLaDroite() {
		if (vaisseau.abscisseLaPlusADroite() < (longueur - 1)) {
			vaisseau.deplacerHorizontalementVers(Direction.DROITE);
			if (!estDansEspaceJeu(vaisseau.abscisseLaPlusADroite(), vaisseau.ordonneeLaPlusHaute())) {
				vaisseau.positionner(longueur - vaisseau.longueur(), vaisseau.ordonneeLaPlusHaute());
			}
		}
	}

	public void deplacerVaisseauVersLaGauche() {
		if (0 < vaisseau.abscisseLaPlusAGauche())
			vaisseau.deplacerHorizontalementVers(Direction.GAUCHE);
		if (!estDansEspaceJeu(vaisseau.abscisseLaPlusAGauche(), vaisseau.ordonneeLaPlusHaute())) {
			vaisseau.positionner(0, vaisseau.ordonneeLaPlusHaute());
		}
	}

	public void positionnerUnNouveauVaisseau(Dimension dimension, Position position, int vitesse) {

		int x = position.abscisse();
		int y = position.ordonnee();

		if (!estDansEspaceJeu(x, y))
			throw new HorsEspaceJeuException("La position du vaisseau est en dehors de l'espace jeu");

		int longueurVaisseau = dimension.longueur();
		int hauteurVaisseau = dimension.hauteur();

		if (!estDansEspaceJeu(x + longueurVaisseau - 1, y))
			throw new DebordementEspaceJeuException(
					"Le vaisseau déborde de l'espace jeu vers la droite à cause de sa longueur");
		if (!estDansEspaceJeu(x, y - hauteurVaisseau + 1))
			throw new DebordementEspaceJeuException(
					"Le vaisseau déborde de l'espace jeu vers le bas à cause de sa hauteur");

		vaisseau = new Vaisseau(dimension, position, vitesse);
	}

	public Vaisseau recupererVaisseau() {
		return this.vaisseau;
	}

	
	//Missiles
	
	public void tirerUnMissile(Dimension dimensionMissile, int vitesseMissile) {

		if ((vaisseau.hauteur() + dimensionMissile.hauteur()) > this.hauteur)
			throw new MissileException("Pas assez de hauteur libre entre le vaisseau et le haut de l'espace jeu pour tirer le missile");

		Missile nouveauMissile = this.vaisseau.tirerUnMissile(dimensionMissile,vitesseMissile);
		
		boolean peutTirer = true;
		
		for (int i=0; i < missiles.size(); i++) {
			if (Collision.detecterCollision(missiles.get(i), nouveauMissile)) {
				peutTirer = false;
			}
		}
		
		if (peutTirer) {
			this.missiles.add(nouveauMissile);
		}
		
 	}

	private boolean aUnMissileQuiOccupeLaPosition(int x, int y) {
		if (this.aUnMissile()) {
			for (int i=0; i < missiles.size(); i++) {
				if (missiles.get(i).occupeLaPosition(x, y)) {
					return true;
				}
			}
		}
		
		return false;
	}

	public boolean aUnMissile() {
		return !missiles.isEmpty();
	}

	public List<Missile> recupererMissiles() {
		return this.missiles;
	}
	
	public Missile recupererUnSeulMissile(int index) {
		return this.missiles.get(index);
	}

	public void deplacerMissiles() {
		if (this.aUnMissile()) {
			for (int i=0; i < missiles.size(); i++) { 
				missiles.get(i).deplacerVerticalementVers(Direction.HAUT_ECRAN);
				if (!estDansEspaceJeu(missiles.get(i).abscisseLaPlusADroite(), missiles.get(i).ordonneeLaPlusBasse())) {
					this.missiles.remove(i);
				}
			}
		}
	}

	
	//Envahisseurs
	
	public boolean aUnEnvahisseur() {
		return !envahisseurs.isEmpty();
	}

	public List<Envahisseur> recupererEnvahisseurs() {
		return this.envahisseurs;
	}
	
	public Envahisseur recupererUnSeulEnvahisseur(int index) {
		return this.envahisseurs.get(index);
	}
	
	private boolean aUnEnvahisseurQuiOccupeLaPosition(int x, int y) {
		if (this.aUnEnvahisseur()) {
			for (int i=0; i < envahisseurs.size(); i++) {
				if (envahisseurs.get(i).occupeLaPosition(x, y))
					return true;
				}
			}
		return false;
	}

	public void positionnerUnNouvelEnvahisseur(Dimension dimension, Position position, int vitesse) {
		int x = position.abscisse();
		int y = position.ordonnee();

		if (!estDansEspaceJeu(x, y))
			throw new HorsEspaceJeuException("La position de l'envahisseur est en dehors de l'espace jeu");

		int longueurEnvahisseur = dimension.longueur();
		int hauteurEnvahisseur = dimension.hauteur();
		
		if (!estDansEspaceJeu(x + longueurEnvahisseur - 1, y))
			throw new DebordementEspaceJeuException("L'envahisseur déborde de l'espace jeu vers la droite à cause de sa longueur");
		if (!estDansEspaceJeu(x, y - hauteurEnvahisseur + 1))
			throw new DebordementEspaceJeuException("L'envahisseur déborde de l'espace jeu vers le bas à cause de sa hauteur");

		envahisseurs.add(new Envahisseur(dimension,position,vitesse));
	}
	
	public void deplacerEnvahisseursVersLaDroite() {
		for (int i=0; i < envahisseurs.size(); i++) {
			if (envahisseurs.get(i).abscisseLaPlusADroite() < (longueur - 1)) {
				envahisseurs.get(i).deplacerHorizontalementVers(Direction.DROITE);
				if (!estDansEspaceJeu(envahisseurs.get(i).abscisseLaPlusADroite(), envahisseurs.get(i).ordonneeLaPlusHaute())) {
					envahisseurs.get(i).positionner(longueur - envahisseurs.get(i).longueur(), envahisseurs.get(i).ordonneeLaPlusHaute());
				}
			}
		}
	}
	
	public void deplacerEnvahisseursVersLaGauche() {
		for (int i=0; i < envahisseurs.size(); i++) {
			if (0 < envahisseurs.get(i).abscisseLaPlusAGauche())
				envahisseurs.get(i).deplacerHorizontalementVers(Direction.GAUCHE);
			if (!estDansEspaceJeu(envahisseurs.get(i).abscisseLaPlusAGauche(), envahisseurs.get(i).ordonneeLaPlusHaute())) {
				envahisseurs.get(i).positionner(0, envahisseurs.get(i).ordonneeLaPlusHaute());
			}
		}
	}
	
	public void deplacerEnvahisseurs() {
		if (this.envahisseurSeDeplaceVersLaDroite()) {
			this.deplacerEnvahisseursVersLaDroite();
		} else {
				this.deplacerEnvahisseursVersLaGauche();
		}
	}

	public boolean envahisseurSeDeplaceVersLaDroite() {
		for (int i=0; i < envahisseurs.size(); i++) {
			if (this.envahisseursEstAGauche(i)) {
				this.changerSensDeplacementEnvahisseurs = true;
			} else if (this.envahisseursEstADroite(i)) {
				this.changerSensDeplacementEnvahisseurs = false;
			}
		}

		return this.changerSensDeplacementEnvahisseurs;
	}

	public boolean envahisseursEstADroite(int index) {
		return this.longueur - 1 == this.envahisseurs.get(index).abscisseLaPlusADroite();
	}

	public boolean envahisseursEstAGauche(int index) {
		return this.envahisseurs.get(index).abscisseLaPlusAGauche() == 0;
	}
	
	public void positionnerNouvelleLigneEnvahisseurs() {
		int nbEnvahisseur = calculerNbEnvahisseurs();
	
		for(int i = 0 ; i < nbEnvahisseur ; i++) {
			positionnerUnNouvelEnvahisseur(	new Dimension(Constante.ENVAHISSEUR_LONGUEUR, Constante.ENVAHISSEUR_HAUTEUR), new Position(calculerAbscisseEnvahisseur(i), Constante.ENVAHISSEUR_HAUTEUR*2),Constante.ENVAHISSEUR_VITESSE);
		}
	}
	
	public int calculerAbscisseEnvahisseur(int i) {
		return (i * (Constante.ENVAHISSEUR_LONGUEUR * 2));
	}

	public int calculerNbEnvahisseurs() {
		return (Constante.ESPACEJEU_LONGUEUR / (Constante.ENVAHISSEUR_LONGUEUR * 2));
	}
	
}