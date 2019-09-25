package bowling;

import bowling.SinglePlayerGame;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author rbastide
 */
public class OtherTest {
	private SinglePlayerGame game;

	@Before
	public void setUp() {
		game = new SinglePlayerGame();
	}

	/**
	 * Si on envoie toutes les 20 boules dans la rigole, le score final est 0
	 */
	@Test( expected = UnsupportedOperationException.class )
	public void unLancerDeTrop() {
		rollMany(20, 0); // Le jeu est fini
		// On doit avoir une exception
		game.lancer(0);
	}
	
	@Test
	public void twoStrikeGame() {
		rollStrike(); // 10 + 10 + 4 
		rollStrike(); // 10 + 4 + 3 
		game.lancer(4); // 4
		game.lancer(3); // 3
		assertEquals((10+10+4) + (10+4+3) + 4 + 3, game.score());
	}
	

	// Quelques methodes utilitaires pour faciliter l'écriture des tests
	private void rollMany(int n, int pins) {
		for (int i = 0; i < n; i++) {
			game.lancer(pins);
		}
	}


	private void rollStrike() {
		game.lancer(10);
	}
	
}
