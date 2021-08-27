package activemq

import constants.Constants
import org.apache.activemq.ActiveMQConnectionFactory
import util.APIResponse
import util.ActiveMQService

import javax.jms.Connection
import javax.jms.Destination
import javax.jms.JMSException
import javax.jms.Message
import javax.jms.MessageConsumer
import javax.jms.Session
import javax.jms.TextMessage

class Consumer {
    String receiveMessage() {
        // Establish a connection for the consumer.
        // Note: Consumers should not use PooledConnectionFactory.
        String result = null
        ActiveMQService activeMQService = new ActiveMQService()
        ActiveMQConnectionFactory connectionFactory = activeMQService.activemqConnect()
        APIResponse apiResponse = new APIResponse()
        try {
            Connection consumerConnection = connectionFactory.createConnection()
            consumerConnection.start()

            // Create a session.
            Session consumerSession = consumerConnection.createSession(false, Session.AUTO_ACKNOWLEDGE)

            // Create a queue named "MyQueue".
            Destination consumerDestination = consumerSession.createQueue(Constants.ACTIVE_MY_QUEUE)

            // Create a message consumer from the session to the queue.
            MessageConsumer consumer = consumerSession.createConsumer(consumerDestination)

            // Begin to wait for messages.
            Message consumerMessage = consumer.receive(Constants.ACTIVE_MQ_TIME_OUT)

            // Receive the message when it arrives.
            TextMessage consumerTextMessage = (TextMessage) consumerMessage
            result = consumerTextMessage.getText()

            // Clean up the consumer.
            consumer.close()
            consumerSession.close()
            consumerConnection.close()
        } catch (JMSException e) {
            e.printStackTrace()
        }
        return result
    }
}
