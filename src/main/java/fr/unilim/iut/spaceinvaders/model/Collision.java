package fr.unilim.iut.spaceinvaders.model;

public class Collision {
	public static boolean detecterCollision(Sprite sprite1, Sprite sprite2) {
		if(sprite1 == null || sprite2 == null) {
			return false;
		}
		return MissileEtEnvahisseurCollisionOrdonnee(sprite1, sprite2) && MissileEtEnvahisseurCollisionAbcisse(sprite1, sprite2);
	}
	
	public static boolean MissileEtEnvahisseurCollisionOrdonnee(Sprite sprite1, Sprite sprite2) {
		return ordonneeLaPlusBasseDeSprite1EstDansOrdonneesSprite2(sprite2, sprite1) || ordonneeLaPlusHauteDeSprite1EstDansOrdonneesSprite2(sprite2, sprite1);
	}
	
	public static boolean MissileEtEnvahisseurCollisionAbcisse(Sprite sprite1, Sprite sprite2) {
		return abscisseAGaucheDeSprite1EstDansLesAbscissesSprite2(sprite1, sprite2) || abscisseADroiteDeSprite1EstDansLesAbscissesSprite2(sprite1, sprite2);
	}
	
	public static boolean ordonneeLaPlusHauteDeSprite1EstDansOrdonneesSprite2(Sprite sprite1, Sprite sprite2) {
		return sprite1.ordonneeLaPlusHaute() >= sprite2.ordonneeLaPlusBasse() && sprite1.ordonneeLaPlusHaute() <= sprite2.ordonneeLaPlusHaute();
	}
	
	public static boolean ordonneeLaPlusBasseDeSprite1EstDansOrdonneesSprite2(Sprite sprite1, Sprite sprite2) {
		return sprite1.ordonneeLaPlusBasse() >= sprite2.ordonneeLaPlusBasse() && sprite1.ordonneeLaPlusBasse() <= sprite2.ordonneeLaPlusHaute();
	}
	
	public static boolean abscisseADroiteDeSprite1EstDansLesAbscissesSprite2(Sprite sprite1, Sprite sprite2) {
		return sprite1.abscisseLaPlusADroite() >= sprite2.abscisseLaPlusAGauche() && sprite1.abscisseLaPlusADroite() <= sprite2.abscisseLaPlusADroite();
		}
	
	public static boolean abscisseAGaucheDeSprite1EstDansLesAbscissesSprite2(Sprite sprite1, Sprite sprite2) {
		return sprite1.abscisseLaPlusAGauche() >= sprite2.abscisseLaPlusAGauche() && sprite1.abscisseLaPlusAGauche() <= sprite2.abscisseLaPlusADroite();
	}
}
