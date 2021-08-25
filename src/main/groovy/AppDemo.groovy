import controller.ApiNew
import io.vertx.core.Vertx

class AppDemo {

    static void main(String[] args) {
        Vertx vertx = Vertx.vertx()
        vertx.deployVerticle(new ApiNew(), result -> {
            if (result.succeeded()) {
                println("success! ")
            } else {
                println("fail ${result.cause()}")
            }
        })
    }

}
