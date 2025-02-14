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
    protected Connection connection;

    public DBContext() {
        try {
            // Chỉnh sửa URL, username, password để kết nối với SQL Server của bạn
            String url = "jdbc:sqlserver://localhost:1433;databaseName=ShoeStore;encrypt=false";
            String username = "sa";
            String password = "123";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Kết nối thành công!");
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Lỗi kết nối: " + ex.getMessage());
        }
    }
//
//    public void getCategoryData() {
//        String query = "SELECT * FROM categories";
//        try (Statement stmt = connection.createStatement();
//             ResultSet rs = stmt.executeQuery(query)) {
//
//            System.out.println("Danh sách danh mục:");
//            while (rs.next()) {
//                int id = rs.getInt("id");
//                String name = rs.getString("name");
//                String description = rs.getString("describe");
//                System.out.println("ID: " + id + ", Tên: " + name + ", Mô tả: " + description);
//            }
//        } catch (SQLException ex) {
//            System.out.println("Lỗi truy vấn: " + ex.getMessage());
//        }
//    }
//
//    public static void main(String[] args) {
//        DBContext db = new DBContext();
//        db.getCategoryData();
//    }
}