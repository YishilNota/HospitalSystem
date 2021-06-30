package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * MySQL连接器
 */
public class MySqlConnector {
    public Connection connection() {
        Connection myConnection = null;
        try {
            myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital?&useSSL=false&serverTimezone=GMT", "root", "root");
            if (myConnection != null)
                System.out.println("MySql连接成功！");
            else
                System.out.println("MySq连接失败！");
        } catch (SQLException e) {
            System.out.println("myConnection出错！");
            e.printStackTrace();
        }
        return myConnection;
    }
}
