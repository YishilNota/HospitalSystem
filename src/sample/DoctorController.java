package sample;

import Table.TableOPC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

/**
 * 医生诊断页面Controller
 */
public class DoctorController {
    @FXML
    private Button logout_btn;
    @FXML
    private Button add_text_btn;
    @FXML
    private Button replay_btn;
    @FXML
    private Button add_opced_btn;
    @FXML
    private Button reopc_btn;
    @FXML
    private Button out_work_btn;
    @FXML
    private Label doctor_name_label;
    @FXML
    private TextArea text_area;
    @FXML
    private TextField opc_id_field;
    @FXML
    private TextField opced_id_field;
    @FXML
    private TextField reopc_id_field;
    @FXML
    private TextField isWorking_field;
    @FXML
    private TableView<TableOPC> opc_top;
    @FXML
    private TableView<TableOPC> opced_top;
    @FXML
    private TableColumn<TableOPC, Integer> opc_id;
    @FXML
    private TableColumn<TableOPC, String> opc_username;
    @FXML
    private TableColumn<TableOPC, String> opc_text;
    @FXML
    private TableColumn<TableOPC, Integer> opced_id;
    @FXML
    private TableColumn<TableOPC, String> opced_username;
    @FXML
    private TableColumn<TableOPC, String> opced_text;

    static ObservableList<TableOPC> opc_list = null;//正在诊断列表副本
    static ObservableList<TableOPC> opced_list = null;//已经诊断列表副本
    static String DoctorName=null;
    static Integer isWorking = 0;

