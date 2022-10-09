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


public class ClientUI implements Initializable {
    String idU = uniID;
    String passU = uniPass;
    @FXML
    TextField name;
    @FXML
    TextField id;
    @FXML
    TextField pass;
    @FXML
    TextField phone;
    @FXML
    TextField address;
    @FXML
    TextField gender;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            inIt();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    void inIt() throws IOException {

        Socket socket = new Socket(uniIP,uniPORT);
        DataOutputStream out = new DataOutputStream( socket.getOutputStream());
        DataInputStream in = new DataInputStream( socket.getInputStream());
        String cmd = "info~"+idU+"~"+passU+"~";
        out.writeUTF(cmd);
        out.flush();
        String rcv =  in.readUTF();
        String info[] =rcv.split("~");

        String rcv_name = info[0];
        String rcv_pass = info[1];
        String rcv_phone = info[2];
        String rcv_balance = info[3];
        String rcv_gender = info[4];
        String rcv_id = info[5];
        String rcv_address = info[6];

//        name+"~"+password+"~"+phone+"~"+balance+"~"+gender+"~"+address;

        System.out.println(rcv);
        System.out.println("bye");

        name.setText(rcv_name);
        id.setText(rcv_id);
        phone.setText(rcv_phone);
        pass.setText(rcv_pass);
        gender.setText(rcv_gender);
        address.setText(rcv_address);
        socket.close();
    }

    public void checkBal(ActionEvent e) throws IOException {
        System.out.println("Check balance");
        Parent root = FXMLLoader.load(getClass().getResource("checkBalance.fxml"));
        Stage stage =  (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setTitle("Balance");
        stage.setScene(new Scene(root));
        stage.show();
    }
    public void sendMoney(ActionEvent e) throws IOException {
        /*try {
            mailNotify.sendMail("muntasiir.mahid@gmail.com");
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }*/
        System.out.println("Check balance");
        Parent root = FXMLLoader.load(getClass().getResource("sendMoney.fxml"));
        Stage stage =  (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setTitle("Send Money");
        stage.setScene(new Scene(root));
        stage.show();
    }
    public void passReset(ActionEvent e) throws IOException {
        System.out.println("Check balance");
        Parent root = FXMLLoader.load(getClass().getResource("resetPass.fxml"));
        Stage stage =  (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setTitle("Password reset");
        stage.setScene(new Scene(root));
        stage.show();
    }
    public void logout(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("clientLogin.fxml"));
        Stage stage =  (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setTitle("Client UI");
        stage.setScene(new Scene(root));
        stage.show();
    }

}
