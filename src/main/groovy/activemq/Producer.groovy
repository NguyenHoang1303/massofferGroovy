package activemq

import constants.Constants
import io.vertx.ext.web.RoutingContext
import org.apache.activemq.ActiveMQConnectionFactory
import util.APIResponse
import util.ActiveMQService

import javax.jms.Connection
import javax.jms.DeliveryMode
import javax.jms.Destination
import javax.jms.JMSException
import javax.jms.MessageProducer
import javax.jms.Session
import javax.jms.TextMessage

class Producer {

    void sendMessage(RoutingContext routingContext) {
        // Establish a connection for the producer.
        ActiveMQService activeMQService = new ActiveMQService()
        ActiveMQConnectionFactory activeMQConnectionFactory = activeMQService.activemqConnect()
        APIResponse apiResponse = new APIResponse()
        try {
            Connection producerConnection = activeMQConnectionFactory.createConnection()
            producerConnection.start()
            // Create a session.
            Session producerSession = producerConnection.createSession(false, Session.AUTO_ACKNOWLEDGE)

            // Create a queue named "MyQueue".
            Destination producerDestination = producerSession.createQueue(Constants.ACTIVE_MQ_QUEUE)

            // Create a producer from the session to the queue.
            MessageProducer producer = producerSession.createProducer(producerDestination)
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT)

            // Create a message.
            String text = routingContext.getBody().toString()
            TextMessage producerMessage = producerSession.createTextMessage(text)

            // Send the message.
            producer.send(producerMessage)
            System.out.println("Message sent.")
            apiResponse.setResult("Sent message " + Constants.ACTIVE_MQ_QUEUE + "success!!")
            apiResponse.setStatusNumber(Constants.STATUS_OK)

            // Clean up the producer.
            producer.close()
            producerSession.close()
            producerConnection.close()
        } catch (JMSException e) {
            System.out.println("sent message fail")
            apiResponse.setResult(Constants.MESSAGE_FAIL)
            apiResponse.setStatusNumber(Constants.STATUS_FAIL)
            e.printStackTrace()
        }
        apiResponse.handlerApiRespone(routingContext)
    }
}
