package entity

class Product {
     String id
     String name
     String description
     String price
    @Override
     String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", desciption='" + description + '\'' +
                ", price='" + price + '\'' +
                '}'
    }
}
