package client;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientLogin extends Application implements Initializable {
    static String uniID;
    static String uniPass;
    static String  uniIP = "localhost";
    static int uniPORT = 8899;
    String host;
    String port;
    @FXML
    TextField id;
    @FXML
    PasswordField pass;
    @FXML
    Button login;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("clientLogin.fxml"));
        primaryStage.setTitle("Client login");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public void login(ActionEvent e) throws IOException {
        if (!id.getText().isEmpty() && !pass.getText().isEmpty()){
            Socket socket = new Socket(uniIP,uniPORT);
            DataOutputStream out = new DataOutputStream( socket.getOutputStream());
            DataInputStream in = new DataInputStream( socket.getInputStream());
            String cmd = "verify~"+id.getText()+"~"+pass.getText()+"~";
            uniID = id.getText();
            uniPass = pass.getText();
            out.writeUTF(cmd);
            out.flush();
            String receive = in.readUTF();
            if (receive.equals("true")){
                System.out.println("logedin");
                Parent root = FXMLLoader.load(getClass().getResource("clientUI.fxml"));
                Stage stage =  (Stage) ((Node) e.getSource()).getScene().getWindow();
                stage.setTitle("Client UI");
                stage.setScene(new Scene(root));
                stage.show();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Something went wrong !");
                alert.setContentText("Please check the info and try again ");
                alert.showAndWait();

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
