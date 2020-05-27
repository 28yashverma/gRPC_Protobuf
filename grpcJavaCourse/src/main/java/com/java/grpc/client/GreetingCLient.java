package com.java.grpc.client;

import com.proto.dummy.DummyServiceGrpc;
import com.proto.greet.*;
import com.proto.greet.GreetServiceGrpc.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class GreetingCLient {

    ManagedChannel channel;

    public static void main(String[] args) {
        System.out.println("Hello I am GRPC Client");

        GreetingCLient main = new GreetingCLient();
        main.run();
    }

    private void run(){
        channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext() //Force SSL to deactivate
                .build();

        // unary call
        //unaryCall(channel);

        // Server Streaming Call
        //serverStreamingCall(channel);

        // Client Streaming Call
        clientStreamingCall(channel);

        System.out.println("Shutting down channel");
        channel.shutdown();
    }

    private void unaryCall(ManagedChannel channel) {
        DummyServiceGrpc.DummyServiceBlockingStub syncClient = DummyServiceGrpc.newBlockingStub(channel);

        // for aynchronous Client
        //DummyServiceGrpc.DummyServiceFutureStub asyncClient = DummyServiceGrpc.newFutureStub(channel);

        GreetServiceBlockingStub greetClient = GreetServiceGrpc.newBlockingStub(channel);

        // Unary call
        Greeting greeting = Greeting.newBuilder()
                .setFirstName("Yeshendra")
                .setLastName("Verma")
                .build();

        GreetRequest request = GreetRequest.newBuilder()
                .setGreeting(greeting)
                .build();
        GreetResponse response = greetClient.greet(request);
        System.out.println(response.getResult());
    }


    private void serverStreamingCall(ManagedChannel channel) {
        GreetServiceBlockingStub greetClient = GreetServiceGrpc.newBlockingStub(channel);

        GreetManyTimesRequest greetManyTimesRequest = GreetManyTimesRequest
                .newBuilder()
                .setGreeting(Greeting.newBuilder().setFirstName("Yeshendra"))
                .build();

        //we stream the result
        greetClient.greetManyTimes(greetManyTimesRequest).forEachRemaining(response -> {
            System.out.println(response.getResult());
        });
    }

    private void clientStreamingCall(ManagedChannel channel) {
        // asynchronous client
        GreetServiceStub asyncClient = GreetServiceGrpc.newStub(channel);

        // For asynchronous programming one need to have a latch
        CountDownLatch latch = new CountDownLatch(1);

        StreamObserver<LongGreetRequest> requestObserver = asyncClient.longGreet(new StreamObserver<LongGreetResponse>() {
            @Override
            public void onNext(LongGreetResponse value) {
                // we get response from the server
                System.out.println("Received a response from the server");
                System.out.println(value.getResult());
                // on next will be called only once because of Client Streaming
            }

            @Override
            public void onError(Throwable t) {
                // error from server
                System.out.println("Error from server : " + t.getLocalizedMessage());
            }

            @Override
            public void onCompleted() {
                // server done sending data
                System.out.println("Server has completed sending us response");
                // on completed will be called right after onNext
                // Whenever server is done sending data latch is going down by 1
                latch.countDown();
            }
        });

        // streaming message #1
        System.out.println("Sending message 1");
        requestObserver.onNext(LongGreetRequest.newBuilder()
                .setGreeting(Greeting.newBuilder()
                .setFirstName("Yeshendra"))
                .build());

        // streaming message #2
        System.out.println("Sending message 2");
        requestObserver.onNext(LongGreetRequest.newBuilder()
                .setGreeting(Greeting.newBuilder()
                        .setFirstName("Manish"))
                .build());

        // streaming message #3
        System.out.println("Sending message 3");
        requestObserver.onNext(LongGreetRequest.newBuilder()
                .setGreeting(Greeting.newBuilder()
                        .setFirstName("Prabhjeet"))
                .build());

        //we tell the server the client is done sending the data
        requestObserver.onCompleted();

        try {
            latch.await(1, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
