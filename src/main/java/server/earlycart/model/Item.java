package server.earlycart.model;

public class Item {
    int id;
    String category;
    String name;
    int price;
    int stock;
    String image;

    public Item() {
    }

    public Item(int id, String category, String name, int price, int stock, String image) {
        this.id = id;
        this.category = category;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
