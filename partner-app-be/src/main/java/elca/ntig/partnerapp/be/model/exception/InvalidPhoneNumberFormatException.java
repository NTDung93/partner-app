package elca.ntig.partnerapp.be.model.exception;

public class InvalidPhoneNumberFormatException extends RuntimeException {
    public InvalidPhoneNumberFormatException(String message) {
        super(message);
    }
}
