package model

import com.mongodb.client.FindIterable
import com.mongodb.client.MongoCollection
import com.mongodb.client.model.FindOneAndUpdateOptions
import com.mongodb.client.model.ReturnDocument
import entity.Product
import org.bson.Document
import org.bson.types.ObjectId
import util.Convert
import util.MongoService

class ProductModel {
    Convert convert = new Convert()
    MongoService mongoService = new MongoService()

    Product save(Document document) {
        MongoCollection<Document> collection = mongoService.mongoConnect()
        collection.insertOne(document)
        String id = document.getObjectId("_id").toHexString()
        Document queryId = ["_id": new ObjectId(id)]
        Document docSave = collection.find(queryId).first()
        return convert.handlerDocToProduct(docSave)
    }

    List<Product> getAll() {
        MongoCollection<Document> collection = mongoService.mongoConnect()
        List<Document> listDoc = new ArrayList<>()
        collection.find().into(listDoc)
        List<Product> productList = listDoc.collect { convert.handlerDocToProduct(it) }

        return productList
    }

    Product update(Document queryById, Document document) {
        MongoCollection<Document> collection = mongoService.mongoConnect()
        Document update = new Document('$set', document)
        Document result = collection.findOneAndUpdate(queryById, update,
                new FindOneAndUpdateOptions().returnDocument(ReturnDocument.AFTER))
        return convert.handlerDocToProduct(result)
    }

    Product delete(String id) {
        MongoCollection<Document> collection = mongoService.mongoConnect()
        Document result = collection.findOneAndDelete(new Document("_id", new ObjectId(id)))
        return convert.handlerDocToProduct(result)
    }

    Product findById(String id) {
        MongoCollection<Document> collection = mongoService.mongoConnect()
        Document document = collection.find(new Document("_id", new ObjectId(id))).first()
        return convert.handlerDocToProduct(document)
    }
}
