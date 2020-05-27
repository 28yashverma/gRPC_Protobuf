package com.proto.examples;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.util.JsonFormat;
import example.simple.Simple;

import java.util.Arrays;

public class ProtoToJsonMain {

    public static void main(String[] args) throws InvalidProtocolBufferException {
        Simple.SimpleMessage.Builder builder = Simple.SimpleMessage.newBuilder();

        // Simple fields
        builder.setId(42).setName("Yeshendra").setIsSimple(true);

        // Repeated Fields
        builder.addSampleList(1);
        builder.addSampleList(2);
        builder.addSampleList(3);

        builder.addAllSampleList(Arrays.asList(4, 5, 6));

        //Print this as JSON
        String jsonString = JsonFormat.printer().print(builder);
        System.out.println(jsonString);

        //calls for repeated can also be chained
        builder.addSampleList(7).addSampleList(8).addAllSampleList(Arrays.asList(9, 10, 11)).setSampleList(9, 32);

        System.out.print(builder.toString());
    }

}
