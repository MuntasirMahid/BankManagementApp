package StuffUser;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import static StuffUser.StaffLogin.uniIP;
import static StuffUser.StaffLogin.uniPORT;

public class CreateAccount implements Initializable {
    static String generated_id;
    static String generated_pass;
    @FXML
    TextField first_name;
    @FXML
    TextField last_name;
    @FXML
    TextField phone;
    @FXML
    TextField pass;
    @FXML
    TextField address;
    @FXML
    RadioButton male;
    @FXML
    RadioButton female;
    String gender;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void create(ActionEvent e) throws IOException {
        if (male.isSelected()){
            gender = "Male";
            System.out.println("male");
        }
        else if (female.isSelected()){
            gender = "Female";
            System.out.println("female");
        }
        String name =  first_name.getText()+" "+last_name.getText();
        System.out.println(name);
        Socket socket = new Socket(uniIP,uniPORT);
        DataOutputStream out = new DataOutputStream( socket.getOutputStream());
        DataInputStream in = new DataInputStream( socket.getInputStream());
        String cmd = "add~"+name+"~"+pass.getText()+"~"+phone.getText()+"~"+address.getText()+"~"+gender;
        System.out.println(cmd);
        out.writeUTF(cmd);
        out.flush();
        String receive = in.readUTF();
        generated_id = receive;
        generated_pass =pass.getText();

        Parent root = FXMLLoader.load(getClass().getResource("afterCreate.fxml"));
        Stage stage =  (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setTitle("Create account | Stuff");
        stage.setScene(new Scene(root));
        stage.show();

    }

    public void back(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("staffUI.fxml"));
        Stage stage =  (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setTitle("Stuff UI");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
