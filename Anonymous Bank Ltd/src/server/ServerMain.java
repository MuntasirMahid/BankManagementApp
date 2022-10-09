package server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServerMain {
    public static int uniPORT = 8899;
    public static void main(String[] args) throws SQLException {

        try {
            ServerSocket serverSocket = new ServerSocket(uniPORT);
            while (true) {
                System.out.println("Server Waiting to connect with client");
                Socket clientSocket = serverSocket.accept();
                ServerWorker serverworker = new ServerWorker(clientSocket);
                serverworker.start();
            }

        }catch (IOException e) {
            e.printStackTrace();
        }



    }


}
