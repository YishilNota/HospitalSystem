package sample;

import Table.TableChief;
import Table.TableUser;
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
 * 管理员页面Controller
 */
public class AdminController {
    @FXML
    private Button logout_btn;
    @FXML
    private Button close_btn;
    @FXML
    private Button add_user_btn;
    @FXML
    private Button addInfoBtn;
    @FXML
    private TextField addInfoIDfield;
    @FXML
    private TextArea addInfoArea;


    //添加用户
    @FXML
    private TextField UserNameField;
    @FXML
    private TextField PassWordField;
    @FXML
    private TextField RealNameField;
    @FXML
    private TextField AgeField;
    @FXML
    private TextField SexField;
    @FXML
    private TextField IDCARDField;

    //添加医生
    @FXML
    private TextField CUserNameField;
    @FXML
    private TextField CPassWordField;
    @FXML
    private TextField CRealNameField;
    @FXML
    private TextField CAgeField;
    @FXML
    private TextArea Cworktime_area;
    @FXML
    private RadioButton Cmale_rbtn;
    @FXML
    private RadioButton Cfamale_rbtn;
    @FXML
    private RadioButton d_1;
    @FXML
    private RadioButton d_2;
    @FXML
    private RadioButton d_3;
    @FXML
    private RadioButton d_4;
    @FXML
    private RadioButton d_5;

    //添加管理员
    @FXML
    private Button add_admin_btn;
    @FXML
    private RadioButton admintype1_rb;
    @FXML
    private RadioButton admintype0_rb;
    @FXML
    private TextField AdminNamefield;
    @FXML
    private TextField AdminPswdfield;
    @FXML
    private TextField AdminRNamefield;

    //收费
    @FXML
    private Button money_out_btn;
    @FXML
    private TextField out_money_field;
    @FXML
    private Button out_money_btn;
    @FXML
    private TextField userNamefield;
    @FXML
    private Button money_check_btn;

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
     * 当前界面关闭
     *
     * @throws Exception
     */
    public void back_to_home() throws Exception {
        Stage currentStage = (Stage) close_btn.getScene().getWindow();
        System.out.println(currentStage.getTitle() + "——>点击关闭系统！");
        if (currentStage.getTitle() == "医院管理系统 | 医院信息") {
            currentStage.close();
            return;
        }
        if(currentStage.getTitle() == "医院管理系统 | 收费"){
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
            System.out.println(currentStage.getTitle() + "关闭。");
        } else {
            return;
        }
    }


