package Models;

import Models.CartItem.CartItemBuilder;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;

public class Cart {
    private int id;
    private int userId;
    private LocalDateTime createdDate;
    private LocalDateTime saleDate;
    private List<CartItem> items;
    private double total;

    public Cart() {
    }
    
    public Cart(CartBuilder builder) {
        this.id = builder.id;
        this.userId = builder.userId;
        this.createdDate = builder.createdDate;
        this.saleDate = builder.saleDate;
        this.items = builder.items;
        this.total = builder.total;
    }
    
    public static CartBuilder getBuilder() {
        return new CartBuilder();
    }
    
    public static class CartBuilder {
        private int id;
        private int userId;
        private LocalDateTime createdDate;
        private LocalDateTime saleDate;
        private List<CartItem> items;
        private double total;

        private CartBuilder() {
            this.items = new ArrayList<>();
        }
        
        public CartBuilder Id(int id) { this.id = id; return this; }
        public CartBuilder UserId(int userId) { this.userId = userId; return this; }
        public CartBuilder CreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; return this; }
        public CartBuilder SaleDate(LocalDateTime saleDate) { this.saleDate = saleDate; return this; }
        public CartBuilder Item(CartItemBuilder builder) { this.items.add(builder.Build()); return this; }
        public CartBuilder Total(double total) { this.total = total; return this; }
        public Cart Build() {
            return new Cart(this);
        }
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getSaleDate() {
        return saleDate;
    }

    public List<CartItem> getItems() {
        return items;
    }
    
    public double getTotal() {
        return total;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUser(int userId) {
        this.userId = userId;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public void setSaleDate(LocalDateTime saleDate) {
        this.saleDate = saleDate;
    }
    
    public void setItems(List<CartItem> items) {
        this.items = items;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
