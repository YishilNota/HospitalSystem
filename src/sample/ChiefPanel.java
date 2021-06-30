package sample;

import Table.TableChief;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 用户界面查医师信息页面
 */
public class ChiefPanel {

    @FXML
    private Button close_btn;
    @FXML
    private TableView<TableChief> a_top;
    @FXML
    private TableView<TableChief> b_top;
    @FXML
    private TableView<TableChief> c_top;
    @FXML
    private TableView<TableChief> d_top;
    @FXML
    private TableView<TableChief> e_top;
    @FXML
    private TableColumn<TableChief, Integer> a_ID;
    @FXML
    private TableColumn<TableChief, String> a_RealName;
    @FXML
    private TableColumn<TableChief, String> a_WorkTime;
    @FXML
    private TableColumn<TableChief, Integer> a_Other;
    @FXML
    private TableColumn<TableChief, Integer> b_ID;
    @FXML
    private TableColumn<TableChief, String> b_RealName;
    @FXML
    private TableColumn<TableChief, String> b_WorkTime;
    @FXML
    private TableColumn<TableChief, Integer> b_Other;
    @FXML
    private TableColumn<TableChief, Integer> c_ID;
    @FXML
    private TableColumn<TableChief, String> c_RealName;
    @FXML
    private TableColumn<TableChief, String> c_WorkTime;
    @FXML
    private TableColumn<TableChief, Integer> c_Other;
    @FXML
    private TableColumn<TableChief, Integer> d_ID;
    @FXML
    private TableColumn<TableChief, String> d_RealName;
    @FXML
    private TableColumn<TableChief, String> d_WorkTime;
    @FXML
    private TableColumn<TableChief, Integer> d_Other;
    @FXML
    private TableColumn<TableChief, Integer> e_ID;
    @FXML
    private TableColumn<TableChief, String> e_RealName;
    @FXML
    private TableColumn<TableChief, String> e_WorkTime;
    @FXML
    private TableColumn<TableChief, Integer> e_Other;


    public void back_to_home() throws Exception {
        Stage currentStage = (Stage) close_btn.getScene().getWindow();
        System.out.println(currentStage.getTitle() + "——>点击关闭系统！");
        currentStage.close();
        System.out.println(currentStage.getTitle() +"系统关闭！");

    }

    public void initialize() throws SQLException { //初始化
        setTableVoc();
    }

    public void setTableVoc() throws SQLException { //显示专家页面
        Connection connection = new MySqlConnector().connection();
        if (connection == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("系统出错！");
            System.out.println("专家信息页面数据库连接出错！");
            alert.showAndWait();
            return;
        }

        //骨科 代号1
        String sql1 = "select * from Chief where Work=1";
        PreparedStatement preparedStatement = connection.prepareStatement(sql1);
        ResultSet rs1 = preparedStatement.executeQuery();
        ObservableList<TableChief> data1 = FXCollections.observableArrayList();
        while (rs1.next()) {
            Integer ID = rs1.getInt("ID");
            String WorkTime = rs1.getString("WorkTime");
            String RealName = rs1.getString("RealName");
            data1.add(new TableChief(ID, RealName, WorkTime));
        }
        a_ID.setCellValueFactory(new PropertyValueFactory<TableChief, Integer>("ID"));
        a_RealName.setCellValueFactory(new PropertyValueFactory<TableChief, String>("RealName"));
        a_WorkTime.setCellValueFactory(new PropertyValueFactory<TableChief, String>("WorkTime"));
        a_top.setItems(data1);

        //儿科 代号2
        String sql2 = "select * from Chief where Work=2";
        preparedStatement = connection.prepareStatement(sql2);
        ResultSet rs2 = preparedStatement.executeQuery();
        ObservableList<TableChief> data2 = FXCollections.observableArrayList();
        while (rs2.next()) {
            Integer ID = rs2.getInt("ID");
            String WorkTime = rs2.getString("WorkTime");
            String RealName = rs2.getString("RealName");
            data2.add(new TableChief(ID, RealName, WorkTime));
        }
        b_ID.setCellValueFactory(new PropertyValueFactory<TableChief, Integer>("ID"));
        b_RealName.setCellValueFactory(new PropertyValueFactory<TableChief, String>("RealName"));
        b_WorkTime.setCellValueFactory(new PropertyValueFactory<TableChief, String>("WorkTime"));
        b_top.setItems(data2);

        //内科 代号3
        String sql3 = "select * from Chief where Work=3";
        preparedStatement = connection.prepareStatement(sql3);
        ResultSet rs3 = preparedStatement.executeQuery();
        ObservableList<TableChief> data3 = FXCollections.observableArrayList();
        while (rs3.next()) {
            Integer ID = rs3.getInt("ID");
            String WorkTime = rs3.getString("WorkTime");
            String RealName = rs3.getString("RealName");
            data3.add(new TableChief(ID, RealName, WorkTime));
        }
        c_ID.setCellValueFactory(new PropertyValueFactory<TableChief, Integer>("ID"));
        c_RealName.setCellValueFactory(new PropertyValueFactory<TableChief, String>("RealName"));
        c_WorkTime.setCellValueFactory(new PropertyValueFactory<TableChief, String>("WorkTime"));
        c_top.setItems(data3);

        //外科 代号4
        String sql4 = "select * from Chief where Work=4";
        preparedStatement = connection.prepareStatement(sql4);
        ResultSet rs4 = preparedStatement.executeQuery();
        ObservableList<TableChief> data4 = FXCollections.observableArrayList();
        while (rs4.next()) {
            Integer ID = rs4.getInt("ID");
            String WorkTime = rs4.getString("WorkTime");
            String RealName = rs4.getString("RealName");
            data4.add(new TableChief(ID, RealName, WorkTime));
        }
        d_ID.setCellValueFactory(new PropertyValueFactory<TableChief, Integer>("ID"));
        d_RealName.setCellValueFactory(new PropertyValueFactory<TableChief, String>("RealName"));
        d_WorkTime.setCellValueFactory(new PropertyValueFactory<TableChief, String>("WorkTime"));
        d_top.setItems(data4);

        //其他 代号5
        String sql5 = "select * from Chief where Work=5";
        preparedStatement = connection.prepareStatement(sql5);
        ResultSet rs5 = preparedStatement.executeQuery();
        ObservableList<TableChief> data5 = FXCollections.observableArrayList();
        while (rs5.next()) {
            Integer ID = rs5.getInt("ID");
            String WorkTime = rs5.getString("WorkTime");
            String RealName = rs5.getString("RealName");
            data5.add(new TableChief(ID, RealName, WorkTime));
        }
        e_ID.setCellValueFactory(new PropertyValueFactory<TableChief, Integer>("ID"));
        e_RealName.setCellValueFactory(new PropertyValueFactory<TableChief, String>("RealName"));
        e_WorkTime.setCellValueFactory(new PropertyValueFactory<TableChief, String>("WorkTime"));
        e_top.setItems(data5);

        preparedStatement.close();
        connection.close();
    }


}


