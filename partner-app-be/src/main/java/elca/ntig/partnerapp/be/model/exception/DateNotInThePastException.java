package elca.ntig.partnerapp.be.model.exception;

public class DateNotInThePastException extends RuntimeException {
    public DateNotInThePastException(String message) {
        super(message);
    }
}
