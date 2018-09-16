package bowling;

/**
 * Le 10° tour, différent de tous les autres tours : Il peut avoir trois lancés
 * Il n'a pas de lancé suivant
 */
public class TenthFrame extends Frame {

	private int thirdRoll = 0;

	public TenthFrame() {
		super(10, null);
	}

	@Override
	public void roll(int number) {
		if (number < 0) {
			throw new IllegalArgumentException("number must be positive");
		}
		if (ballsThrown > 2) {
			throw new UnsupportedOperationException("Max 3 rolls in last Frame");
		}		
		ballsThrown++;
		switch (ballsThrown) {
			case 1:
				firstRoll = number;
				break;
			case 2:
				secondRoll = number;
				break;
			case 3:
				thirdRoll = number;
		}
	}

	@Override
	public int strikeBonus() {
		return firstRoll + secondRoll;
	}

	@Override
	public int score() {
		return firstRoll + secondRoll + thirdRoll;
	}

	@Override
	public boolean isFinished() {
		return (isStrike() || isSpare()) ? (ballsThrown == 3) : (ballsThrown == 2);
	}

	@Override
	public String toString() {
		return super.toString() + ", third roll: " + thirdRoll;
	}
}
