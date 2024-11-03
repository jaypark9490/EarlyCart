package server.earlycart.model;

public class Order {
    int id;
    String userId;
    int cartId;
    int price;
    int status;

    public Order() {
    }

    public Order(int id, String userId, int cartId, int price, int status) {
        this.id = id;
        this.userId = userId;
        this.cartId = cartId;
        this.price = price;
        this.status = status;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
