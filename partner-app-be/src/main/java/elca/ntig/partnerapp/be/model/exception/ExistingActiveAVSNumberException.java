package elca.ntig.partnerapp.be.model.exception;

public class ExistingActiveAVSNumberException extends RuntimeException{
    public ExistingActiveAVSNumberException(String message) {
        super(message);
    }
}
