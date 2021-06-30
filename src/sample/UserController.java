package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Random;

/**
 * 病人页面Controller
 */
public class UserController {
    @FXML
    private Button close_btn;
    @FXML
    private Button logout_btn;
    @FXML
    private Button hospital_btn;
    @FXML
    private Button clief_btn;
    @FXML
    private Button info_btn;
    @FXML
    private Button opc_price_btn;
    @FXML
    private Button opc_btn;
    @FXML
    private Label name_label;
    @FXML
    private Label IDCARD_label;
    @FXML
    private Label age_label;
    @FXML
    private Label sex_label;
    @FXML
    private Label balance_label;
    @FXML
    private Label realname_label;
    @FXML
    private Button money_btn;

    //挂号
    @FXML
    private RadioButton opc_1_rb;
    @FXML
    private RadioButton opc_2_rb;
    @FXML
    private RadioButton opc_3_rb;
    @FXML
    private RadioButton opc_4_rb;
    @FXML
    private RadioButton opc_5_rb;
    @FXML
    private Button opc_confirm_btn;

    //充值
    @FXML
    private Button add_money_btn;
    @FXML
    private TextField money_field;
    @FXML
    private Button money_check_btn;


    /**
     * 关闭系统
     *
     * @throws Exception
     */
    public void back_to_home() throws Exception {
        Stage currentStage = (Stage) close_btn.getScene().getWindow();
        System.out.println(currentStage.getTitle() + "——>点击关闭系统！");
        if (currentStage.getTitle().equals("医院管理系统 | 收费标准")) {
            currentStage.close();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("确认关闭吗？");
        alert.setTitle("关闭");
        Optional<ButtonType> result = alert.showAndWait();
        //如果点击OK
        if (result.get() == ButtonType.OK) {
            currentStage.close();
            System.out.println("系统关闭！");
        } else {
            return;
        }
    }

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
     * 病人页面信息显示
     *
     * @param UserName 用户名
     * @param Age      年龄
     * @param Sex      性别
     * @param IDCARD   身份证号码
     * @param RealName 真实姓名
     */
    public void display(String UserName, int Age, int Sex, String IDCARD, String RealName) {
        name_label.setText("用户名: " + UserName);
        age_label.setText("年龄: " + Age + "岁");
        String sex_str = null;
        if (Sex == 1) {
            sex_str = "男";
        } else sex_str = "女";
        sex_label.setText("性别: " + sex_str);
        IDCARD_label.setText("身份证号码: " + IDCARD);
        realname_label.setText("姓名: " + RealName);

    }

    /**
     * 医院信息显示
     */
    public void hospital_info_display() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../FXML/hospital.fxml"));
            Stage newStage = new Stage();
            newStage.setTitle("医院管理系统 | 医院信息");
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
     * 医师信息显示页面打开
     */
    public void clief_info_display() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../FXML/chief.fxml"));
            Stage newStage = new Stage();
            newStage.setTitle("医院管理系统 | 主任医师信息");
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
     * 医师信息显示
     */

    /**
     * 用户通知页面打开
     */
    public void oepn_info_panel() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../FXML/info.fxml"));
            Stage newStage = new Stage();
            newStage.setTitle("医院管理系统 | 通知");
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
     * 打开门诊页面
     */
    public void add_now_opc() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../FXML/userOPC.fxml"));
            Stage newStage = new Stage();
            newStage.setTitle("医院管理系统 | 挂号");
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
     * 打开收费标准页面
     */
    public void opc_price_panel() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../FXML/opcPrice.fxml"));
            Stage newStage = new Stage();
            newStage.setTitle("医院管理系统 | 收费标准");
            newStage.setScene(new Scene(root));
            newStage.setResizable(false);
            Image icon = new Image("source/hospital.png");//图标路径
            newStage.getIcons().add(icon);//图标
            newStage.show();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void add_opc_confirm() throws SQLException, IOException {
        if (loginController.StaticBalance < 5) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("挂号失败，余额不足，请先充值。");
            System.out.println("余额不足以挂号。");
            alert.showAndWait();
            Stage currentStage = (Stage) opc_confirm_btn.getScene().getWindow();
            currentStage.close();
            return;
        }
        int opc_id = 5;
        if (opc_1_rb.isSelected()) {
            opc_id = 1;
        } else if (opc_2_rb.isSelected()) {
            opc_id = 2;
        } else if (opc_3_rb.isSelected()) {
            opc_id = 3;
        } else if (opc_4_rb.isSelected()) {
            opc_id = 4;
        }
        Connection connection = new MySqlConnector().connection();
        if (connection == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("系统出错！");
            System.out.println("挂号页面系统连接有误！");
            alert.showAndWait();
            return;
        }
        String sql_chief = "select * from Chief where  Work=? and isWorking=1";
        PreparedStatement preparedStatement = connection.prepareStatement(sql_chief);
        preparedStatement.setInt(1, opc_id);
        ResultSet rs = preparedStatement.executeQuery();
        int n = 0;
        while (rs.next()) {
            n++;
        }
        if (n == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("挂号失败，请在工作时间挂号！");
            System.out.println("非挂号时间。");
            alert.showAndWait();
            return;
        }
        System.out.println(n);
        Random random = new Random();
        int chief_id = random.nextInt(n);
        if (chief_id == 0) {
            chief_id = 1;
        }
        n = 1;
        rs.first();
        System.out.println(chief_id);
        while (rs.next()) {
            if (n == chief_id) {
                chief_id = rs.getInt("ID");
                break;
            }
            n++;
        }

        String sql = "insert into OPC (UserID, WorkType, OPC_Doctor_ID) VALUES(?,?,?)";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, loginController.StaticUserID);
        preparedStatement.setInt(2, opc_id);
        preparedStatement.setInt(3, chief_id);
        preparedStatement.execute();
        loginController.StaticBalance -= 10;//挂号-5元
        String sql_balance = "update User set Balance=? where ID = ?";
        preparedStatement = connection.prepareStatement(sql_balance);
        preparedStatement.setDouble(1, loginController.StaticBalance);
        preparedStatement.setInt(2, loginController.StaticUserID);
        preparedStatement.execute();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        String opc_type = null;
        if (opc_id == 1) {
            opc_type = "骨科";
        } else if (opc_id == 2) {
            opc_type = "儿科";
        } else if (opc_id == 3) {
            opc_type = "内科";
        } else if (opc_id == 4) {
            opc_type = "外科";
        } else {
            opc_type = "全科";
        }
        alert.setHeaderText(opc_type + "挂号成功！");
        System.out.println("挂号成功，页面关闭。");
        alert.showAndWait();
        Stage currentStage = (Stage) opc_confirm_btn.getScene().getWindow();
        currentStage.close();
        preparedStatement.close();
        connection.close();
    }

    /**
     * 充值页面
     */
    public void open_add_money_panel() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../FXML/addMoney.fxml"));
            Stage newStage = new Stage();
            newStage.setTitle("医院管理系统 | 充值");
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
     * 充值
     */
    public void add_money_by_user() throws SQLException {
        Connection connection = new MySqlConnector().connection();
        if (connection == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("系统出错！");
            System.out.println("充值页面系统连接有误！");
            alert.showAndWait();
            return;
        }
        String sql = "select Balance from User where ID=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, loginController.StaticUserID);
        ResultSet rs = preparedStatement.executeQuery();
        Double balance = 0.0;
        while (rs.next()) {
            balance = rs.getDouble("Balance");
        }
        System.out.println("*********************"+balance);
        Double add_money = Double.valueOf(money_field.getText());
        if (add_money > 100000) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("请输入金额为5元~10000元。");
            System.out.println("输入金额太大或太小。");
            alert.showAndWait();
            return;
        }
        balance = balance+add_money;
        String sql2 = "update User set Balance=? where ID=?";
        preparedStatement = connection.prepareStatement(sql2);
        preparedStatement.setDouble(1, balance);
        preparedStatement.setInt(2, loginController.StaticUserID);
        preparedStatement.execute();
        loginController.StaticBalance=balance;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("充值成功！");
        System.out.println("充值成功！");
        alert.showAndWait();
        Stage currentStage = (Stage) add_money_btn.getScene().getWindow();
        currentStage.close();
    }

    public void money_check_by_user(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("余额");
        alert.setHeaderText("您的余额为"+loginController.StaticBalance+"元。");
        System.out.println("显示余额。");
        alert.showAndWait();
    }

    public void open_opcInfo_panel(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../FXML/opc.fxml"));
            Stage newStage = new Stage();
            newStage.setTitle("医院管理系统 | 病历");
            newStage.setScene(new Scene(root));
            newStage.setResizable(false);
            Image icon = new Image("source/hospital.png");//图标路径
            newStage.getIcons().add(icon);//图标
            newStage.show();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }


}
