package controller

import activemq.ActiveMQ
import activemq.Consumer
import activemq.Producer
import com.google.gson.Gson
import constants.Constants
import entity.Product
import io.vertx.core.AbstractVerticle
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.BodyHandler
import org.bson.Document
import util.APIResponse


class RestApiProduct extends AbstractVerticle {
    @Override
    void start() throws Exception {
        Router router = Router.router(vertx)
        ProductController productController = new ProductController()
        APIResponse apiResponse = new APIResponse(Constants.STATUS_FAIL, Constants.MESSAGE_FAIL)
        router.route().handler(BodyHandler.create()).produces(Constants.APPLICATION_JSON)

        router.post("/products/save").handler(context -> {
            Document document = Document.parse(context.getBodyAsJson().encode())
            Product product = productController.save(document)
            if (product == null) apiResponse.handlerApiRespone(context)
            String result = new Gson().toJson(product)
            apiResponse.setResult(result)
            apiResponse.setStatusNumber(Constants.STATUS_OK)
            apiResponse.handlerApiRespone(context)
        })

        router.get("/products").handler(context -> {
            List<String> list = productController.getAll()
            if (list == null) apiResponse.handlerApiRespone(context)
            apiResponse.setResult(list.toString())
            apiResponse.setStatusNumber(Constants.STATUS_OK)
            apiResponse.handlerApiRespone(context)
        })

        router.get("/products/search").handler(context -> {
            String id = context.request().getParam("id")
            Product product = productController.findById(id)
            if (product == null) apiResponse.handlerApiRespone(context)
            apiResponse.setResult(new Gson().toJson(product))
            apiResponse.setStatusNumber(Constants.STATUS_OK)
            apiResponse.handlerApiRespone(context)

        })

        router.put("/products/update").handler(context -> {
            Document document = Document.parse(context.getBodyAsJson().toString())
            Product product = productController.update(document)
            if (product == null) apiResponse.handlerApiRespone(context)
            apiResponse.setResult(new Gson().toJson(product))
            apiResponse.setStatusNumber(Constants.STATUS_OK)
            apiResponse.handlerApiRespone(context)
        })

        router.delete("/products/delete").handler(context -> {
            String id = context.request().getParam("id")
            Product product = productController.delete(id)
            if (product == null) apiResponse.handlerApiRespone(context)
            apiResponse.setResult(new Gson().toJson(product))
            apiResponse.setStatusNumber(Constants.STATUS_OK)
            apiResponse.handlerApiRespone(context)
        })
        router.post("/message/enqueue").handler(context -> {
            Document document = Document.parse(context.getBodyAsJson().toString())
            Producer producer = new Producer(document)
            producer.start()
            apiResponse.setResult("Sent message success!!")
            apiResponse.setStatusNumber(Constants.STATUS_OK)
            apiResponse.handlerApiRespone(context)
        })
        router.get("/message/dequeue").handler(context -> {
            Consumer consumer = new Consumer()
            String message = consumer.receiveMessage()
            println("message ${message}")
        })

        vertx.createHttpServer().requestHandler(router)
                .listen(Constants.SERVER_PORT, { result ->
                    {
                        if (result.succeeded()) {
                            println("HTTP server running on port ${Constants.SERVER_PORT}")
                        }
                    }
                })
    }
}
