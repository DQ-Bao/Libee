package Models;

import Models.Product.ProductBuilder;

public class CartItem {
    private int cartId;
    private Product product;
    private int quantity;
    private double purchasePrice;

    public CartItem() {
    }

    public CartItem(int cartId, Product product, int quantity, double purchasePrice) {
        this.cartId = cartId;
        this.product = product;
        this.quantity = quantity;
        this.purchasePrice = purchasePrice;
    }
    
    public CartItem(CartItemBuilder builder) {
        this.cartId = builder.cartId;
        this.product = builder.product;
        this.quantity = builder.quantity;
        this.purchasePrice = builder.purchasePrice;
    }
    
    public static CartItemBuilder getBuilder() {
        return new CartItemBuilder();
    }
    
    public static class CartItemBuilder {
        private int cartId;
        private Product product;
        private int quantity;
        private double purchasePrice;

        private CartItemBuilder() {
        }
        
        public CartItemBuilder CartId(int cartId) { this.cartId = cartId; return this; }
        public CartItemBuilder Product(ProductBuilder builder) { this.product = builder.Build(); return this; }
        public CartItemBuilder Quantity(int quantity) { this.quantity = quantity; return this; }
        public CartItemBuilder PurchasePrice(double purchasePrice) { this.purchasePrice = purchasePrice; return this; }
        public CartItem Build() {
            return new CartItem(this);
        }
    }

    public int getCartId() {
        return cartId;
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

    public void setCartId(int cartId) {
        this.cartId = cartId;
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
