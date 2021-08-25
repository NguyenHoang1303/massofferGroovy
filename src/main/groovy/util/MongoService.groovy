package util

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import constants.Constants
import org.bson.Document

class MongoService {
    private MongoClient mongoClient
    def URI_CONNECT = Constants.URI_MONGODB
    def DATABASE = Constants.DB_NAME
    def COLLECTION = Constants.COLLECTION_NAME

    MongoCollection<Document> mongoConnect() {
        mongoClient = mongoClient ?: MongoClients.create(URI_CONNECT)
        MongoDatabase mongoDatabase = mongoClient.getDatabase(DATABASE)
        return mongoDatabase.getCollection(COLLECTION)
    }
}
