package StuffUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import static StuffUser.StaffLogin.uniIP;
import static StuffUser.StaffLogin.uniPORT;

public class Withdraw implements Initializable {
    @FXML
    private Button backMenuButton;

    @FXML
    private TextField accountNumberField;

    @FXML
    private TextField amountField;

    @FXML
    private Button withdrawButton;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public boolean isNumeric(String tempAmount) {
        try {
            double d = Double.parseDouble(tempAmount);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public void withdrawAction(ActionEvent e) throws IOException {
        if (isNumeric(amountField.getText())){
            Socket socket = new Socket(uniIP,uniPORT);
            DataOutputStream out = new DataOutputStream( socket.getOutputStream());
            DataInputStream in = new DataInputStream( socket.getInputStream());
            String cmd = "withdraw~"+accountNumberField.getText()+"~"+amountField.getText()+"~";
            out.writeUTF(cmd);
            out.flush();
            String receive = in.readUTF();
            socket.close();

            // change scene
            if(receive.equalsIgnoreCase("true")){
                Parent root = FXMLLoader.load(getClass().getResource("afterWithdraw.fxml"));
                Stage stage =  (Stage) ((Node) e.getSource()).getScene().getWindow();
                stage.setTitle("Succeed");
                stage.setScene(new Scene(root));
                stage.show();
            }
            else {
                Parent root = FXMLLoader.load(getClass().getResource("afterWithdrawFailed.fxml"));
                Stage stage =  (Stage) ((Node) e.getSource()).getScene().getWindow();
                stage.setTitle("Withdrawal failed");
                stage.setScene(new Scene(root));
                stage.show();
            }
        }



    }
    public void back(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("staffUI.fxml"));
        Stage stage =  (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setTitle("Stuff UI");
        stage.setScene(new Scene(root));
        stage.show();
    }

}
