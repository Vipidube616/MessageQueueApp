package com.motadata.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.*;

public class Producer {
    private static final String BROKER_URL = "tcp://localhost:61616";
    private static final String QUEUE_NAME = "testQueue";

    public void sendMessages(int messageCount) {
        Connection connection = null;
        Session session = null;
        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(BROKER_URL);
            connection = connectionFactory.createConnection();
            connection.start();

            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Queue queue = session.createQueue(QUEUE_NAME);

            MessageProducer producer = session.createProducer(queue);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            for (int i = 1; i <= messageCount; i++) {
                TextMessage message = session.createTextMessage("Message #" + i);
                producer.send(message);
                System.out.println("Sent: " + message.getText());
            }
        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            try {
                if (session != null) session.close();
                if (connection != null) connection.close();
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
