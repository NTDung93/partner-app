package elca.ntig.partnerapp.be.model.exception;

//@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resourceName, String fieldName, long fieldValue) {
        super(String.format("%s with %s: '%s' not found!", resourceName, fieldName, fieldValue)); // Person with id: '1' not found!
    }
}
