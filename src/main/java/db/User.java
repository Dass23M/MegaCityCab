/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;

/**
 *
 * @author DELL
 */


public class User {
    private int userId;
    private String username;
    private String email;
    private String password; // Transient or excluded from JSON
    private String phone;
    private String address;
    private String nic;
    private String role;

    public User() {}

    // Constructor without password for safe serialization
    public User(int userId, String username, String email, String phone, String address, String nic, String role) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.nic = nic;
        this.role = role;
    }

    // Getters and Setters (password has setter only for deserialization)
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getNic() { return nic; }
    public void setNic(String nic) { this.nic = nic; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}