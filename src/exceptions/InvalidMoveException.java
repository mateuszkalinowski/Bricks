package exceptions;

/**
 * Created by Mateusz on 08.12.2016.
 * Project Bricks
 */
public class InvalidMoveException extends Exception {
    public InvalidMoveException(String message) {
        super(message);
    }
}
