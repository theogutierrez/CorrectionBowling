package bowling;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author rbastide
 */
public class SinglePlayerGameTest {

	private SinglePlayerGame game;

	@Before
	public void setUp() {
		game = new SinglePlayerGame();
	}

	/**
	 * Si on envoie toutes les 20 boules dans la rigole, le score final est 0
	 */
	@Test
	public void testGutterGame() {
		rollMany(20, 0);
		assertEquals(0, game.score());
		assertTrue(game.isFinished());
	}

	@Test
	public void testAllOnes() {
		rollMany(20, 1);
		assertEquals(20, game.score());
		assertTrue(game.isFinished());	}

	@Test
	public void testOneSpare()  {
		rollSpare(); // 10 + 3
		game.lancer(3); // 3
		rollMany(17, 0); // 0
		assertEquals(16, game.score());
		assertTrue(game.isFinished());
	}

	@Test
	public void testOneStrike()  {
		rollStrike(); // 10 + 7
		assertTrue(game.hasCompletedFrame());
                assertEquals(1, game.getNextBallNumber());
		game.lancer(3);
		game.lancer(4);
		assertTrue(game.hasCompletedFrame());
		rollMany(16, 0);
		assertEquals(24, game.score());
		assertTrue(game.isFinished());
	}

	@Test
	public void testPerfectGame() {
		// 12 boules à 10 points
		rollMany(12, 10);
		assertEquals(300, game.score());
		assertTrue(game.isFinished());
	}

	@Test
	public void testTypicalGame()  {
		rollMany(8, 3); // 6 points aux 4 1° tours -> 24
		assertTrue(game.hasCompletedFrame()); // Le dernier tir a terminé le cours précédent
                assertEquals(5, game.getFrameNumber()); // On est au tour n° 5
		rollStrike(); // 10 + 10
		assertTrue(game.hasCompletedFrame());
		rollSpare(); // 10 + 0
		rollMany(6, 0); // 0 points aux 3 tours suivants
		rollMany(3, 10); // 30 points au dernier tour
		assertTrue(game.isFinished());
		assertEquals(84, game.score());
	}

	// Quelques methodes utilitaires pour faciliter l'écriture des tests
	private void rollMany(int n, int pins) {
		for (int i = 0; i < n; i++) {
			game.lancer(pins);
		}
	}

	private void rollSpare() {
		game.lancer(7);
		game.lancer(3);
	}

	private void rollStrike() {
		game.lancer(10);
	}

}
