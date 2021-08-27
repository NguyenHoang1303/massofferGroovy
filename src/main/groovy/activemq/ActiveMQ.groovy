package activemq

class ActiveMQ {
    
    void handlerAMQ(String text){
        Producer producer = new Producer()
        Consumer consumer = new Consumer()
        producer.sendMessage(text)
        consumer.receiveMessage()
    }
}
