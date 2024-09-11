package com.motadata.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.*;

public class Consumer {
    private static final String BROKER_URL = "tcp://localhost:61616";
    private static final String QUEUE_NAME = "testQueue";
    
    private int successCount = 0;
    private int errorCount = 0;

    public void consumeMessages() {
        Connection connection = null;
        Session session = null;
        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
            connection = connectionFactory.createConnection();
            connection.start();

            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue(QUEUE_NAME);

            MessageConsumer consumer = session.createConsumer(queue);

            consumer.setMessageListener(new MessageListener() {
                public void onMessage(Message message) {
                    try {
                        if (message instanceof TextMessage) {
                            String text = ((TextMessage) message).getText();
                            System.out.println("Received: " + text);
                            successCount++;
                        }
                    } catch (JMSException e) {
                        System.err.println("Error processing message: " + e.getMessage());
                        errorCount++;
                    }
                }
            });

            // Keep consumer alive for receiving messages
            Thread.sleep(10000);

        } catch (JMSException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                if (session != null) session.close();
                if (connection != null) connection.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }

        // Log the total number of successes and errors
        System.out.println("Total messages processed successfully: " + successCount);
        System.out.println("Total errors encountered: " + errorCount);
    }
}
