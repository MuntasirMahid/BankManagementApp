package client;

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

import static client.ClientLogin.*;

public class SendMoney implements Initializable {
    @FXML
    TextField sender;
    @FXML
    TextField recipient;
    @FXML
    TextField amount;
    @FXML
    Button send;
    @FXML
    Button back;

    String myID = uniID;
    String myPass = uniPass;

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

    public void back(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("clientUI.fxml"));
        Stage stage =  (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setTitle("Client UI");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void send(ActionEvent e) throws Exception {

        if (isNumeric(amount.getText())){
            Socket socket = new Socket(uniIP,uniPORT);
            DataOutputStream out = new DataOutputStream( socket.getOutputStream());
            DataInputStream in = new DataInputStream( socket.getInputStream());
            String cmd = "sendMoney~"+uniID+"~"+recipient.getText()+"~"+amount.getText()+"~";
            System.out.println(cmd);
            out.writeUTF(cmd);
            out.flush();
            String receive = in.readUTF();
            System.out.println(receive);

            if (receive.equals("true")){

                Parent root = FXMLLoader.load(getClass().getResource("afterSendMoney.fxml"));
                Stage stage =  (Stage) ((Node) e.getSource()).getScene().getWindow();
                stage.setTitle("Money sent");
                stage.setScene(new Scene(root));
                stage.show();

            }
            else if (receive.equals("false")){
                Parent root = FXMLLoader.load(getClass().getResource("afterSendMoneyFailed.fxml"));
                Stage stage =  (Stage) ((Node) e.getSource()).getScene().getWindow();
                stage.setTitle("Money sending failed");
                stage.setScene(new Scene(root));
                stage.show();
            }
        }
    }
}
