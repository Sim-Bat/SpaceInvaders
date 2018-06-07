package fr.unilim.iut.spaceinvaders.model;

public class Collision {
	public static boolean detecterCollision(Sprite sprite1, Sprite sprite2) {
		if(sprite1 == null || sprite2 == null) {
			return false;
		}
		return sprite1.occupeLaPosition(sprite2.abscisseLaPlusAGauche(), sprite2.ordonneeLaPlusBasse())
				|| sprite1.occupeLaPosition(sprite2.abscisseLaPlusAGauche(), sprite2.ordonneeLaPlusHaute())
				|| sprite1.occupeLaPosition(sprite2.abscisseLaPlusADroite(), sprite2.ordonneeLaPlusBasse())
				|| sprite1.occupeLaPosition(sprite2.abscisseLaPlusADroite(), sprite2.ordonneeLaPlusHaute())
				|| sprite2.occupeLaPosition(sprite1.abscisseLaPlusAGauche(), sprite1.ordonneeLaPlusBasse())
				|| sprite2.occupeLaPosition(sprite1.abscisseLaPlusAGauche(), sprite1.ordonneeLaPlusHaute())
				|| sprite2.occupeLaPosition(sprite1.abscisseLaPlusADroite(), sprite1.ordonneeLaPlusBasse())
				|| sprite2.occupeLaPosition(sprite1.abscisseLaPlusADroite(), sprite1.ordonneeLaPlusHaute());
	}
}
