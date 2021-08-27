package util

import entity.Product
import org.bson.Document

class Convert {

     Product handlerDocToProduct(Document document){
        String id = document.getObjectId("_id").toHexString()
        String name = document.get("name")
        String description = document.get("description")
        String price = document.get("price")
        return new Product( id:id, name:name, description:description, price:price)
    }
}
