package greet;

import com.proto.greet.GreetRequest;
import com.proto.greet.GreetResponse;
import com.proto.greet.GreetServiceGrpc;
import com.proto.greet.Greeting;
import io.grpc.stub.StreamObserver;

public class GreetServiceImpl extends GreetServiceGrpc.GreetServiceImplBase {
    @Override
    public void greet(GreetRequest request, StreamObserver<GreetResponse> responseObserver) {
        Greeting greeting = request.getGreeting();
        String firstName = greeting.getFirstName();

        String results = "Hello " + firstName;

        // create the response
        GreetResponse response = GreetResponse.newBuilder()
                .setResult(results)
                .build();

        // send the response
        responseObserver.onNext(response);

        //complete the rpc call
        responseObserver.onCompleted();
    }
}
