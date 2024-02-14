package Models;

import java.time.LocalDateTime;
import java.util.List;

public class User {
    private int id;
    private String firstName;
    private String lastName;
    private List<Role> roles;
    private String email;
    private String password;
    private LocalDateTime createdDate;

    public User() {
    }

    public User(int id, String firstName, String lastName, List<Role> roles, String email, String password, LocalDateTime createdDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.roles = roles;
        this.email = email;
        this.password = password;
        this.createdDate = createdDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }
}
