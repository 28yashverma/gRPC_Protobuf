package greet;

import com.proto.greet.*;
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

    @Override
    public void greetManyTimes(GreetManyTimesRequest request, StreamObserver<GreetManyTimesResponse> responseObserver) {
        String firstName = request.getGreeting().getFirstName();

        for (int i = 0; i < 10; i++) {
            String result = "Hello " + firstName + " response number: " +i;
            GreetManyTimesResponse response = GreetManyTimesResponse
                    .newBuilder()
                    .setResult(result)
                    .build();
            responseObserver.onNext(response);
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            } finally {

            }
        }
        responseObserver.onCompleted();
    }
}
