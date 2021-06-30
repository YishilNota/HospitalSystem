package sample;

import Table.TableInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.*;
import java.text.SimpleDateFormat;

/**
 * 用户通知页面
 */
public class infoPanel {
    @FXML
    private Button close_btn;
    @FXML
    private TextArea inform_area;
    @FXML
    private TableView<TableInfo> info_top;
    @FXML
    private TableColumn<TableInfo, String> info_text;
    @FXML
    private TableColumn<TableInfo, String> info_time;

    public void back_to_home() throws Exception {
        Stage currentStage = (Stage) close_btn.getScene().getWindow();
        System.out.println(currentStage.getTitle() + "——>点击关闭系统！");
        currentStage.close();
        System.out.println("系统关闭！");
    }

    public void initialize() throws SQLException { //初始化
        setInfoTable();
    }

    public void setInfoTable() throws SQLException {
        Connection connection = new MySqlConnector().connection();
        if (connection == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("系统出错！");
            System.out.println("通知信息页面数据库连接出错！");
            alert.showAndWait();
            return;
        }
        //用户消息
        int id = loginController.StaticUserID;
        String sql = "select * from Information where Text_Type=? order by ID desc";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet rs = preparedStatement.executeQuery();
        ObservableList data = FXCollections.observableArrayList();
        while (rs.next()) {
            String Text = rs.getString("Text");
            String Time = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(rs.getTimestamp("Time"));
            data.add(new TableInfo(Text, Time));
        }
        info_text.setCellValueFactory(new PropertyValueFactory<TableInfo, String>("Text"));
        info_time.setCellValueFactory(new PropertyValueFactory<TableInfo, String>("Time"));
        info_top.setItems(data);

        //通知
        StringBuilder infoPublic = new StringBuilder();
        String sql1 = "select * from Information where Text_Type=0 order by ID desc";
        preparedStatement = connection.prepareStatement(sql1);
        ResultSet rs1 = preparedStatement.executeQuery();
        while (rs1.next()) {
            String text = rs1.getString("Text");
            String time = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(rs1.getTimestamp("Time"));
            infoPublic.append(time);
            infoPublic.append("\t");
            infoPublic.append(text);
            infoPublic.append("\n");
        }
        String infos = infoPublic.toString();
        inform_area.setText(infos);

        preparedStatement.close();
        connection.close();
    }
}
