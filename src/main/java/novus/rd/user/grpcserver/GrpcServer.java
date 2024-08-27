package novus.rd.user.grpcserver;

import helloworld.GreeterGrpc;
import helloworld.HelloReply;
import helloworld.HelloRequest;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import novus.rd.user.controller.TestController;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class GrpcServer implements ApplicationRunner {

    private final int port = 9090;
    private Server server;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        start();
        System.out.println("Server started on port " + port);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.err.println("*** shutting down gRPC server since JVM is shutting down");
            GrpcServer.this.stop();
            System.err.println("*** server shut down");
        }));
    }

    private void start() throws Exception {
        server = ServerBuilder.forPort(port)
                .addService(new TestController())
                //.addService(new GreeterServiceImpl())
                .build()
                .start();
    }

    private void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    private static class GreeterServiceImpl extends GreeterGrpc.GreeterImplBase {

        @Override
        public void sayHello(HelloRequest req, StreamObserver<HelloReply> responseObserver) {
            String message = "Hello " + req.getName();
            HelloReply reply = HelloReply.newBuilder().setMessage(message).build();
            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        }
    }
}
