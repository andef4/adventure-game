package ch.andefgassm.adventuregame.game;

public class AdventureGameException extends RuntimeException {
	private static final long serialVersionUID = 1028732810082824965L;

	public AdventureGameException(String message, Throwable cause) {
		super(message, cause);
	}

	public AdventureGameException(String message) {
		super(message);
	}

	public AdventureGameException(Throwable cause) {
		super(cause);
	}
}
