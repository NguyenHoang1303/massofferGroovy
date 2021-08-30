package controller

import com.google.gson.Gson
import entity.Product
import model.ProductModel
import org.bson.Document
import org.bson.types.ObjectId

class ProductController {
    ProductModel productModel

    ProductController() {
        productModel = new ProductModel()
    }

    Product save(Document document) {
        return productModel.save(document)
    }

    List<String> getAll() {
        List<Product> list = productModel.getAll()
        return list.collect{new Gson().toJson(it)}
    }

    Product update(Document updateDocument) {
        String databaseId = updateDocument.remove("_id") as String
        Document queryId = ["_id": new ObjectId(databaseId)]
        Product product = productModel.update(queryId, updateDocument)
        if (product == null) {
            return null
        }
        return product
    }

    Product delete(String id) {
        Product deleteDoc = productModel.delete(id)
        if (deleteDoc == null) {
            return null
        }
        return deleteDoc
    }

    Product findById(String id) {
        Product product = productModel.findById(id)
        if (product == null) {
            return null
        }
        return product
    }
}
