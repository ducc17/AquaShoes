package context;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Wind
 */
public class DBContext {
    protected Connection c;

    public DBContext() {
        try {
            // Chỉnh sửa URL, username, password để kết nối với SQL Server của bạn
            String url = "jdbc:sqlserver://localhost:1433;databaseName=AquaShoes;encrypt=false";
            String username = "sa";
            String password = "123";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            c = DriverManager.getConnection(url, username, password);
            System.out.println("Kết nối thành công!");
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Lỗi kết nối: " + ex.getMessage());
        }
    }

    public void getCategoryData() {
        String query = "SELECT * FROM users";
        try (Statement stmt = c.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.println("Danh sách danh mục:");
            while (rs.next()) {
                int id = rs.getInt("user_id");
                String name = rs.getString("username");
                String description = rs.getString("role");
                System.out.println("ID: " + id + ", Tên: " + name + ", Mô tả: " + description);
            }
        } catch (SQLException ex) {
            System.out.println("Lỗi truy vấn: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        DBContext db = new DBContext();
        db.getCategoryData();
    }
}