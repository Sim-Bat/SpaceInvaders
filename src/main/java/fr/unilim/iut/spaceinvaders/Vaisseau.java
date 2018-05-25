package fr.unilim.iut.spaceinvaders;

public class Vaisseau {

	int x;
	int y;
	int longueur;
	int hauteur;

	public Vaisseau(int longueur, int hauteur, int x, int y) {
	   this.longueur=longueur;
	   this.hauteur=hauteur;
	   this.x = x;
	   this.y = y;
	}
	
	public Vaisseau(int longueur, int hauteur) {
		this(longueur, hauteur, 0, 0);
	}

    public boolean occupeLaPosition(int x, int y) {
	     return (estAbcisseCouverte(x) && estOrdonneeCouverte(y)) ;
     }

	public boolean estOrdonneeCouverte(int y) {
		return (ordonneeLaPlusBasse()<=y) && (y<=ordonneeLaPlusHaute());
	}

	public int ordonneeLaPlusHaute() {
		return this.y;
	}

	public int ordonneeLaPlusBasse() {
		return ordonneeLaPlusHaute()-this.hauteur+1;
	}

	private boolean estAbcisseCouverte(int x) {
		return (abcisseLaPlusAGauche()<=x) && x<=abscisseLaPlusADroite();
	}

	public int abscisseLaPlusADroite() {
		return this.x+this.longueur-1;
	}

	public int abcisseLaPlusAGauche() {
		return this.x;
	}
		
	public void seDeplacerVersLaDroite() {
		this.x = this.x + 1 ;		
	}

	public void seDeplacerVersLaGauche() {
		this.x = this.x - 1 ;
	}
	
	 public void positionner(int x, int y) {
	    this.x = x;
	    this.y = y;
	 }
}
