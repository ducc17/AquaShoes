package dao;

import context.DBContext;
import entity.Account;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO extends DBContext {

    // Kiểm tra đăng nhập người dùng
    public Account checkLogin(String user, String pass) {
        // Đảm bảo luôn sử dụng try-with-resources để tự động đóng tài nguyên
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        
        // Khai báo các đối tượng bên ngoài try-with-resources
        try ( 
             PreparedStatement ps = connection.prepareStatement(query)) {
             
            // Set các tham số vào PreparedStatement
            ps.setString(1, user);
            ps.setString(2, pass);
            
            // Thực thi truy vấn
            try (ResultSet rs = ps.executeQuery()) {
                // Nếu có kết quả, tạo và trả về đối tượng Account
                if (rs.next()) {
                    return new Account(rs.getString("username"),
                            rs.getString("password"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();  // In ra lỗi nếu có
        }
        
        // Trả về null nếu không có tài khoản hợp lệ
        return null;
    }

    public static void main(String[] args) {
        // Tạo đối tượng UserDAO để kiểm tra login
        UserDAO dao = new UserDAO();
        
        // Thử login với username và password
        String username = "admin";  // Thay username theo yêu cầu
        String password = "hashed_password_1";  // Thay password theo yêu cầu
        
        Account account = dao.checkLogin(username, password);
        
        if (account != null) {
            System.out.println("Login successful!");
            System.out.println("Username: " + account.getUsername());
            // Thực hiện các thao tác với đối tượng Account nếu cần
        } else {
            System.out.println("Invalid username or password.");
        }
    }
}