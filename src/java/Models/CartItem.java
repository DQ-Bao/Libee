package Models;

public class CartItem {
    private Cart cart;
    private Product product;
    private int quantity;
    private double purchasePrice;

    public CartItem() {
    }

    public CartItem(Cart cart, Product product, int quantity, double purchasePrice) {
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
        this.purchasePrice = purchasePrice;
    }

    public Cart getCart() {
        return cart;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
