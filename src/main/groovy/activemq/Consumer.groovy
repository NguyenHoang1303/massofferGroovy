package activemq

import constants.Constants
import io.vertx.ext.web.RoutingContext
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
    void receiveMessage(RoutingContext routingContext) {
        // Establish a connection for the consumer.
        // Note: Consumers should not use PooledConnectionFactory.
        ActiveMQService activeMQService = new ActiveMQService()
        ActiveMQConnectionFactory connectionFactory = activeMQService.activemqConnect()
        APIResponse apiResponse = new APIResponse()
        try {
            Connection consumerConnection = connectionFactory.createConnection()
            consumerConnection.start()

            // Create a session.
            Session consumerSession = consumerConnection.createSession(false, Session.AUTO_ACKNOWLEDGE)

            // Create a queue named "MyQueue".
            Destination consumerDestination = consumerSession.createQueue(Constants.ACTIVE_MQ_QUEUE)

            // Create a message consumer from the session to the queue.
            MessageConsumer consumer = consumerSession.createConsumer(consumerDestination)

            // Begin to wait for messages.
            Message consumerMessage = consumer.receive(Constants.ACTIVE_MQ_TIME_OUT)

            // Receive the message when it arrives.
            TextMessage consumerTextMessage = (TextMessage) consumerMessage
            System.out.println("Message received: " + consumerTextMessage.getText())
            apiResponse.setResult(consumerTextMessage.getText())
            apiResponse.setStatusNumber(Constants.STATUS_OK)

            // Clean up the consumer.
            consumer.close()
            consumerSession.close()
            consumerConnection.close()
        } catch (JMSException e) {
            apiResponse.setResult(Constants.MESSAGE_FAIL)
            apiResponse.setStatusNumber(Constants.STATUS_FAIL)
            e.printStackTrace()
        }
        apiResponse.handlerApiRespone(routingContext)
    }
}
