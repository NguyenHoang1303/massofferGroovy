package model


import com.mongodb.MongoException
import com.mongodb.client.FindIterable
import com.mongodb.client.MongoCollection
import com.mongodb.client.result.DeleteResult
import com.mongodb.client.result.UpdateResult
import entity.Product
import org.bson.Document
import util.MongoService

class ProductModel {

    boolean save(Document document) {
        MongoService mongoService = new MongoService()
        MongoCollection<Document> collection = mongoService.mongoConnect()
        if (collection == null) {
            return false
        }
        if (document == null) {
            return false
        }
        try {
            collection.insertOne(document)
            return true
        } catch (NullPointerException ignored) {
            println("unable to connect to MongoDB ${collection.namespace}, ${ignored}")
        }
        return false
    }

    List<Product> getAll() {
        MongoService mongoService = new MongoService()
        MongoCollection<Document> collection = mongoService.mongoConnect()
        if (collection == null) {
            return null
        }
        List<Product> list = new ArrayList<>()
        FindIterable<Document> result = collection.find()
        Iterator iterator = result.iterator()
        while (iterator.hasNext()){
            Document item = iterator.next()
            String id = item.get("_id").toString()
            String name = item.get("name")
            String description = item.get("description")
            String price = item.get("price")
            Product product = new Product(id, name, description, price)
            list.add(product)
        }
        return list
    }

    boolean update(Document queryById, Document document) {
        MongoService mongoService = new MongoService()
        MongoCollection<Document> collection = mongoService.mongoConnect()
        if (collection == null) return false
        Document update = new Document()
        update.append("\$set", document)
        UpdateResult result = collection.updateOne(queryById, update)
        if (result.getModifiedCount() > 0) return true
        return false
    }

    boolean delete(Document document) {
        MongoService mongoService = new MongoService()
        MongoCollection<Document> collection = mongoService.mongoConnect()
        if (collection == null) return false
        try {
            DeleteResult result = collection.deleteOne(document)
            if (result.getDeletedCount() > 0) return true
        } catch (MongoException me) {
            println("Unable to delete due to an error: " + me)
        }
        return false
    }
}
