package model


import com.mongodb.client.MongoCollection
import org.bson.Document
import org.bson.types.ObjectId
import util.MongoService

class ProductModel {

    Document save(Document document) {
        MongoService mongoService = new MongoService()
        MongoCollection<Document> collection = mongoService.mongoConnect()
        collection.insertOne(document)
        String id = document.getObjectId("_id").toHexString()
        Document queryId = new Document("_id", new ObjectId(id))
        Document currentSave = collection.find(queryId).first()
        return currentSave
    }

    List<Document> getAll() {
        MongoService mongoService = new MongoService()
        MongoCollection<Document> collection = mongoService.mongoConnect()
        List<Document> listDoc = collection.find().collect()
        return listDoc
    }

    Document update(Document queryById, Document document) {
        MongoService mongoService = new MongoService()
        MongoCollection<Document> collection = mongoService.mongoConnect()
        Document update = new Document()
        update.append("\$set", document)
        Document result = collection.findOneAndUpdate(queryById, update)
        return result
    }

    Document delete(String id) {
        Document document = new Document()
        document.append("_id", new ObjectId(id))
        MongoService mongoService = new MongoService()
        MongoCollection<Document> collection = mongoService.mongoConnect()
        Document result = collection.findOneAndDelete(document)
        return result
    }

    Document findById(String id) {
        Document queryId = new Document("_id", new ObjectId(id))
        MongoService mongoService = new MongoService()
        MongoCollection<Document> collection = mongoService.mongoConnect()
        Document document = collection.find(queryId).first()
        return document
    }
}
