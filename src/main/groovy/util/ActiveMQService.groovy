package util

import constants.Constants
import org.apache.activemq.ActiveMQConnectionFactory

class ActiveMQService {

    ActiveMQConnectionFactory activemqConnect() {
        // Create a connection factory.
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory()
        // Pass the username and password.
        connectionFactory.setUserName(Constants.ACTIVE_MQ_USERNAME)
        connectionFactory.setPassword(Constants.ACTIVE_MQ_PASSWORD)
        connectionFactory.setBrokerURL(Constants.ACTIVE_MQ_URL)
        return connectionFactory
    }
}
