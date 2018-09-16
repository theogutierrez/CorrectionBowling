package bowling;

/**
 * Correspond à un tour du jeu, constitué de 1 ou 2 lancés Connait sa Frame
 * suivante, pour calculer le bonus éventuel pour le strike ou le spare
 */
public class Frame {

	private final int frameNumber;
	private final Frame nextFrame;
	protected int firstRoll = 0;
	protected int secondRoll = 0;
	protected int ballsThrown = 0;

	public Frame(int number, Frame next) {
		frameNumber = number;
		nextFrame = next;
	}

	/**
	 * enregistrer le r�sultat d'un lancer
	 *
	 * @param number : nombre de quilles abattue � ce lancer
	 */
	public void roll(int number) {
		if (number < 0) {
			throw new IllegalArgumentException("number must be positive");
		}
		if ((number + firstRoll) > 10) {
			throw new IllegalArgumentException("Max 10 points in a Frame");
		}
		if (ballsThrown > 1) {
			throw new UnsupportedOperationException("Max 2 rolls in a Frame");
		}

		ballsThrown++;
		switch (ballsThrown) {
			case 1:
				firstRoll = number;
				break;
			case 2:
				secondRoll = number;
				break;
		}
	}

	/**
	 * @return vrai si ce tour est un stike, faux sinon
	 */
	public boolean isStrike() {
		return firstRoll == 10;
	}

	/**
	 * @return vrai si ce tour est un spare, faux sinon
	 */
	public boolean isSpare() {
		return (firstRoll + secondRoll) == 10;
	}

	/**
	 * @return le bonus accord� par ce tour en cas de spare au tour
	 * pr�c�dent
	 */
	public int spareBonus() {
		return firstRoll;
	}

	/**
	 * @return le bonus accord� par ce tour en cas de strike au tour
	 * pr�c�dent
	 */
	public int strikeBonus() {
		if (isStrike()) {
			return firstRoll + nextFrame.spareBonus();
		} else {
			return firstRoll + secondRoll;
		}
	}

	/**
	 * Le score réalisé à partir de ce tour, en tenant compte des bonus
	 * éventuels
	 *
	 * @return le score
	 *
	 */
	public int score() {
		int frameScore = 0;
		if (isStrike()) {
			frameScore = 10 + nextFrame.strikeBonus();
		} else if (isSpare()) {
			frameScore = 10 + nextFrame.spareBonus();
		} else {
			frameScore = firstRoll + secondRoll;
		}
		return frameScore + nextFrame.score();
	}

	/**
	 * @return vrai si ce tour est fini, faux sinon
	 */
	public boolean isFinished() {
		return isStrike() || (ballsThrown == 2);
	}

	/**
	 * @return le tour suivant ce tour
	 */
	public Frame next() {
		return nextFrame;
	}

	@Override
	public String toString() {
		return "Frame# " + frameNumber + ", first roll: " + firstRoll + ", secondRoll: " + secondRoll;
	}
}
