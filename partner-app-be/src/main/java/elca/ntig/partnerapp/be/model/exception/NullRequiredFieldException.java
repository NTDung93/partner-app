package elca.ntig.partnerapp.be.model.exception;

public class NullRequiredFieldException extends RuntimeException{
    public NullRequiredFieldException(String message) {
        super(message);
    }
}
