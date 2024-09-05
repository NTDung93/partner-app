package elca.ntig.partnerapp.be.controller;

import elca.ntig.partnerapp.common.proto.GreetingRequest;
import elca.ntig.partnerapp.common.proto.GreetingResponse;
import elca.ntig.partnerapp.common.proto.GreetingServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class GreetingServiceGrpcImpl extends GreetingServiceGrpc.GreetingServiceImplBase {

    @Override
    public void greeting(GreetingRequest request, StreamObserver<GreetingResponse> responseObserver) {
        String message = request.getMessage();
        System.out.println("Received message: " + message);

        GreetingResponse greetingResponse = GreetingResponse.newBuilder()
                .setMessage("Received your " + message + ". Hello from server. ")
                .build();

        responseObserver.onNext(greetingResponse);
        responseObserver.onCompleted();
    }
}
