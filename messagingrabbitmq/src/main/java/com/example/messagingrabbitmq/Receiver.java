package com.example.messagingrabbitmq;

import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class Receiver {

    private CountDownLatch countDownLatch = new CountDownLatch(1);

    public void receiveMessage(String message) {
        System.out.println("Received<" + message + ">");
        countDownLatch.countDown();
    }

    public CountDownLatch getLatch() {
        return countDownLatch;
    }
}
