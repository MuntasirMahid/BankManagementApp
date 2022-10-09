package StuffUser;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import static StuffUser.StaffLogin.*;

public class StaffUI implements Initializable {
    String id = staffUniId;
    @FXML
    Label staff_name;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            setName();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setName() throws IOException {
        Socket socket = new Socket(uniIP,uniPORT);
        DataOutputStream out = new DataOutputStream( socket.getOutputStream());
        DataInputStream in = new DataInputStream( socket.getInputStream());
        String cmd = "staffinfo~"+id;
        out.writeUTF(cmd);
        out.flush();
        String receive = in.readUTF();
        String array[] = receive.split("~");
        String receive_name = array[0];
        socket.close();

        staff_name.setText(receive_name);
    }
    public void createAccount(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("createAccount.fxml"));
        Stage stage =  (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setTitle("Create account");
        stage.setScene(new Scene(root));
        stage.show();
    }
    public void withdraw(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("withdraw.fxml"));
        Stage stage =  (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setTitle("Money withdraw");
        stage.setScene(new Scene(root));
        stage.show();
    }
    public void depositMoney(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("depositMoney.fxml"));
        Stage stage =  (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setTitle("Money Deposit");
        stage.setScene(new Scene(root));
        stage.show();
    }
    public void stuffPassChange(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("resetPassStuff.fxml"));
        Stage stage =  (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setTitle("Password reset");
        stage.setScene(new Scene(root));
        stage.show();
    }
    public void checkBal(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("checkBalanceStaff.fxml"));
        Stage stage =  (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setTitle("Balance");
        stage.setScene(new Scene(root));
        stage.show();
    }
    public void logout(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("staffLogin.fxml"));
        Stage stage =  (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setTitle("Staff UI");
        stage.setScene(new Scene(root));
        stage.show();
    }


}
