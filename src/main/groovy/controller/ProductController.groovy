package controller


import constants.Constants
import entity.Product
import io.vertx.core.json.JsonObject
import io.vertx.ext.web.RoutingContext
import model.ProductModel
import org.bson.Document
import org.bson.types.ObjectId
import util.APIResponse

class ProductController {

    void save(RoutingContext context) {
        APIResponse apiResponse = new APIResponse(Constants.STATUS_FAIL, Constants.MESSAGE_FAIL)
        JsonObject object = context.getBodyAsJson()
        Document document = Document.parse(object.encode())
        ProductModel productModel = new ProductModel()
        boolean checkSave = productModel.save(document)
        if (checkSave) {
            apiResponse.setResult(Constants.MESSAGE_SUCCESS)
            apiResponse.setStatusNumber(Constants.STATUS_OK)
        }
        apiResponse.handlerApiRespone(context)
    }

    void getAll(RoutingContext context) {
        APIResponse apiResponse = new APIResponse(Constants.STATUS_FAIL, Constants.MESSAGE_FAIL)
        ProductModel productModel = new ProductModel()
        List<Product> list = productModel.getAll()
        List<String> listResponse = new ArrayList<>()
        if (list != null) {
            for (item in list) {
                Document document = new Document()
                document.append("_id", item.id)
                        .append("name", item.name)
                        .append("description", item.desciption)
                        .append("price", item.price)
                listResponse.add(document.toJson())
            }
            apiResponse.setStatusNumber(Constants.STATUS_OK)
            apiResponse.setResult(listResponse.toString())
        }
        apiResponse.handlerApiRespone(context)
    }

    void update(RoutingContext context) {
        APIResponse apiResponse = new APIResponse(Constants.STATUS_FAIL, Constants.MESSAGE_FAIL)
        ProductModel productModel = new ProductModel()
        JsonObject object = context.getBodyAsJson()
        Document queryId = new Document()
        try {
            queryId.append("_id", new ObjectId(object.getString("_id")))
        }catch(IllegalArgumentException argumentException){
            println("Fail: ${argumentException}")
        }
        Document updateDoc = new Document()
        Set<String> listKey = object.fieldNames();
        for (key in listKey) {
            if (key == "_id") continue
            updateDoc.append(key, object.getString(key))
        }
       boolean checkUpdate = productModel.update(queryId,updateDoc)
        if (checkUpdate){
            apiResponse.setResult(Constants.MESSAGE_SUCCESS)
            apiResponse.setStatusNumber(Constants.STATUS_OK)
        }
        apiResponse.handlerApiRespone(context)
    }

    void delete(RoutingContext context) {
        APIResponse apiResponse = new APIResponse(Constants.STATUS_FAIL, Constants.MESSAGE_FAIL)
        JsonObject object = context.getBodyAsJson()
        String id = object.getString("_id")
        Document document = new Document()
        document.append("_id", new ObjectId(id))
        ProductModel productModel = new ProductModel()
        boolean checkDelete = productModel.delete(document)
        if (checkDelete) {
            apiResponse.setResult(Constants.MESSAGE_SUCCESS)
            apiResponse.setStatusNumber(Constants.STATUS_OK)
        }
        apiResponse.handlerApiRespone(context)
    }
}
