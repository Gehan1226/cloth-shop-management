import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.controller.Admin.AdminDashboardFormController;
import org.example.controller.User.UserDashboardFormController;

public class Main extends Application {
    public static void main(String[] args) {
        launch();
    }
    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("view/userDashboardForm.fxml"))));
        stage.show();
        UserDashboardFormController.primaryStage = stage;
    }
}