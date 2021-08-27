package controller

import com.google.gson.Gson
import entity.Product
import model.ProductModel
import org.bson.Document
import org.bson.types.ObjectId
import util.Convert

class ProductController {

    Product save(Document document) {
        ProductModel productModel = new ProductModel()
        Document documentSave = productModel.save(document)
        Convert convert = new Convert()
        Product result = convert.handlerDocToProduct(documentSave)
        return result
    }

    List<String> getAll() {
        ProductModel productModel = new ProductModel()
        List<Document> list = productModel.getAll()
        List<String> listResponse = new ArrayList<>()
        Convert convert = new Convert()
        for (item in list) {
            Product product = convert.handlerDocToProduct(item)
            String result = new Gson().toJson(product)
            listResponse.add(result)
        }
        return listResponse
    }

    Product update(Document document) {
        Document queryId = new Document()
        Document updateDoc = new Document()
        for (key in document.keySet()) {
            if (key == "_id") {
                ObjectId objectId = new ObjectId(document.getString(key))
                queryId.append("_id", objectId)
            }else {
                updateDoc.append(key, document.getString(key))
            }
        }
        ProductModel productModel = new ProductModel()
        Document updateSuccess = productModel.update(queryId, updateDoc)
        if (updateSuccess == null) return null
        Convert convert = new Convert()
        return convert.handlerDocToProduct(updateSuccess)
    }

    Product delete(String id) {
        ProductModel productModel = new ProductModel()
        Document deleteDoc = productModel.delete(id)
        if (deleteDoc == null) return null
        Convert convert = new Convert()
        return convert.handlerDocToProduct(deleteDoc)
    }

    Product findById(String id) {
        ProductModel productModel = new ProductModel()
        Document document = productModel.findById(id)
        println(document)
        Convert convert = new Convert()
        if (document == null) return null
        return convert.handlerDocToProduct(document)
    }
}
