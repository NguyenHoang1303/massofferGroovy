package controller

import activemq.Consumer
import activemq.Producer
import constants.Constants
import io.vertx.core.AbstractVerticle
import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.BodyHandler

class ApiNew extends AbstractVerticle {
    @Override
    void start() throws Exception {
        Router router = Router.router(vertx)
        ProductController productController = new ProductController()

        router.route().handler(BodyHandler.create()).produces(Constants.APPLICATION_JSON)
        router.post("/products/save").handler(productController::save)
        router.get("/products").handler(productController::getAll)
        router.put("/products/update").handler(productController::update)
        router.delete("/products/delete").handler(productController::delete)
        router.post("/message/enqueue").handler(context -> {
            Producer producer = new Producer()
            producer.sendMessage(context)
        })
        router.post("/message/dequeue").handler(context -> {
            Consumer consumer = new Consumer()
            consumer.receiveMessage(context)
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
