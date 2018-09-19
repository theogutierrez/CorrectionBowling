package bowling;

/**
 * Correspond à un tour du jeu, constitué de 1 ou 2 lancés Connait sa Frame
 * suivante, pour calculer le bonus éventuel pour le strike ou le spare.
 * 
 * La classe n'est pas publique, sa visibilité est limitée au package
 */
class Frame {

	protected int frameNumber;
	protected Frame nextFrame;
	protected int firstRoll = 0;
	protected int secondRoll = 0;
	protected int ballsThrown = 0;
	
	protected Frame() {/* pour l'héritage */};

	Frame(int number, Frame next) {
		if (number < 1 || number > 9) {
			throw new IllegalArgumentException("Normal frames are numbered from 1 to 9");
		}
		if (next == null) {
			throw new IllegalArgumentException("All normal frames have e next frame");
		}
		
		frameNumber = number;
		nextFrame = next;
	}

	/**
	 * enregistrer le r�sultat d'un lancer
	 *
	 * @param number : nombre de quilles abattue à ce lancer
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
	 * @return le bonus accordé par ce tour en cas de spare au tour
	 * précédent
	 */
	public int spareBonus() {
		return firstRoll;
	}

	/**
	 * @return le bonus accordé par ce tour en cas de strike au tour
	 * précédent
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
