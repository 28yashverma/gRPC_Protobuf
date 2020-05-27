package com.java.grpc.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

public class CalculatorServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        Server server = ServerBuilder.forPort(50051)
                .build();

        System.out.println("Server start");
        server.start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.print("Receive shutdown request");
            server.shutdown();
            System.out.println("Stopped the server");
        }));

        server.awaitTermination();
    }
}
