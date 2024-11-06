package Models;

public class StandardViewResponse<T> {
    private final T user;
    private final String errorMessage;

    public StandardViewResponse(T user, String errorMessage) {
        this.user = user;
        this.errorMessage = errorMessage;
    }

    public T getUser() {
        return user;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
