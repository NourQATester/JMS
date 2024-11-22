package org.JMS;

import javax.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;

public class Main {

    public static void main(String[] args) {
        // URL of the JMS server (ActiveMQ in this case)
        String brokerURL = "tcp://localhost:61616"; // Change if needed
        String queueName = "exampleQueue"; // Name of the JMS queue

        try {
            // Create a connection factory
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);

            // Create a connection
            Connection connection = connectionFactory.createConnection();
            connection.start();

            // Create a session (with auto acknowledgment)
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create the destination (queue)
            Destination queue = session.createQueue(queueName);

            // Create a message producer (for sending messages)
            MessageProducer producer = session.createProducer(queue);

            // Create and send a text message
            TextMessage message = session.createTextMessage("Hello, this is a test message!");
            producer.send(message);
            System.out.println("Sent message: " + message.getText());

            // Create a message consumer (for receiving messages)
            MessageConsumer consumer = session.createConsumer(queue);

            // Receive the message (this will block until a message is received)
            Message receivedMessage = consumer.receive(1000); // Wait for up to 1 second

            // Check if a message is received
            if (receivedMessage instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) receivedMessage;
                System.out.println("Received message: " + textMessage.getText());
            }

            // Clean up
            producer.close();
            consumer.close();
            session.close();
            connection.close();

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
