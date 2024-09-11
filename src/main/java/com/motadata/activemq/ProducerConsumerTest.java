package com.motadata.activemq;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ProducerConsumerTest {

    private Producer producer;
    private Consumer consumer;

    @Before
    public void setUp() {
        producer = new Producer();
        consumer = new Consumer();
    }

    @Test
    public void testSuccessfulMessageProcessing() throws Exception {
        // Send messages
        producer.sendMessages(5);

        // Consume messages
        consumer.consumeMessages();

        // Check the logs manually to verify that 5 messages were processed successfully
    }

    @Test
    public void testErrorInMessageProcessing() {
        // Introduce an error in the consumer (simulate by modifying consumer logic)
        // For this example, the consumer processes successfully; you could modify
        // the code to simulate an error for this test case.

        // Consume messages and verify errors
        consumer.consumeMessages();
        
        // Expect some errors to occur
    }
}
