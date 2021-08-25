package util

import constants.Constants
import io.vertx.ext.web.RoutingContext

class APIResponse {
    int statusNumber
    String result

    void handlerApiRespone(RoutingContext context){
        context.response().putHeader(Constants.CONTENT_TYPE, Constants.APPLICATION_JSON)
                .setStatusCode(this.statusNumber)
                .end(this.result)
    }

    APIResponse() {
    }

    APIResponse(int statusNumber, String result) {
        this.statusNumber = statusNumber
        this.result = result
    }

    def getStatusNumber() {
        return statusNumber
    }

    void setStatusNumber(statusNumber) {
        this.statusNumber = statusNumber
    }

    def getResult() {
        return result
    }

    void setResult(result) {
        this.result = result
    }
}
