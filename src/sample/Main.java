package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * 程序入口 系统开始页面
 */
public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Group root = new Group();

        Scene scene = new Scene(root, Color.LIGHTSKYBLUE);
        Image icon = new Image("source/hospital.png");//图标路径
        stage.getIcons().add(icon);//图标
        stage.setTitle("医院管理系统");//窗体名称
        stage.setWidth(800);//宽度
        stage.setHeight(600);//高度
        stage.setResizable(false);

        Text text = new Text("欢迎使用中心医院自助系统\n\t\tV1.0");
        Font font = new Font(30);
        text.setFont(font);
        text.setFill(Color.DEEPPINK);
        text.setX(230);
        text.setY(200);
        root.getChildren().add(text);

        Line line = new Line();
        line.setStartX(230);
        line.setStartY(210);
        line.setEndX(590);
        line.setEndY(210);
        line.setStrokeWidth(5);
        line.setStroke(Color.WHITE);
        line.setRotate(0);
        line.setOpacity(0.5);
        root.getChildren().add(line);

        Image image = new Image("source/h1.png");
        ImageView imageView = new ImageView(image);
        imageView.setX(350);
        imageView.setY(50);
        root.getChildren().add(imageView);

        Button button = new Button("退出系统");
        button.setTextFill(Color.WHITE);
        Font btn_font = new Font(20);
        button.setFont(btn_font);
        button.setPrefSize(200, 50);
        button.setLayoutX(300);
        button.setLayoutY(400);
        button.setStyle(" -fx-background-color: #1063d4;");
        root.getChildren().add(button);
        button.setOnAction(new EventHandler<ActionEvent>() { //关闭系统按钮
            @Override
            public void handle(ActionEvent event) {
                System.out.println("系统正在关闭！");
                try {
                    stage.close();
                } catch (Exception exception) {
                    System.out.println("系统关闭出错！");
                }
            }
        });

        Label version = new Label("TenLeon工作室提供技术支持");
        version.setTextFill(Color.GRAY);
        Font label_font = new Font(18);
        version.setFont(label_font);
        version.setLayoutX(280);
        version.setLayoutY(490);
        root.getChildren().add(version);

        Button button1 = new Button("进入系统");
        button1.setTextFill(Color.WHITE);
        button1.setFont(btn_font);
        button1.setPrefSize(200, 50);
        button1.setLayoutX(300);
        button1.setLayoutY(330);
        button1.setStyle(" -fx-background-color: #1063d4;");
        root.getChildren().add(button1);
        button1.setOnAction(new EventHandler<ActionEvent>() { //进入系统按钮
            @Override
            public void handle(ActionEvent event) {
                System.out.println("正在进入登录页面！");
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("../FXML/login.fxml"));//转到登录界面
                    Stage newStage=new Stage();
                    newStage.setTitle("医院管理系统 | 登录");
                    newStage.setScene(new Scene(root));
                    newStage.setResizable(false);
                    Image icon = new Image("source/hospital.png");//图标路径
                    newStage.getIcons().add(icon);//图标
                    newStage.show();
                    stage.close();

                } catch (IOException exception) {
                    System.out.println("进入登录页面时系统出错！");
                }

            }
        });

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
