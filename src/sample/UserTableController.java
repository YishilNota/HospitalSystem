package sample;

import Table.TableUser;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 用户信息表Controller
 */
public class UserTableController {

    //用户信息页面
    @FXML
    private Button close_btn;
    @FXML
    private TableColumn<TableUser, String> user_RName;
    @FXML
    private TableColumn<TableUser, Integer> user_sex;
    @FXML
    private TableColumn<TableUser, Integer> user_age;
    @FXML
    private TableColumn<TableUser, String> user_IDCARD;
    @FXML
    private TableColumn<TableUser, String> user_UName;
    @FXML
    private TableColumn<TableUser, String> user_passwd;
    @FXML
    private TableView<TableUser> user_table;

    /**
     * 关闭页面
     */
    public void back_to_home() throws Exception {
        Stage currentStage = (Stage) close_btn.getScene().getWindow();
        System.out.println(currentStage.getTitle() + "——>点击关闭系统！");
        currentStage.close();
        System.out.println(currentStage.getTitle() + "系统关闭！");

    }

    /**
     * 病人用户信息表展示
     *
     * @throws SQLException
     */
    public void initialize() throws SQLException { //初始化
        user_panel_display();
    }

    /**
     * 病人用户信息表展示
     */
    public void user_panel_display() throws SQLException {
        Connection connection = new MySqlConnector().connection();
        if (connection == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("系统出错！");
            System.out.println("专家信息页面数据库连接出错！");
            alert.showAndWait();
            return;
        }
        String sql = "select * from User";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();
        ObservableList<TableUser> data = FXCollections.observableArrayList();
        while (rs.next()) {
            String RealName = rs.getString("RealName");
            Integer Sex = rs.getInt("Sex");
            Integer Age = rs.getInt("Age");
            String sexText = null;
            if (Sex == 0) {
                sexText = "女";
            } else {
                sexText = "男";
            }
            String IDCARD = rs.getString("IDCARD");
            String UserName = rs.getString("UserName");
            String PassWord = rs.getString("Password");
            data.add(new TableUser(RealName, Age, sexText, IDCARD, UserName, PassWord));
        }
        user_RName.setCellValueFactory(new PropertyValueFactory<TableUser, String>("RealName"));
        user_sex.setCellValueFactory(new PropertyValueFactory<TableUser, Integer>("Sex"));
        user_age.setCellValueFactory(new PropertyValueFactory<TableUser, Integer>("Age"));
        user_IDCARD.setCellValueFactory(new PropertyValueFactory<TableUser, String>("IDCARD"));
        user_UName.setCellValueFactory(new PropertyValueFactory<TableUser, String>("UserName"));
        user_passwd.setCellValueFactory(new PropertyValueFactory<TableUser, String>("PassWord"));
        user_table.setItems(data);
        preparedStatement.close();
        connection.close();
    }

}
