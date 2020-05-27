package com.java.grpc.client;

import com.proto.dummy.DummyServiceGrpc;
import com.proto.greet.*;
import com.proto.greet.GreetServiceGrpc.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class GreetingCLient {
    public static void main(String[] args) {
        System.out.println("Hello I am GRPC Client");

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext() //Force SSL to deactivate
                .build();

        // For Synchronous client
        // DummyServiceGrpc.DummyServiceBlockingStub syncClient = DummyServiceGrpc.newBlockingStub(channel);

        // for aynchronous Client
        //DummyServiceGrpc.DummyServiceFutureStub asyncClient = DummyServiceGrpc.newFutureStub(channel);

        GreetServiceBlockingStub greetClient = GreetServiceGrpc.newBlockingStub(channel);

        // Unary call
//        Greeting greeting = Greeting.newBuilder()
//                .setFirstName("Yeshendra")
//                .setLastName("Verma")
//                .build();
//
//        GreetRequest request = GreetRequest.newBuilder()
//                .setGreeting(greeting)
//                .build();
//        GreetResponse response = greetClient.greet(request);
//        System.out.println(response.getResult());

        // Server streaming call
        // we prepare the request here
        GreetManyTimesRequest greetManyTimesRequest = GreetManyTimesRequest
                .newBuilder()
                .setGreeting(Greeting.newBuilder().setFirstName("Yeshendra"))
                .build();

        //we stream the result
        greetClient.greetManyTimes(greetManyTimesRequest).forEachRemaining(response -> {
            System.out.println(response.getResult());
        });

        System.out.println("Shutting down channel");
        channel.shutdown();
    }
}
