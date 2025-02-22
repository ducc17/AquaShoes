/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import context.DBContext;
import entity.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Wind
 */
public class ManagerDAO extends DBContext {

    public ArrayList<User> getUser() {
        ArrayList<User> list = new ArrayList<>();
        String query = "SELECT * FROM users";
        try (PreparedStatement ps = c.prepareStatement(query); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("role"),
                        rs.getString("status")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void addUser(User u) {
        if (getUserByUsername(u.getUsername()) != null) {
            System.out.println("User with username " + u.getUsername() + " already exists.");
            return;
        }
        String query = "INSERT INTO users (username, email, password, role, status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = c.prepareStatement(query)) {
            stmt.setString(1, u.getUsername());
            stmt.setString(2, u.getEmail());
            stmt.setString(3, u.getPassword());
            stmt.setString(4, u.getRole());
            stmt.setString(5, u.getStatus());
            stmt.executeUpdate();
            System.out.println("User added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ✅ Xóa user theo ID
    public void deleteUser(int userId) {
        String query = "DELETE FROM users WHERE user_id = ?";
        if (getUserById(userId) == null) {
            System.out.println("No user found with ID: " + userId);
            return;
        }
        try (PreparedStatement stmt = c.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
            System.out.println("User deleted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ✅ Lấy thông tin user theo ID
    public User getUserById(int userId) {
        String query = "SELECT * FROM users WHERE user_id = ?";
        try (PreparedStatement stmt = c.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("role"),
                        rs.getString("status")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // ✅ Cập nhật thông tin user
    public void updateUser(User u) {
        String query = "UPDATE users SET username = ?, email = ?, password = ?, role = ?, status = ? WHERE user_id = ?";
        try (PreparedStatement stmt = c.prepareStatement(query)) {
            stmt.setString(1, u.getUsername());
            stmt.setString(2, u.getEmail());
            stmt.setString(3, u.getPassword());
            stmt.setString(4, u.getRole());
            stmt.setString(5, u.getStatus());
            stmt.setInt(6, u.getUserId());
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("User updated successfully!");
            } else {
                System.out.println("No user found with ID: " + u.getUserId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ✅ Lấy thông tin user theo username
    public User getUserByUsername(String username) {
        String query = "SELECT * FROM users WHERE username = ?";
        try (PreparedStatement stmt = c.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("role"),
                        rs.getString("status")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;  // Không tìm thấy user
    }

    public static void main(String[] args) {
        // Kết nối database
        // Giả sử đã có class DBContext quản lý kết nối
        ManagerDAO manager = new ManagerDAO();

        // 🔹 1. Lấy danh sách tất cả user
        ArrayList<User> users = manager.getUser();
        System.out.println("=== Danh sách User ===");
        for (User u : users) {
            System.out.println(u);
        }
//
//        // 🔹 2. Kiểm tra user có tồn tại không
//        String usernameToCheck = "john_doe";
//        User existingUser = manager.getUserByUsername(usernameToCheck);
//        if (existingUser != null) {
//            System.out.println("User exists: " + existingUser.getUsername());
//        } else {
//            System.out.println("User does not exist.");
//        }
//
//        // 🔹 3. Thêm người dùng mới nếu chưa tồn tại
//        String newUsername = "new_user1";
//        String newEmail = "new_user1@example.com";
//        String newPassword = "securePass123";
//
//        if (manager.getUserByUsername(newUsername) == null) {
//            User newUser = new User(0, newUsername, newEmail, newPassword, "customer", "active");
//            manager.addUser(newUser);
//            System.out.println("User added successfully!");
//        } else {
//            System.out.println("Username already taken.");
//        }
//
//        // 🔹 4. Cập nhật thông tin người dùng
//        User updateUser = manager.getUserByUsername(newUsername);
//        if (updateUser != null) {
//            updateUser.setEmail("updated_email@example.com");
//            updateUser.setPassword("newSecurePass456");
//            manager.updateUser(updateUser);
//            System.out.println("User updated successfully!");
//        }
//
//        // 🔹 5. Xóa người dùng
//        User userToDelete = manager.getUserByUsername("test_user");
//        if (userToDelete != null) {
//            manager.deleteUser(userToDelete.getUserId());
//            System.out.println("User deleted successfully!");
//        }
    }

}
