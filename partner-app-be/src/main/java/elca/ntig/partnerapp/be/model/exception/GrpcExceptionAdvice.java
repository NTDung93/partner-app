package elca.ntig.partnerapp.be.model.exception;

import io.grpc.Metadata;
import io.grpc.StatusRuntimeException;
import io.grpc.Status;
import net.devh.boot.grpc.server.advice.GrpcAdvice;
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler;
import org.apache.log4j.Logger;

import javax.validation.ConstraintViolationException;

@GrpcAdvice
public class GrpcExceptionAdvice {

    private Logger logger = Logger.getLogger(GrpcExceptionAdvice.class);

    private static final Metadata.Key<String> KEY_NAME = Metadata.Key.of("error", Metadata.ASCII_STRING_MARSHALLER);

    @GrpcExceptionHandler(RuntimeException.class)
    public StatusRuntimeException handleException(RuntimeException e) {
        e.printStackTrace();

        Status status = Status.UNKNOWN.withDescription(e.getMessage()).withCause(e);
        Metadata metadata = new Metadata();
        metadata.put(KEY_NAME, e.getMessage());
        return status.asRuntimeException(metadata);
    }

    @GrpcExceptionHandler(ResourceNotFoundException.class)
    public StatusRuntimeException handleResourceNotFoundException(ResourceNotFoundException e) {

        Status status = Status.NOT_FOUND.withDescription(e.getMessage()).withCause(e);
        Metadata metadata = new Metadata();
        metadata.put(KEY_NAME, e.getMessage());
        return status.asRuntimeException(metadata);
    }

    @GrpcExceptionHandler(ConstraintViolationException.class)
    public StatusRuntimeException handleConstraintViolationException(ConstraintViolationException e) {

        Status status = Status.INVALID_ARGUMENT.withDescription(e.getMessage()).withCause(e);
        Metadata metadata = new Metadata();
        metadata.put(KEY_NAME, e.getMessage());
        return status.asRuntimeException(metadata);
    }

    @GrpcExceptionHandler(DeleteInactivePartnerException.class)
    public StatusRuntimeException handleDeleteInactivePartnerException(DeleteInactivePartnerException e) {

        Status status = Status.FAILED_PRECONDITION.withDescription(e.getMessage()).withCause(e);
        Metadata metadata = new Metadata();
        metadata.put(KEY_NAME, e.getMessage());
        return status.asRuntimeException(metadata);
    }

    @GrpcExceptionHandler(ExistingActiveAVSNumberException.class)
    public StatusRuntimeException handleExistingActiveAVSNumberException(ExistingActiveAVSNumberException e) {

        Status status = Status.ALREADY_EXISTS.withDescription(e.getMessage()).withCause(e);
        Metadata metadata = new Metadata();
        metadata.put(KEY_NAME, e.getMessage());
        return status.asRuntimeException(metadata);
    }

    @GrpcExceptionHandler(ExistingActiveIDENumberException.class)
    public StatusRuntimeException handleExistingActiveIDENumberException(ExistingActiveIDENumberException e) {

        Status status = Status.ALREADY_EXISTS.withDescription(e.getMessage()).withCause(e);
        Metadata metadata = new Metadata();
        metadata.put(KEY_NAME, e.getMessage());
        return status.asRuntimeException(metadata);
    }

    @GrpcExceptionHandler(InvalidAVSNumberFormatException.class)
    public StatusRuntimeException handleInvalidAVSNumberFormatException(InvalidAVSNumberFormatException e) {

        Status status = Status.INVALID_ARGUMENT.withDescription(e.getMessage()).withCause(e);
        Metadata metadata = new Metadata();
        metadata.put(KEY_NAME, e.getMessage());
        return status.asRuntimeException(metadata);
    }

    @GrpcExceptionHandler(InvalidIDENumberFormatException.class)
    public StatusRuntimeException handleInvalidIDENumberFormatException(InvalidIDENumberFormatException e) {

        Status status = Status.INVALID_ARGUMENT.withDescription(e.getMessage()).withCause(e);
        Metadata metadata = new Metadata();
        metadata.put(KEY_NAME, e.getMessage());
        return status.asRuntimeException(metadata);
    }

    @GrpcExceptionHandler(DateNotInThePastException.class)
    public StatusRuntimeException handleDateNotInThePastException(DateNotInThePastException e) {

        Status status = Status.INVALID_ARGUMENT.withDescription(e.getMessage()).withCause(e);
        Metadata metadata = new Metadata();
        metadata.put(KEY_NAME, e.getMessage());
        return status.asRuntimeException(metadata);
    }

    @GrpcExceptionHandler(NullRequiredFieldException.class)
    public StatusRuntimeException handleNullRequiredFieldException(NullRequiredFieldException e) {

        Status status = Status.INVALID_ARGUMENT.withDescription(e.getMessage()).withCause(e);
        Metadata metadata = new Metadata();
        metadata.put(KEY_NAME, e.getMessage());
        return status.asRuntimeException(metadata);
    }

    @GrpcExceptionHandler(InvalidPhoneNumberFormatException.class)
    public StatusRuntimeException handleInvalidPhoneNumberFormatException(InvalidPhoneNumberFormatException e) {

        Status status = Status.INVALID_ARGUMENT.withDescription(e.getMessage()).withCause(e);
        Metadata metadata = new Metadata();
        metadata.put(KEY_NAME, e.getMessage());
        return status.asRuntimeException(metadata);
    }

    @GrpcExceptionHandler(OverlapPeriodException.class)
    public StatusRuntimeException handleOverlapPeriodException(OverlapPeriodException e) {

        Status status = Status.FAILED_PRECONDITION.withDescription(e.getMessage()).withCause(e);
        Metadata metadata = new Metadata();
        metadata.put(KEY_NAME, e.getMessage());
        return status.asRuntimeException(metadata);
    }

    @GrpcExceptionHandler(EndDateBeforeStartDateException.class)
    public StatusRuntimeException handleEndDateBeforeStartDateException(EndDateBeforeStartDateException e) {

        Status status = Status.INVALID_ARGUMENT.withDescription(e.getMessage()).withCause(e);
        Metadata metadata = new Metadata();
        metadata.put(KEY_NAME, e.getMessage());
        return status.asRuntimeException(metadata);
    }
}
