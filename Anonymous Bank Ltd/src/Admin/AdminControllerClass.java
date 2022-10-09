package Admin;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.mail.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class AdminControllerClass  {

    String IP = "localhost";
    int PORT = 8899;
    @FXML
    TextField staffIDField;
    @FXML
    TextField amountField;
    @FXML
    TextField staffNameField;
    @FXML
    TextField staffPassField;

    static String generatedID;
    static String staffPass;

    public void addStaff(ActionEvent e) throws IOException {
        if (!staffNameField.getText().isEmpty() && !staffPassField.getText().isEmpty()){
            Socket socket = new Socket("localhost",8899);
            DataOutputStream out = new DataOutputStream( socket.getOutputStream());
            DataInputStream in = new DataInputStream( socket.getInputStream());
            String cmd = "addStaff~"+staffNameField.getText()+"~"+staffPassField.getText()+"~";
            out.writeUTF(cmd);
            out.flush();
            String rcv =  in.readUTF();
            System.out.println(rcv);
            System.out.println("bye");
            generatedID = rcv;
            staffPass = staffPassField.getText();
            Parent root = FXMLLoader.load(getClass().getResource("afterSuccessAccountCreate.fxml"));
            Stage stage =  (Stage) ((Node) e.getSource()).getScene().getWindow();
            stage.setTitle("Deposit");
            stage.setScene(new Scene(root));
            stage.show();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty Text Field");
            alert.setHeaderText("Empty Text field");
            alert.setContentText("Please fill up every Text Field ");
            alert.showAndWait();
        }

    }
    public boolean isNumeric(String tempAmount) {
        try {
            double d = Double.parseDouble(tempAmount);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public void depositToStaff() throws IOException {
        if (!staffIDField.getText().isEmpty() && !amountField.getText().isEmpty()){
            if (isNumeric(amountField.getText())){
                Socket socket = new Socket("localhost",8899);
                DataOutputStream out = new DataOutputStream( socket.getOutputStream());
                DataInputStream in = new DataInputStream( socket.getInputStream());
                String cmd = "depositToStaff~"+staffIDField.getText()+"~"+amountField.getText()+"~";
                out.writeUTF(cmd);
                out.flush();
                String rcv =  in.readUTF();
                System.out.println(rcv);
                System.out.println("bye");

                staffIDField.setText("");
                amountField.setText("");

                if (rcv.equalsIgnoreCase("true")){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Money Deposit");
                    alert.setHeaderText(null);
                    alert.setContentText("Money deposited successfully !");
                    alert.showAndWait();
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Deposit Failed");
                    alert.setHeaderText("Deposit failed");
                    alert.setContentText("Something went wrong ! Please try again");
                    alert.showAndWait();
                }
            }
            else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid amount");
                alert.setHeaderText("Invalid amount");
                alert.setContentText("Please enter valid amount and try again");
                alert.showAndWait();
            }

        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Empty Text Field");
            alert.setHeaderText("Empty Text field");
            alert.setContentText("Please fill up every Text Field ");
            alert.showAndWait();
        }


    }

    public void goToAdmin(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("admin.fxml"));
        Stage stage =  (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setTitle("Admin Panel");
        stage.setScene(new Scene(root));
        stage.show();
    }
    public void goToDeposit(ActionEvent e) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("deposit.fxml"));
        Stage stage =  (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setTitle("Deposit");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void goToAddStaff(ActionEvent e) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("addStaff.fxml"));
        Stage stage =  (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setTitle("Create staff account");
        stage.setScene(new Scene(root));
        stage.show();
    }


}
