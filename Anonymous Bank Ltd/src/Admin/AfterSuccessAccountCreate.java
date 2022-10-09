package Admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static Admin.AdminControllerClass.generatedID;
import static Admin.AdminControllerClass.staffPass;

public class AfterSuccessAccountCreate implements Initializable {
    @FXML
    Label rcvIDLabel;

    @FXML
    Label rcvPassLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        rcvIDLabel.setText(generatedID);
        rcvPassLabel.setText(staffPass);
    }

    public void goToAdmin(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("admin.fxml"));
        Stage stage =  (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setTitle("Admin");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void createAgain(ActionEvent e) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("addStaff.fxml"));
        Stage stage =  (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setTitle("Create account");
        stage.setScene(new Scene(root));
        stage.show();

    }
    public void back(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("admin.fxml"));
        Stage stage =  (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setTitle("Admin Panel");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
