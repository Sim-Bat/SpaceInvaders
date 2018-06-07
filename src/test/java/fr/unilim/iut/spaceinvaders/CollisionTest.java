package fr.unilim.iut.spaceinvaders;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import fr.unilim.iut.spaceinvaders.model.Collision;
import fr.unilim.iut.spaceinvaders.model.Dimension;
import fr.unilim.iut.spaceinvaders.model.Position;
import fr.unilim.iut.spaceinvaders.model.SpaceInvaders;

public class CollisionTest {

	private SpaceInvaders spaceinvaders;

    @Before
    public void initialisation() {
	    spaceinvaders = new SpaceInvaders(15, 10);
    }
    
	@Test
	public void test_CollisionEntreDeuxSprite() throws Exception {
		spaceinvaders.positionnerUnNouveauVaisseau(new Dimension(7,2),new Position(5,9), 1);
		spaceinvaders.positionnerUnNouvelEnvahisseur(new Dimension(3,2), new Position(7,1), 1);
		
		spaceinvaders.tirerUnMissile(new Dimension(3,2),2);
		
		spaceinvaders.deplacerMissile();
		
		assertEquals("" + 
			      ".......EEE.....\n" + 
			      ".......EEE.....\n" +
			      "...............\n" + 
			      "...............\n" + 
			      ".......MMM.....\n" + 
			      ".......MMM.....\n" + 
			      "...............\n" + 
			      "...............\n" + 
			      ".....VVVVVVV...\n" + 
			      ".....VVVVVVV...\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
		
		
		spaceinvaders.deplacerMissile();
		
		assertEquals("" + 
			      ".......EEE.....\n" + 
			      ".......EEE.....\n" +
			      ".......MMM.....\n" + 
			      ".......MMM.....\n" + 
			      "...............\n" + 
			      "...............\n" + 
			      "...............\n" + 
			      "...............\n" + 
			      ".....VVVVVVV...\n" + 
			      ".....VVVVVVV...\n" , spaceinvaders.recupererEspaceJeuDansChaineASCII());
		
		spaceinvaders.deplacerMissile();
		
		assertEquals(true , Collision.detecterCollision(spaceinvaders.recupererMissile(), spaceinvaders.recupererEnvahisseur()));
	}
}
