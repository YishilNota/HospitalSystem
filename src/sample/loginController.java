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

/**
 * 登录控制页面Controller
 */
public class loginController {

    @FXML
    private Button close_btn; //关闭按钮
    @FXML
    private Button login_btn;//登录按钮

    @FXML
    private TextField username_field; //用户名输入
    @FXML
    private TextField pswd_field;//密码输入
    @FXML
    private RadioButton user_rb;
    @FXML
    private RadioButton admin_rb;
    @FXML
    private RadioButton doctor_rb;

    private static String UserName; //用户名
    private static String Age; //年龄
    private static String Sex; //性别
    private static String IDCARD; //身份证信息
    private static String Balance; //余额


    public static int StaticDoctorID;//医生ID
    public static int StaticUserID;//病人ID
    public static int StaticAdminId;//全局管理员ID
    public static double StaticBalance;

    private Parent root;

    public String getUserName() {
        return UserName;
    }

    /**
     * 关闭医院信息页面
     * @throws IOException
     */
    public void back_to_home() throws IOException {
        Stage currentStage = (Stage) close_btn.getScene().getWindow();
        System.out.println(currentStage.getTitle() + "——>点击关闭系统！");
        if (currentStage.getTitle() == "医院管理系统 | 医院信息") {
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
     * 用户，医生，管理员登录
     * @throws IOException
     */
    public void user_login() throws IOException {
        Connection connection = new MySqlConnector().connection();
        if (connection == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("系统出错，请稍后重试!");
            System.out.println("连接数据库失败！");
            alert.showAndWait();
        } else {
            if (username_field.getText().equals("")) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("用户名不能为空！");
                alert.showAndWait();
                return;
            }
            try {
                //判断登录类型
                //病人 医生 管理员
                String sql = "select * from user where Username=?";
                if (user_rb.isSelected()) {
                    sql = "select * from user where Username=?";
                }
                if(doctor_rb.isSelected()){
                    sql = "select * from chief where Username=?";
                }
                if(admin_rb.isSelected()){
                    sql = "select * from admin where Username=?";
                }

                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, username_field.getText());
                ResultSet rs = preparedStatement.executeQuery();
                if (!(rs.next())) { //找不到编号
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("用户名不存在!");
                    alert.showAndWait();
                    return;
                }
                String realPassword = rs.getString("Password");
                String inputPassword = pswd_field.getText();
                if (!(realPassword.equals(inputPassword))) {//密码错误
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText("密码错误!");
                    alert.showAndWait();
                } else {
                    UserName = username_field.getText();
                    Stage currentStage = (Stage) login_btn.getScene().getWindow();
                    currentStage.close(); //关闭当前窗口
                    if(user_rb.isSelected()) { //病人登录
                        StaticUserID = rs.getInt("ID");//病人ID全局设置
                        StaticBalance = rs.getDouble("Balance");//余额全局设置
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/user.fxml"));
                        root = loader.load();
                        UserController userPanelController = loader.getController();
                        userPanelController.display(rs.getString("UserName"), rs.getInt("Age"), rs.getInt("Sex"), rs.getString("IDCARD"), rs.getString("RealName"));
                        Stage newStage = new Stage();
                        newStage.setTitle("医院管理系统 | " + rs.getString("RealName"));
                        newStage.setScene(new Scene(root));
                        Image icon = new Image("source/hospital.png");//图标路径
                        newStage.getIcons().add(icon);//图标
                        newStage.setResizable(false);
                        newStage.show();
                        System.out.println("病人:"+rs.getString("RealName")+" 登录成功！");
                    }
                    if(doctor_rb.isSelected()){ //医生登录
                        StaticDoctorID = rs.getInt("ID");//医生ID全局设置
                        Parent root = FXMLLoader.load(getClass().getResource("../FXML/doctor.fxml"));
                        Stage newStage = new Stage();
                        newStage.setTitle("医院管理系统 | "+rs.getString("RealName")+" 医师");
                        newStage.setScene(new Scene(root));
                        newStage.setResizable(false);
                        Image icon = new Image("source/hospital.png");//图标路径
                        newStage.getIcons().add(icon);//图标
                        newStage.show();
                    }
                    if (admin_rb.isSelected()){
                        StaticAdminId = rs.getInt("ID");//管理员ID全局设置
                        Parent root = FXMLLoader.load(getClass().getResource("../FXML/admin.fxml"));
                        Stage newStage = new Stage();
                        newStage.setTitle("医院管理系统 | "+rs.getString("RealName")+" 管理员");
                        newStage.setScene(new Scene(root));
                        newStage.setResizable(false);
                        Image icon = new Image("source/hospital.png");
                        newStage.getIcons().add(icon);
                        newStage.show();
                    }
                    preparedStatement.close();
                    connection.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

        }

    }


}
