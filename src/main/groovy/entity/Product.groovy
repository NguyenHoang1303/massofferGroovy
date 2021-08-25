package entity

class Product {
    private String id
    private String name
    private String desciption
    private String price

    Product() {
    }


    Product(String id, String name, String desciption, String price) {
        this.id = id
        this.name = name
        this.desciption = desciption
        this.price = price
    }

    String getId() {
        return id
    }

    void setId(String id) {
        this.id = id
    }

    String getName() {
        return name
    }

    void setName(String name) {
        this.name = name
    }

    String getDesciption() {
        return desciption
    }

    void setDesciption(String desciption) {
        this.desciption = desciption
    }

    String getPrice() {
        return price
    }

    void setPrice(String price) {
        this.price = price
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", desciption='" + desciption + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
