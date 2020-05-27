package com.java.grpc.client;

import com.proto.calculate.Calculate;
import com.proto.calculate.CalculateRequest;
import com.proto.calculate.CalculateResponse;
import com.proto.calculate.CalculatorServiceGrpc;
import com.proto.calculate.CalculatorServiceGrpc.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

public class CalculatorClient {
    public static void main(String[] args) {
        System.out.println("Client for Calculator Service");

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 50051)
                .usePlaintext()
                .build();

        CalculatorServiceBlockingStub calculatorClient = CalculatorServiceGrpc.newBlockingStub(channel);
        Calculate calculate = Calculate.newBuilder()
                .setFirstNumber(12)
                .setSecondNumber(10)
                .build();

        CalculateRequest request = CalculateRequest.newBuilder()
                .setCalculate(calculate)
                .build();

        CalculateResponse response = calculatorClient.calculate(request);
        System.out.println(response);

        System.out.println("Shutting down channel");
        channel.shutdown();
    }
}
