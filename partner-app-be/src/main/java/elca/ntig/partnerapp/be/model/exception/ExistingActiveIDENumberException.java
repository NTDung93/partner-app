package elca.ntig.partnerapp.be.model.exception;

public class ExistingActiveIDENumberException extends RuntimeException{
    public ExistingActiveIDENumberException(String message) {
        super(message);
    }
}
