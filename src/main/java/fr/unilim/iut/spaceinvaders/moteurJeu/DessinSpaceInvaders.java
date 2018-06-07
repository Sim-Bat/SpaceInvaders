package fr.unilim.iut.spaceinvaders.moteurJeu;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.List;

import fr.unilim.iut.spaceinvaders.model.Envahisseur;
import fr.unilim.iut.spaceinvaders.model.Missile;
import fr.unilim.iut.spaceinvaders.model.SpaceInvaders;
import fr.unilim.iut.spaceinvaders.model.Vaisseau;

public class DessinSpaceInvaders implements DessinJeu {

	private SpaceInvaders jeu;

	public DessinSpaceInvaders(SpaceInvaders spaceInvaders) {
		this.jeu = spaceInvaders;
	}

	@Override
	public void dessiner(BufferedImage im) {
		if (this.jeu.aUnVaisseau()) {
			Vaisseau vaisseau = this.jeu.recupererVaisseau();
			this.dessinerUnVaisseau(vaisseau, im);
		}
		if (this.jeu.aUnMissile()) {
			List<Missile> missiles = this.jeu.recupererMissiles();
			for (int i=0; i < missiles.size(); i++) {
				if (missiles.get(i) != null) {
					this.dessinerUnMissile(missiles.get(i), im);
				}
			}
		}
		if (this.jeu.aUnEnvahisseur()) {
			List<Envahisseur> envahisseurs = this.jeu.recupererEnvahisseurs();
			for (int j=0; j < envahisseurs.size(); j++) {
				if (envahisseurs.get(j) != null) {
					this.dessinerUnEnvahisseur(envahisseurs.get(j), im);
				}
			}
		}
	}

	private void dessinerUnVaisseau(Vaisseau vaisseau, BufferedImage im) {
		Graphics2D crayon = (Graphics2D) im.getGraphics();

		crayon.setColor(Color.gray);
		crayon.fillRect(vaisseau.abscisseLaPlusAGauche(), vaisseau.ordonneeLaPlusBasse(), vaisseau.longueur(), vaisseau.hauteur());

	}

	private void dessinerUnMissile(Missile missile, BufferedImage im) {
		Graphics2D crayon = (Graphics2D) im.getGraphics();
		
		crayon.setColor(Color.blue);
		crayon.fillRect(missile.abscisseLaPlusAGauche(), missile.ordonneeLaPlusBasse(), missile.longueur(), missile.hauteur());
	}
	
	private void dessinerUnEnvahisseur(Envahisseur envahisseur, BufferedImage im) {
		Graphics2D crayon = (Graphics2D) im.getGraphics();
		
		crayon.setColor(Color.red);
		crayon.fillRect(envahisseur.abscisseLaPlusAGauche(), envahisseur.ordonneeLaPlusBasse(), envahisseur.longueur(), envahisseur.hauteur());
	}

}
