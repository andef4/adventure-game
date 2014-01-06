package ch.andefgassm.adventuregame.game.assets;

public class AssetLoadException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public AssetLoadException(String message) {
        super(message);
    }

    public AssetLoadException(Throwable cause) {
        super(cause);
    }

    public AssetLoadException(String message, Throwable cause) {
        super(message, cause);
    }
}
