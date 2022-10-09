package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import static client.ClientLogin.*;

public class CheckBalance implements Initializable {
    @FXML
    TextField balance;

    String  myID = uniID;
    String myPass = uniPass;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            autoCheck();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void back(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("clientUI.fxml"));
        Stage stage =  (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setTitle("Client UI");
        stage.setScene(new Scene(root));
        stage.show();
    }
    public void checkBal() throws IOException {
        Socket socket = new Socket(uniIP,uniPORT);
        DataOutputStream out = new DataOutputStream( socket.getOutputStream());
        DataInputStream in = new DataInputStream( socket.getInputStream());
        String cmd = "bal~"+myID+"~"+myPass+"~";
        out.writeUTF(cmd);
        out.flush();
        String receive = in.readUTF();
        balance.setText(receive);
        socket.close();
    }

    public void autoCheck() throws IOException {
        Socket socket = new Socket(uniIP,uniPORT);
        DataOutputStream out = new DataOutputStream( socket.getOutputStream());
        DataInputStream in = new DataInputStream( socket.getInputStream());
        String cmd = "bal~"+myID+"~"+myPass+"~";
        out.writeUTF(cmd);
        out.flush();
        String receive = in.readUTF();
        balance.setText(receive);
        socket.close();
    }
}
