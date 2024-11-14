package server.earlycart.model;

public class Order {
    int id;
    String userId;
    int cartId;
    String name;
    int price;
    String image;
    int status;
    String date;

    public Order() {
    }

    public Order(int id, String userId, int cartId, String name, int price, String image, int status, String date) {
        this.id = id;
        this.userId = userId;
        this.cartId = cartId;
        this.name = name;
        this.price = price;
        this.image = image;
        this.status = status;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}