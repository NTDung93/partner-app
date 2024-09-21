package elca.ntig.partnerapp.be.model.exception;

import io.grpc.Metadata;
import io.grpc.StatusRuntimeException;
import io.grpc.Status;
import net.devh.boot.grpc.server.advice.GrpcAdvice;
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler;

import javax.validation.ConstraintViolationException;

@GrpcAdvice
public class GrpcExceptionAdvice {
    private static final Metadata.Key<String> KEY_NAME = Metadata.Key.of("key", Metadata.ASCII_STRING_MARSHALLER);

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

    @GrpcExceptionHandler(Exception.class)
    public StatusRuntimeException handleException(Exception e) {

        Status status = Status.UNKNOWN.withDescription(e.getMessage()).withCause(e);
        Metadata metadata = new Metadata();
        metadata.put(KEY_NAME, e.getMessage());
        return status.asRuntimeException(metadata);
    }
}
