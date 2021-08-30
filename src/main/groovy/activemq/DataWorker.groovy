package activemq

import com.google.gson.Gson
import constants.Constants
import controller.ProductController
import org.apache.activemq.ActiveMQConnectionFactory
import org.bson.Document
import util.ActiveMQService

import javax.jms.Connection
import javax.jms.Destination
import javax.jms.JMSException
import javax.jms.Message
import javax.jms.MessageConsumer
import javax.jms.Session
import javax.jms.TextMessage

class DataWorker extends Thread {

    ProductController productController
    String message
    String result


    DataWorker() {
        productController = new ProductController()

    }

    @Override
    void run() {
        receiveMessage()
    }

    void handlerDataAMQ(String data) {
        Document document = Document.parse(data)
        String action = document.remove("action")
        println(data)
        switch (action) {
            case Constants.MONGO_SAVE:
                result = new Gson().toJson(productController.save(document))
                break
            case Constants.MONGO_UPDATE:
                result = new Gson().toJson(productController.update(document))
                break
            case Constants.MONGO_DELETE:
                String id = document.getString("_id")
                result = new Gson().toJson(productController.delete(id))
                break
        }
    }

    void receiveMessage() {
        // Establish a connection for the consumer.
        // Note: Consumers should not use PooledConnectionFactory.
        ActiveMQService activeMQService = new ActiveMQService()
        ActiveMQConnectionFactory connectionFactory = activeMQService.activemqConnect()
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

            while (true) {
                Message consumerMessage = consumer.receive()
                // Receive the message when it arrives.
                TextMessage consumerTextMessage = (TextMessage) consumerMessage
                if (consumerTextMessage == null) {
                    continue
                }
                message = consumerTextMessage.getText()
                handlerDataAMQ(message)
            }
            // Clean up the consumer.
        } catch (JMSException e) {
            e.printStackTrace()
        }
    }
}
