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
import static org.apache.tomcat.jakartaee.commons.lang3.ArrayUtils.insert;

/**
 *
 * @author Wind
 */
public class UserDAO extends DBContext {

    // Kiểm tra đăng nhập người dùng
    public User checkLogin(String user, String pass) {
        // Đảm bảo luôn sử dụng try-with-resources để tự động đóng tài nguyên
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";

        // Khai báo các đối tượng bên ngoài try-with-resources
        try (
                PreparedStatement ps = c.prepareStatement(query)) {

            // Set các tham số vào PreparedStatement
            ps.setString(1, user);
            ps.setString(2, pass);

            // Thực thi truy vấn
            try (ResultSet rs = ps.executeQuery()) {
                // Nếu có kết quả, tạo và trả về đối tượng Account
                if (rs.next()) {
                    
                    return new User(
                            rs.getInt("user_id"), // userId
                            rs.getString("username"), // username
                            rs.getString("email"), // email
                            rs.getString("password"), // password (lưu ý: sử dụng password đã mã hóa)
                            rs.getString("role"), // role
                             rs.getString("status") // role
                            
                    );      // phải trung trên bảng vs database
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  // In ra lỗi nếu có
        }

        // Trả về null nếu không có tài khoản hợp lệ
        return null;
    }

    public User checkUserExist(String user) {
        // Đảm bảo luôn sử dụng try-with-resources để tự động đóng tài nguyên
        String query = "SELECT * FROM users WHERE username = ?";

        // Khai báo các đối tượng bên ngoài try-with-resources
        try (
                PreparedStatement ps = c.prepareStatement(query)) {

            // Set các tham số vào PreparedStatement
            ps.setString(1, user);

            // Thực thi truy vấn
            try (ResultSet rs = ps.executeQuery()) {
                // Nếu có kết quả, tạo và trả về đối tượng Account
                if (rs.next()) {
                    return new User(
                            rs.getInt("user_id"), // userId
                            rs.getString("username"), // username
                            rs.getString("email"), // email
                            rs.getString("password"), // password (lưu ý: sử dụng password đã mã hóa)
                            rs.getString("role"), // role
                              rs.getString("status")
                    );      // phải trung trên bảng vs database
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  // In ra lỗi nếu có
        }

        // Trả về null nếu không có tài khoản hợp lệ
        return null;
    }

    public boolean insert(User user) {
        String query = "INSERT INTO users (username, email, password ) VALUES (?, ?, ?)";

        try (PreparedStatement ps = c.prepareStatement(query)) {
            // Set các tham số vào PreparedStatement
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());

            // Thực thi câu lệnh insert
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;  // Trả về true nếu chèn thành công
        } catch (SQLException e) {
            e.printStackTrace();  // In ra lỗi nếu có
            return false;  // Trả về false nếu có lỗi
        }
    }

    public User checkEmailExist(String email) {
        // Đảm bảo luôn sử dụng try-with-resources để tự động đóng tài nguyên
        String query = "SELECT * FROM users WHERE email = ?";

        // Khai báo các đối tượng bên ngoài try-with-resources
        try (
                PreparedStatement ps = c.prepareStatement(query)) {

            // Set các tham số vào PreparedStatement
            ps.setString(1, email);

            // Thực thi truy vấn
            try (ResultSet rs = ps.executeQuery()) {
                // Nếu có kết quả, tạo và trả về đối tượng Account
                if (rs.next()) {
                    return new User(
                            rs.getInt("user_id"), // userId
                            rs.getString("username"), // username
                            rs.getString("email"), // email
                            rs.getString("password"), // password (lưu ý: sử dụng password đã mã hóa)
                            rs.getString("role"),
                            rs.getString("status") // role
                            
                    );      // phải trung trên bảng vs database
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  // In ra lỗi nếu có
        }

        // Trả về null nếu không có tài khoản hợp lệ
        return null;
    }

//    public static void main(String[] args) {
//        // Tạo đối tượng UserDAO để kiểm tra User
//        UserDAO dao = new UserDAO();
//        
//        // Thử User với username và password
//        String username = "john_doe";  // Thay username theo yêu cầu
//        String password = "password123";  // Thay password theo yêu cầu
//        
//        User account = dao.checkLogin(username, password);
//        
//        if (account != null) {
//            System.out.println("Login successful!");
//            System.out.println("Username: " + account.getUsername());
//            // Thực hiện các thao tác với đối tượng Account nếu cần
//        } else {
//            System.out.println("Invalid username or password.");
//        }
//    }
    public static void main(String[] args) {
        // Khởi tạo đối tượng UserDAO (giả sử đã có kết nối cơ sở dữ liệu)
        UserDAO dao = new UserDAO();

        // Kiểm tra user có tồn tại không
        String usernameToCheck = "john_doe";
        User existingUser = dao.checkUserExist(usernameToCheck);
        if (existingUser != null) {
            System.out.println("User exists: " + existingUser.getUsername());
        } else {
            System.out.println("User does not exist.");
        }
//
        // Kiểm tra email có tồn tại không
//        String emailToCheck = "john@example.com";
//        User existingEmail = dao.checkEmailExist(emailToCheck);
//        if (existingEmail != null) {
//            System.out.println("Email already registered: " + existingEmail.getEmail());
//        } else {
//            System.out.println("Email is available.");
//        }
        // Đăng ký tài khoản mới
//        String newUser = "new_user1";
//        String newEmail = "new_user1@example.com";
//        String newPassword = "securePass123";
//        User user1 = new User(newUser, newEmail, newPassword);
//        dao.insert(user1);
//        System.out.println("User signed up successfully!");
//
//        // Kiểm tra lại user vừa đăng ký
//        User checkNewUser = dao.checkUserExist(newUser);
//        if (checkNewUser != null) {
//            System.out.println("New user exists: " + checkNewUser.getUsername());
//        } else {
//            System.out.println("Signup failed.");
//        }
    }

}
