import activemq.DataWorker
import controller.RestApiProduct
import io.vertx.core.Vertx

class AppDemo {

    static void main(String[] args) {
        Vertx vertx = Vertx.vertx()
        vertx.deployVerticle(new RestApiProduct(), result -> {
            if (result.succeeded()) {
                println("success! ")
            } else {
                println("fail ${result.cause()}")
            }
        })
        DataWorker dataWorker = new DataWorker()
        dataWorker.start()
    }

}
