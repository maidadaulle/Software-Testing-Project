package Models;

public class InvalidIsbnFormatException extends Exception {
    public InvalidIsbnFormatException(String message) {
        super(message);
    }
}
