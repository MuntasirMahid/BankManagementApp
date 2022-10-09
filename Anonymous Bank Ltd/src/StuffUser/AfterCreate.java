package StuffUser;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static StuffUser.CreateAccount.generated_id;
import static StuffUser.CreateAccount.generated_pass;

public class AfterCreate implements Initializable {
    @FXML
    Button createAgain;
    @FXML
    Label id;
    @FXML
    Label pass;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setID_Pass();
    }

    public void createAgain(ActionEvent e) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("createAccount.fxml"));
        Stage stage =  (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setTitle("Crate Account");
        stage.setScene(new Scene(root));
        stage.show();

    }

    void setID_Pass(){
        id.setText(generated_id);
        pass.setText(generated_pass);
    }

    public void back(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("staffUI.fxml"));
        Stage stage =  (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setTitle("Stuff UI");
        stage.setScene(new Scene(root));
        stage.show();
    }

}
