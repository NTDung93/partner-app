package elca.ntig.partnerapp.be.model.exception;

import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.server.advice.GrpcAdvice;
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler;

import javax.validation.ConstraintViolationException;

@GrpcAdvice
public class GrpcExceptionAdvice {
    @GrpcExceptionHandler(ResourceNotFoundException.class)
    public StatusRuntimeException handleResourceNotFoundException(ResourceNotFoundException e) {
        Status status = Status.NOT_FOUND.withDescription(e.getMessage()).withCause(e);
        Metadata metadata = new Metadata();
        Metadata.Key<String> key = Metadata.Key.of("key", Metadata.ASCII_STRING_MARSHALLER);
        metadata.put(key, e.getMessage());
        return status.asRuntimeException(metadata);
    }

    @GrpcExceptionHandler(ConstraintViolationException.class)
    public StatusRuntimeException handleConstraintViolationException(ConstraintViolationException e) {

        Status status = Status.INVALID_ARGUMENT.withDescription(e.getMessage()).withCause(e);
        Metadata metadata = new Metadata();
        Metadata.Key<String> key = Metadata.Key.of("key", Metadata.ASCII_STRING_MARSHALLER);
        metadata.put(key, e.getMessage());
        return status.asRuntimeException(metadata);
    }
}
