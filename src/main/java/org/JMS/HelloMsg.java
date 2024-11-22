import javax.jms.*;
import org.apache.activemq.ActiveMQConnectionFactory;

public class HelloMsg {
    public static void main(String[] argv) throws Exception {
        // ActiveMQ connection factory
        ActiveMQConnectionFactory connFactory = new ActiveMQConnectionFactory("tcp://localhost:61616"); // Assuming ActiveMQ broker is running locally
        QueueConnection conn = connFactory.createQueueConnection();

        // This session is not transacted, and it uses automatic message acknowledgement
        QueueSession session = conn.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue q = session.createQueue("world");

        // Sender
        QueueSender sender = session.createSender(q);
        // Text message
        TextMessage msg = session.createTextMessage();
        msg.setText("Hello there!");
        System.out.println("Sending the message: " + msg.getText());
        sender.send(msg);

        // Receiver
        QueueReceiver receiver = session.createReceiver(q);
        conn.start();

        Message m = receiver.receive();
        if (m instanceof TextMessage) {
            TextMessage txt = (TextMessage) m;
            System.out.println("Message Received: " + txt.getText());
        }

        session.close();
        conn.close();
    }
}
