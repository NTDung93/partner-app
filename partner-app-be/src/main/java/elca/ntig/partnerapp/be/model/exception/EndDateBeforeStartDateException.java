package elca.ntig.partnerapp.be.model.exception;

public class EndDateBeforeStartDateException extends RuntimeException {
    public EndDateBeforeStartDateException(String message) {
        super(message);
    }
}