    /**
     * 退出登录
     */
    public void logout_to_login() {
        try {
            Stage currentStage = (Stage) logout_btn.getScene().getWindow();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("确认注销账户吗？");
            alert.setTitle("注销");
            Optional<ButtonType> result = alert.showAndWait();
            //如果点击OK
            if (result.get() == ButtonType.OK) {
                currentStage.close();
                System.out.println("注销账户！");
            } else {
                return;
            }
            currentStage.close(); //关闭当前窗口
            Parent root = FXMLLoader.load(getClass().getResource("../FXML/login.fxml")); //转到登录界面
            Stage newStage = new Stage();
            newStage.setTitle("医院管理系统 | 登录");
            newStage.setScene(new Scene(root));
            newStage.setResizable(false);
            Image icon = new Image("source/hospital.png");//图标路径
            newStage.getIcons().add(icon);//图标
            newStage.show();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * 医师诊断页面初始化
     *
     * @throws SQLException
     */
    public void initialize() throws SQLException { //初始化
        setTableOPC();
    }

    /**
     * 加载诊断页面及数据
     *
     * @throws SQLException
     */
    public void setTableOPC() throws SQLException { //显示诊断病人
        Connection connection = new MySqlConnector().connection();
        if (connection == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("系统出错！");
            System.out.println("诊断页面系统连接有误！");
            alert.showAndWait();
            return;
        }
        //正在诊断病人列表
        String sql1 = "select * from OPC where OPCed =0 AND OPC_Doctor_ID=? order by ID desc";
        PreparedStatement preparedStatement = connection.prepareStatement(sql1);
        preparedStatement.setInt(1, loginController.StaticDoctorID);
        ResultSet rs1 = preparedStatement.executeQuery();
        ObservableList<TableOPC> data1 = FXCollections.observableArrayList();
        int id = 1;
        while (rs1.next()) {
            String OPC_Text = rs1.getString("OPC_Text");
            Integer ID = rs1.getInt("UserID");
            Integer OPC_ID = rs1.getInt("ID");
            String sql1_1 = "select RealName from User where ID = ?";
            preparedStatement = connection.prepareStatement(sql1_1);
            preparedStatement.setInt(1, ID);
            ResultSet rs1_1 = preparedStatement.executeQuery();
            String RealName = null;
            while (rs1_1.next()) {
                RealName = rs1_1.getString("RealName");
            }
            data1.add(new TableOPC(id, RealName, OPC_Text, OPC_ID));
            id++;

        }
        opc_id.setCellValueFactory(new PropertyValueFactory<TableOPC, Integer>("ID"));
        opc_username.setCellValueFactory(new PropertyValueFactory<TableOPC, String>("RealName"));
        opc_text.setCellValueFactory(new PropertyValueFactory<TableOPC, String>("OPC_Text"));
        opc_top.setItems(data1);
        opc_list = data1;
        //已诊断病人列表
        String sql2 = "select * from OPC where OPCed =1 AND OPC_Doctor_ID=? order by ID desc";
        preparedStatement = connection.prepareStatement(sql2);
        preparedStatement.setInt(1, loginController.StaticDoctorID);
        ResultSet rs2 = preparedStatement.executeQuery();
        ObservableList<TableOPC> data2 = FXCollections.observableArrayList();
        id = 1;
        while (rs2.next()) {
            String OPC_Text = rs2.getString("OPC_Text");
            Integer ID = rs2.getInt("UserID");
            Integer OPC_ID = rs2.getInt("ID");
            //已进入表示
            String sql2_1 = "select RealName from User where ID = ?";
            preparedStatement = connection.prepareStatement(sql2_1);
            preparedStatement.setInt(1, ID);
            ResultSet rs2_1 = preparedStatement.executeQuery();
            String RealName = null;
            while (rs2_1.next()) {
                RealName = rs2_1.getString("RealName");
            }
            data2.add(new TableOPC(id, RealName, OPC_Text, OPC_ID));
            id++;
        }
        opced_id.setCellValueFactory(new PropertyValueFactory<TableOPC, Integer>("ID"));
        opced_username.setCellValueFactory(new PropertyValueFactory<TableOPC, String>("RealName"));
        opced_text.setCellValueFactory(new PropertyValueFactory<TableOPC, String>("OPC_Text"));
        opced_top.setItems(data2);
        opced_list = data2;

        //设置医生信息和工作状态
        int doctor_ID = loginController.StaticDoctorID;
        String sql_dName = "select RealName,isWorking from Chief where ID=?";
        preparedStatement = connection.prepareStatement(sql_dName);
        preparedStatement.setInt(1, doctor_ID);
        ResultSet nameRs = preparedStatement.executeQuery();
        while (nameRs.next()) {
            DoctorName= nameRs.getString("RealName");
            isWorking = nameRs.getInt("isWorking");
        }
        doctor_name_label.setText(DoctorName);
        if(isWorking==0){
            isWorking_field.setText("正在休息");
        }else{
            isWorking_field.setText("正在工作");
        }
        preparedStatement.close();
        connection.close();
    }

    /**
     * 添加诊断
     *
     * @throws SQLException
     */
    public void add_text_to_opc_text() throws SQLException {
        int t = 0;
        try {
            t = Integer.valueOf(opc_id_field.getText());
            if (t <= 0 || opc_list.size() < t) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("输入序号有误！");
                alert.setTitle("提示");
                alert.showAndWait();
                return;
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("输入序号有误！");
            alert.setTitle("提示");
            alert.showAndWait();
            return;
        }
        int id = opc_list.get(t - 1).getOPC_ID();
        Connection connection = new MySqlConnector().connection();
        if (connection == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("系统出错！");
            System.out.println("诊断页面系统连接有误！");
            alert.showAndWait();
            return;
        }
        //为诊断记录添加医生姓名
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dateNowStr = sdf.format(date);
        String text = "医师：" + DoctorName + " 时间：" + dateNowStr + "\n诊断结果：" + text_area.getText();
        String sql = "UPDATE OPC SET OPC_Text=? where ID=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, text);
        preparedStatement.setInt(2, id);
        preparedStatement.execute();
        replay();
    }

    /**
     * 清楚诊断内容
     */
    public void clear_text() {
        text_area.setText("");
    }

    /**
     * 点击诊断输入框时默认信息消失。
     */
    public void ready_for_input_text() {
        if (text_area.getText().equals("请输入诊断结果：")) {
            text_area.setText("");
            return;
        }
    }

    /**
     * 刷新诊断页面
     *
     * @throws SQLException
     */
    public void replay() throws SQLException {
        initialize();
    }

    /**
     * 添加到已诊断列表
     */
    public void add_opced() throws SQLException {
        int t = 0;
        try {
            t = Integer.valueOf(opced_id_field.getText());
            if (t <= 0 || opc_list.size() < t) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("输入序号有误！");
                alert.setTitle("提示");
                alert.showAndWait();
                return;
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("输入序号有误！");
            alert.setTitle("提示");
            alert.showAndWait();
            return;
        }
        int id = opc_list.get(t - 1).getOPC_ID();
        Connection connection = new MySqlConnector().connection();
        if (connection == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("系统出错！");
            System.out.println("诊断页面系统连接有误！");
            alert.showAndWait();
            return;
        }
        String sql = "UPDATE OPC SET OPCed=1 where ID=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.execute();
        replay();
    }

    /**
     * 重新诊断
     */
    public void reopc_to_opc() throws SQLException {
        int t = 0;
        try {
            t = Integer.valueOf(reopc_id_field.getText());
            System.out.println(opced_list.size());
            if (t <= 0 || opced_list.size() < t) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("输入序号有误！");
                alert.setTitle("提示");
                alert.showAndWait();
                return;
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("输入序号有误！");
            alert.setTitle("提示");
            alert.showAndWait();
            return;
        }
        int id = opced_list.get(t - 1).getOPC_ID();
        Connection connection = new MySqlConnector().connection();
        if (connection == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("系统出错！");
            System.out.println("诊断页面系统连接有误！");
            alert.showAndWait();
            return;
        }
        String sql = "UPDATE OPC SET OPCed=0 where ID=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        preparedStatement.execute();
        replay();
    }

    /**
     * 切换到下班或休息状态
     */
    public void change_isWorking_Out() throws SQLException{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("确认休息或下班吗？");
        alert.setTitle("下班");
        Optional<ButtonType> result = alert.showAndWait();
        //如果点击OK
        if (result.get() == ButtonType.OK) {
            System.out.println("注销账户！");
            Connection connection = new MySqlConnector().connection();
            if (connection == null) {
                Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);
                alertInfo.setHeaderText("系统出错！");
                System.out.println("诊断页面系统连接有误！");
                alertInfo.showAndWait();
                return;
            }
            int doctor_ID = loginController.StaticDoctorID;
            String sql = "UPDATE Chief SET isWorking=0 where ID=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, doctor_ID);
            preparedStatement.execute();
            replay();
            Alert alertOut = new Alert(Alert.AlertType.INFORMATION);
            alertOut.setHeaderText("已将工作状态调整为下班！");
            System.out.println("调整医师工作状态为下班！");
            alertOut.showAndWait();
            return;

        } else {
            return;
        }

    }

   public void change_isWorking_On()throws SQLException{
       Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
       alert.setHeaderText("确认上班开始诊断吗？");
       alert.setTitle("上班");
       Optional<ButtonType> result = alert.showAndWait();
       //如果点击OK
       if (result.get() == ButtonType.OK) {
           System.out.println("开始诊断");
           Connection connection = new MySqlConnector().connection();
           if (connection == null) {
               Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);
               alertInfo.setHeaderText("系统出错！");
               System.out.println("诊断页面系统连接有误！");
               alertInfo.showAndWait();
               return;
           }
           int doctor_ID = loginController.StaticDoctorID;
           String sql = "UPDATE Chief SET isWorking=1 where ID=?";
           PreparedStatement preparedStatement = connection.prepareStatement(sql);
           preparedStatement.setInt(1, doctor_ID);
           preparedStatement.execute();
           replay();
           Alert alertOut = new Alert(Alert.AlertType.INFORMATION);
           alertOut.setHeaderText("已将工作状态调整为上班班！");
           System.out.println("调整医师工作状态为上班！");
           alertOut.showAndWait();
           return;
       } else {
           return;
       }

   }
}
