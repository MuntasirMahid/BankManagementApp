package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AfterSendMoney implements Initializable {
    @FXML
    Button sendAgain;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void again(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("sendMoney.fxml"));
           Stage stage =  (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setTitle("Send Money");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void back(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("clientUI.fxml"));
        Stage stage =  (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setTitle("Client UI");
        stage.setScene(new Scene(root));
        stage.show();
    }


}
