package activemq

import constants.Constants
import org.apache.activemq.ActiveMQConnectionFactory
import util.ActiveMQService

import javax.jms.*

class Producer {

    boolean sendMessage(String text) {
        // Establish a connection for the producer.
        ActiveMQService activeMQService = new ActiveMQService()
        ActiveMQConnectionFactory activeMQConnectionFactory = activeMQService.activemqConnect()
        try {
            Connection producerConnection = activeMQConnectionFactory.createConnection()
            producerConnection.start()
            // Create a session.
            Session producerSession = producerConnection.createSession(false, Session.AUTO_ACKNOWLEDGE)

            // Create a queue named "MyQueue".
            Destination producerDestination = producerSession.createQueue(Constants.ACTIVE_MY_QUEUE)

            // Create a producer from the session to the queue.
            MessageProducer producer = producerSession.createProducer(producerDestination)
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT)

            // Create a message.
            TextMessage producerMessage = producerSession.createTextMessage(text)

            // Send the message.
            producer.send(producerMessage)
            System.out.println("Message sent.")
            // Clean up the producer.
            producer.close()
            producerSession.close()
            producerConnection.close()
            return true
        } catch (JMSException e) {
            System.out.println("sent message fail")
            e.printStackTrace()
        }
        return false
    }
}
