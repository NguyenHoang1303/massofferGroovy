package activemq

import constants.Constants
import org.apache.activemq.ActiveMQConnectionFactory
import org.bson.Document
import util.APIResponse
import util.ActiveMQService

import javax.jms.*

class Producer extends Thread {

    Document document
    Producer() {
    }


    Producer(Document document) {
        this.document = document
    }

    @Override
    void run() {
        sendMessage()
    }

    void sendMessage() {
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
            TextMessage producerMessage = producerSession.createTextMessage(document.toJson())
            // Send the message.
            producer.send(producerMessage)
            System.out.println("Message sent.")

            // Clean up the producer.
            producer.close()
            producerSession.close()
            producerConnection.close()
        } catch (JMSException e) {
            System.out.println("sent message fail")
            e.printStackTrace()
        }
    }

}
