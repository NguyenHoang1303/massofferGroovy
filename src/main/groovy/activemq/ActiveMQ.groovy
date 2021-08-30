package activemq

import com.google.gson.Gson
import constants.Constants
import controller.ProductController
import org.bson.Document
import util.APIResponse

class ActiveMQ extends Thread {

    ProductController productController
    String message
    String result


    ActiveMQ() {
        productController = new ProductController()
    }

    ActiveMQ(String message) {
        this.message = message
    }

    @Override
    void run() {
        Consumer consumer  = new Consumer()
        while (true){
            message = consumer.receiveMessage()
            println("message ${message}")
//            handlerAMQ(message)
        }
    }

    void handlerAMQ(String data) {
        if (data == null){
            println("doi cho")
            wait()
        }
        Document document = Document.parse(data)
        String field = document.remove("field")
        switch (field) {
            case "save":
                result = new Gson().toJson(productController.save(document))
                break
            case "update":
                result = new Gson().toJson(productController.update(document))
                break
            case "delete":
                String id = document.getString("_id")
                result = new Gson().toJson(productController.delete(id))
                break
        }
    }
}
