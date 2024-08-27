package novus.rd.user.controller;

import helloworld.GreeterGrpc;
import helloworld.HelloReply;
import helloworld.HelloRequest;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class TestController extends GreeterGrpc.GreeterImplBase{

    @Override
    public void sayHello(HelloRequest req, StreamObserver<HelloReply> responseObserver) {
        String message = "Hello2 " + req.getName();
        HelloReply reply = HelloReply.newBuilder().setMessage(message).build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