    /**
     * 管理员发通知。
     */
    public void admin_add_info() throws SQLException {
        Connection connection = new MySqlConnector().connection();
        if (connection == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("系统出错！");
            System.out.println("诊断页面系统连接有误！");
            alert.showAndWait();
            return;
        }
        int t = 0;
        try {
            t = Integer.valueOf(addInfoIDfield.getText());
            if (t < 0) {
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
        // 发通公告
        if (t == 0) {
            String sql = "insert into Information ( Text_Type, Text) VALUES (0,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, addInfoArea.getText());
            preparedStatement.execute();
            preparedStatement.close();
            connection.close();
        } else {
            String isHasSql = "select ID from User where ID = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(isHasSql);
            preparedStatement.setInt(1, t);
            ResultSet rs = preparedStatement.executeQuery();
            rs.last();
            if (rs.getRow() == 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("该用户不存在或输入序号有误！");
                alert.setTitle("提示");
                alert.showAndWait();
                return;
            }
            String sql = "insert into Information ( Text_Type, Text) VALUES (?,?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, t);
            preparedStatement.setString(2, addInfoArea.getText());
            preparedStatement.execute();
            preparedStatement.close();
            connection.close();
        }
    }

    /**
     * 发布通知框点击清空
     */
    public void clear_text_area() {
        if (addInfoArea.getText().equals("请输入通知信息：")) {
            addInfoArea.setText("");
        }
    }


    /**
     * 添加用户界面
     */
    public void add_user() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../FXML/addUser.fxml"));
            Stage newStage = new Stage();
            newStage.setTitle("医院管理系统 | 添加病人");
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
     * 添加医生界面
     */
    public void add_chief() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../FXML/addChief.fxml"));
            Stage newStage = new Stage();
            newStage.setTitle("医院管理系统 | 添加医生");
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
     * 管理员添加病人用户
     *
     * @throws SQLException
     */
    public void add_user_by_admin() throws SQLException {
        Connection connection = new MySqlConnector().connection();
        if (connection == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("系统出错！");
            System.out.println("诊断页面系统连接有误！");
            alert.showAndWait();
            return;
        }
        int age = Integer.valueOf(AgeField.getText());
        int sex = 0;
        if (SexField.getText().equals("男")) {
            sex = 1;
        }
        String addUserSql = "insert into User(UserName, Password, Age, Sex, IDCARD, RealName) value(?,?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(addUserSql);
        preparedStatement.setString(1, UserNameField.getText());
        preparedStatement.setString(2, PassWordField.getText());
        preparedStatement.setInt(3, age);
        preparedStatement.setInt(4, sex);
        preparedStatement.setString(5, IDCARDField.getText());
        preparedStatement.setString(6, RealNameField.getText());
        preparedStatement.execute();
        connection.close();
        preparedStatement.close();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("用户 " + RealNameField.getText() + " 添加成功！");
        System.out.println(RealNameField.getText() + "————》用户添加成功！");
        alert.showAndWait();
        return;

    }

    /**
     * 管理员添加病人用户
     */
    public void add_chief_by_admin() throws SQLException {
        Connection connection = new MySqlConnector().connection();
        if (connection == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("系统出错！");
            System.out.println("诊断页面系统连接有误！");
            alert.showAndWait();
            return;
        }
        //输入值预处理
        int age = Integer.valueOf(CAgeField.getText());
        int sex = 1;
        if (Cfamale_rbtn.isSelected()) {
            sex = 0;
        }
        int work = 5;
        if (d_1.isSelected()) {
            work = 1;
        }
        if (d_2.isSelected()) {
            work = 2;
        }
        if (d_3.isSelected()) {
            work = 3;
        }
        if (d_4.isSelected()) {
            work = 4;
        }
        String addUserSql = "insert into chief (UserName, Password, Age, Sex, RealName, WorkTime, Work) VALUES (?,?,?,?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(addUserSql);
        preparedStatement.setString(1, CUserNameField.getText());
        preparedStatement.setString(2, CPassWordField.getText());
        preparedStatement.setInt(3, age);
        preparedStatement.setInt(4, sex);
        preparedStatement.setString(5, CRealNameField.getText());
        preparedStatement.setString(6, Cworktime_area.getText());
        preparedStatement.setInt(7, work);
        preparedStatement.execute();
        connection.close();
        preparedStatement.close();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("医生 " + CRealNameField.getText() + " 添加成功！");
        System.out.println(CRealNameField.getText() + "————》医生添加成功！");
        alert.showAndWait();
        return;
    }

    /**
     * 查看医生信息
     */
    public void open_chief_panel() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../FXML/chief.fxml"));
            Stage newStage = new Stage();
            newStage.setTitle("医院管理系统 | 医生信息");
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
     * 查看病人信息
     */
    public void open_user_panel() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../FXML/userInfoforAdmin.fxml"));
            Stage newStage = new Stage();
            newStage.setTitle("医院管理系统 | 病人信息");
            newStage.setScene(new Scene(root));
            newStage.setResizable(false);
            Image icon = new Image("source/hospital.png");//图标路径
            newStage.getIcons().add(icon);//图标
            newStage.show();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * 添加管理员
     */
    public void add_admin_byAdmin0() throws SQLException {
        Connection connection = new MySqlConnector().connection();
        if (connection == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("系统出错！");
            System.out.println("添加管理员页面系统连接有误！");
            alert.showAndWait();
            return;
        }
        String sql = "select WorkType from admin where ID=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, loginController.StaticAdminId);
        ResultSet rs = preparedStatement.executeQuery();
        int worktype = 1;
        while (rs.next()) {
            worktype = rs.getInt("WorkType");
        }
        if (worktype == 1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("对不起，您的权限不足。");
            System.out.println("普通管理员无法添加管理员。");
            alert.showAndWait();
            return;
        }
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../FXML/addAdmin.fxml"));
            Stage newStage = new Stage();
            newStage.setTitle("医院管理系统 | 添加管理员");
            newStage.setScene(new Scene(root));
            newStage.setResizable(false);
            Image icon = new Image("source/hospital.png");//图标路径
            newStage.getIcons().add(icon);//图标
            newStage.show();
        } catch (Exception exception) {
            exception.printStackTrace();
        }


    }

    /**
     * 添加管理员确认
     *
     * @throws SQLException
     */
    public void add_admin_confirm() throws SQLException {
        Connection connection = new MySqlConnector().connection();
        if (connection == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("系统出错！");
            System.out.println("添加管理员页面系统连接有误！");
            alert.showAndWait();
            return;
        }
        int type = 1;
        if (admintype0_rb.isSelected()) {
            type = 0;
        }
        String sql = "insert into Admin(UserName,PassWord,RealName,WorkType) values(?,?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, AdminNamefield.getText());
        preparedStatement.setString(2, AdminPswdfield.getText());
        preparedStatement.setString(3, AdminRNamefield.getText());
        preparedStatement.setInt(4, type);
        preparedStatement.execute();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("管理员添加成功。");
        System.out.println("添加管理员成功。");
        alert.showAndWait();
        preparedStatement.close();
        connection.close();
        Stage currentStage = (Stage) add_admin_btn.getScene().getWindow();
        currentStage.close();
    }

    /**
     * 打开收费页面
     */
    public void open_money_out_panel() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../FXML/moneyOut.fxml"));
            Stage newStage = new Stage();
            newStage.setTitle("医院管理系统 | 收费");
            newStage.setScene(new Scene(root));
            newStage.setResizable(false);
            Image icon = new Image("source/hospital.png");//图标路径
            newStage.getIcons().add(icon);//图标
            newStage.show();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    /**
     * 收费确认
     */
    public void  out_money_confirm() throws SQLException {
        Connection connection = new MySqlConnector().connection();
        if (connection == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("系统出错！");
            System.out.println("添加管理员页面系统连接有误！");
            alert.showAndWait();
            return;
        }
        String sqlName = "select ID,Balance from User where UserName = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlName);
        preparedStatement.setString(1,userNamefield.getText());
        ResultSet rs = preparedStatement.executeQuery();
        int name = 0;
        double balance = 0.0;
        while (rs.next()){
            name = rs.getInt("ID");
            balance = rs.getDouble("Balance");

        }
        double money = Double.valueOf(out_money_field.getText());
        if(money>balance){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("余额不足，请用户充值。");
            System.out.println("余额不足以收费。");
            alert.showAndWait();
            return;
        }
        balance -= money;
        String sql = "update User set Balance = ? where ID = ?";
        preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setDouble(1,balance);
        preparedStatement.setInt(2,name);
        preparedStatement.execute();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("收费成功！");
        System.out.println("收费成功！");
        alert.showAndWait();
        preparedStatement.close();
        connection.close();
    }

    /**
     * 管理员查看用户余额
     * @throws SQLException
     */
    public void money_check_by_admin() throws SQLException {
        Connection connection = new MySqlConnector().connection();
        if (connection == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("系统出错！");
            System.out.println("添加管理员页面系统连接有误！");
            alert.showAndWait();
            return;
        }
        String sqlName = "select RealName,Balance from User where UserName = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sqlName);
        preparedStatement.setString(1,userNamefield.getText());
        ResultSet rs = preparedStatement.executeQuery();
        String rName="";
        double balance = 0.0;
        while (rs.next()){
            rName = rs.getString("RealName");
            balance = rs.getDouble("Balance");

        }
        if(rName.equals("")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("错误");
            alert.setHeaderText("用户名输入错误。");
            System.out.println("用户名输入错误。");
            return;
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("余额");
        alert.setHeaderText(rName+"的余额为"+balance+"元。");
        System.out.println("管理员查询余额。");
        alert.showAndWait();
    }
}
