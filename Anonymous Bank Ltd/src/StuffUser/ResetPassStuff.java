package StuffUser;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import static StuffUser.StaffLogin.staffUniId;
import static StuffUser.StaffLogin.staffUniPass;
import static client.ClientLogin.*;

public class ResetPassStuff implements Initializable {
    @FXML
    TextField newPassfield;
    @FXML
    TextField newPassfield2;
    @FXML
    Button reset;

    String myID = staffUniId;
    String myPass = staffUniPass;
    String uniIP = StaffLogin.uniIP;
    int uniPORT = StaffLogin.uniPORT;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    public void back(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("staffUI.fxml"));
        Stage stage =  (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setTitle("Stuff UI");
        stage.setScene(new Scene(root));
        stage.show();
    }
    public void reset(ActionEvent e) throws IOException {
        if (!newPassfield.getText().isEmpty() && !newPassfield2.getText().isEmpty()){
            if (newPassfield.getText().equals(newPassfield2.getText())){
                Socket socket = new Socket(uniIP,uniPORT);
                DataOutputStream out = new DataOutputStream( socket.getOutputStream());
                DataInputStream in = new DataInputStream( socket.getInputStream());
                String cmd = "stuffPassReset~"+myID+"~"+newPassfield.getText()+"~";
                System.out.println(cmd);
                out.writeUTF(cmd);
                out.flush();
                String receive = in.readUTF();
                System.out.println(receive);
                if (receive.equalsIgnoreCase("true")){
                    Parent root = FXMLLoader.load(getClass().getResource("afterResetPassStuff.fxml"));
                    Stage stage =  (Stage) ((Node) e.getSource()).getScene().getWindow();
                    stage.setTitle("Stuff UI");
                    stage.setScene(new Scene(root));
                    stage.show();
                }
            }
            else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Password mismatch");
                alert.setHeaderText("Password mismatch !");
                alert.setContentText("Please give the same password to both of the field info and try again ");
                alert.showAndWait();
                newPassfield.setText("");
                newPassfield2.setText("");
            }

        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Info missing");
            alert.setHeaderText("Every field is required to be be filled up");
            alert.setContentText("Please fill all the info and try again ");
            alert.showAndWait();
        }

    }
}
