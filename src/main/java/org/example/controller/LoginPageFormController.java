package org.example.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.bo.BoFactory;
import org.example.bo.custom.UserBo;
import org.example.dto.User;
import org.example.util.BoType;

import java.io.IOException;
import java.net.URL;

public class LoginPageFormController {
    public static Stage primaryStage;
    public JFXTextField txtEmailAddress;
    public JFXPasswordField txtPasssword;
    private UserBo userBo = BoFactory.getInstance().getBo(BoType.USER);

    public void btnMainmenuOnAction(ActionEvent actionEvent) {
        try {
            primaryStage.close();
            URL fxmlLocation = getClass().getClassLoader().getResource("view/home_page_from.fxml");
            FXMLLoader loader = new FXMLLoader(fxmlLocation);
            Parent parent = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.show();
            HomePageFormController.primaryStage = stage;
        } catch (IOException e) {
        }

    }

    public void btnForgetpwOnAction(ActionEvent actionEvent) {
    }

    public void btnLogInOnAction(ActionEvent actionEvent) {
        User user = new User(txtEmailAddress.getText(), txtPasssword.getText(), false);
        String b = userBo.saveUser(user);
        System.out.println(b);
    }

    public void btnCreateNewAccountOnAction(ActionEvent actionEvent) {
    }


}
