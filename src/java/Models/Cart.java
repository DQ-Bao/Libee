package Models;

import java.time.LocalDateTime;

public class Cart {
    private int id;
    private User user;
    private LocalDateTime createdDate;
    private LocalDateTime saleDate;
    private double total;

    public Cart() {
    }

    public Cart(int id, User user, LocalDateTime createdDate, LocalDateTime saleDate, double total) {
        this.id = id;
        this.user = user;
        this.createdDate = createdDate;
        this.saleDate = saleDate;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public LocalDateTime getSaleDate() {
        return saleDate;
    }

    public double getTotal() {
        return total;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public void setSaleDate(LocalDateTime saleDate) {
        this.saleDate = saleDate;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
