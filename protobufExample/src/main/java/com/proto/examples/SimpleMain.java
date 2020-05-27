package com.proto.examples;

import com.example.Complex.ComplexOuterClass.*;
import example.simple.Simple.SimpleMessage;

import java.util.Arrays;

public class SimpleMain {
    public static void main(String[] args) {
        System.out.println("Hello world");

        SimpleMessage.Builder builder = SimpleMessage.newBuilder();

        // Simple fields
        builder.setId(42).setName("Yeshendra").setIsSimple(true);

        // Repeated Fields
        builder.addSampleList(1);
        builder.addSampleList(2);
        builder.addSampleList(3);

        builder.addAllSampleList(Arrays.asList(4, 5, 6));

        //calls for repeated can also be chained
        builder.addSampleList(7).addSampleList(8).addAllSampleList(Arrays.asList(9, 10, 11)).setSampleList(9, 32);

        System.out.print(builder.toString());

        SimpleMessage message = builder.build();
        message.getId();

        NestedComplex.Builder nestedBuilder = NestedComplex.newBuilder();
        NestedComplex nestedComplex = nestedBuilder.setId(55).setName("Nested Builder").build();

        System.out.println("Nested Complex : "+nestedComplex.toString());

        Complex.Builder complexBuilder = Complex.newBuilder();
        complexBuilder.setOneDummy(nestedBuilder);

        complexBuilder.addMultipleDummies(nestedComplex);

    }
}
