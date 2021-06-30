package sample;

import Table.TableOPCInfo;
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
 * 病历页面Controller
 */
public class OpcInfoController {

    @FXML
    private Button close_btn;
    @FXML
    private TableColumn<TableOPCInfo, String> WorkName;
    @FXML
    private TableColumn<TableOPCInfo, String> OpcText;
    @FXML
    private TableView<TableOPCInfo> opc_table;


    public void back_to_home() throws Exception {
        Stage currentStage = (Stage) close_btn.getScene().getWindow();
        System.out.println(currentStage.getTitle() + "——>点击关闭系统！");
        currentStage.close();
        System.out.println(currentStage.getTitle() + "系统关闭！");
    }


    /**
     * 病历展示
     *
     * @throws SQLException
     */
    public void initialize() throws SQLException { //初始化
        user_opc_display();
    }

    public void user_opc_display() throws SQLException {
        Connection connection = new MySqlConnector().connection();
        if (connection == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("系统出错！");
            System.out.println("病历页面数据库连接出错！");
            alert.showAndWait();
            return;
        }
        String sql = "select * from OPC where ID=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, loginController.StaticUserID);
        ResultSet rs = preparedStatement.executeQuery();
        ObservableList<TableOPCInfo> data = FXCollections.observableArrayList();
        while (rs.next()) {
            String opc_text = rs.getString("OPC_Text");
            Integer opc_type = rs.getInt("WorkType");
            String opc_word = null;
            if (opc_type == 1) {
                opc_word = "骨科";
            } else if (opc_type == 2) {
                opc_word = "儿科";
            } else if (opc_type == 3) {
                opc_word = "内科";
            } else if (opc_type == 4) {
                opc_word = "外科";
            } else {
                opc_word = "全科";
            }
            data.add(new TableOPCInfo(opc_word, opc_text));
        }
        WorkName.setCellValueFactory(new PropertyValueFactory<TableOPCInfo, String>("WorkName"));
        OpcText.setCellValueFactory(new PropertyValueFactory<TableOPCInfo, String>("OpcText"));
        opc_table.setItems(data);
        System.out.println("病历页面加载成功。");
        preparedStatement.close();
        connection.close();
    }
}
