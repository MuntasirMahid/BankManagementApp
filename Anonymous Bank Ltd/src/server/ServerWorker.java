package server;

import java.io.*;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;



public class ServerWorker extends Thread {
    private final Socket clientSocket;
    String ID;
    String PASS;


    public ServerWorker(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    @Override
    public void run() {
        try {
            communicate();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void communicate() throws InterruptedException, IOException, SQLException {

        DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());
        DataInputStream input = new DataInputStream(clientSocket.getInputStream());
        Random random = new Random();


        String message;
        while( true ){
            String[] command = input.readUTF().split("~");
            if (command[0].equalsIgnoreCase("add")){
                System.out.println("Creating Account");
                String Name = command[1];
                String Pass = command[2];
                String Phone = command[3];
                String Address = command[4];
                String Gender = command[5];
                System.out.println(Name + Pass + Phone + Address+ Gender);

                try{
                    DataBase dataBase = new DataBase();
                    int id = random.nextInt(99)+101010;
                    String final_id = "temp";
                    boolean isUnique = dataBase.isUnique(Integer.toString(id));
                    System.out.println(isUnique);

                    while (!isUnique){
                        id = id+1;
                        isUnique = dataBase.isUnique(Integer.toString(id));
                    }
//                    sleep(8000);
                    if (isUnique){
                        final_id = Integer.toString(id);
                    }
                    dataBase.add(final_id,Name,Pass,Phone,Address,Gender);
                    output.writeUTF(final_id);
                    System.out.println(final_id);
                }
                catch (Exception e){}

                System.out.println("Account created");
            }

            else if (command[0].equalsIgnoreCase("bal")){
                System.out.println("Checking Balance");
                try{
                    DataBase dataBase = new DataBase();
                    if (dataBase.isUserVerified(command[1],command[2])){
                       double balance = dataBase.getBalance(command[1]);
                       output.writeUTF(String.valueOf(balance));
                    }
                    else  output.writeUTF("Not Verified");
                }
                catch (Exception e){
                    System.out.println("caught from server worker");
                }

            }
            else if (command[0].equalsIgnoreCase("info")){
                System.out.println("Information");
                String id =  command[1];
                DataBase dataBase = new DataBase();
                String information  =  dataBase.getUserInfo(id);
                String array[] =information.split("~");
                String  name = array[0];
                String  acId = array[1];
                String  balance =  array[2];
                String  password = array[3];
                String phone =  array[4];
                String address = array[5];
                String  gender = array[6];
                String info = name+"~"+password+"~"+phone+"~"+balance+"~"+gender+"~"+acId+"~"+address+"~";
                output.writeUTF(info);
                output.flush();

                try {

                }catch (Exception e){}


            }

            else if (command[0].equalsIgnoreCase("verify")){
                System.out.println("Verify");
                String id =  command[1];
                String pass =  command[2];
                System.out.println("recv from client id:"+id+" "+"pas:"+pass);
                DataBase dataBase = new DataBase();
                boolean isVerified = dataBase.isUserVerified(id,pass);
                if (isVerified){
                    output.writeUTF("true");
                }
                else output.writeUTF("false");
                output.flush();

                try {

                }catch (Exception e){}


            }

            else if (command[0].equalsIgnoreCase("stuffVerify")){
                System.out.println("Verify");
                String id =  command[1];
                String pass =  command[2];
                System.out.println("recv from client id:"+id+" "+"pas:"+pass);
                DataBase dataBase = new DataBase();
                boolean isVerified = dataBase.isStaffVerified(id,pass);
                if (isVerified){
                    output.writeUTF("true");
                }
                else output.writeUTF("false");
                output.flush();

                try {

                }catch (Exception e){}

            }

            else if (command[0].equalsIgnoreCase("staffinfo")){
                System.out.println("Information");
                String id =  command[1];
                DataBase dataBase = new DataBase();
                String info = dataBase.getStaffInfo(id);
                output.writeUTF(info);
                output.flush();

                try {

                }catch (Exception e){}

            }
            else if (command[0].equalsIgnoreCase("sendMoney")){
                System.out.println("Send money");
                String senderID =  command[1];
                String recipientID =  command[2];
                double amount = Double.parseDouble(command[3]);
                DataBase dataBase = new DataBase();
                boolean money_sent = dataBase.sendMoney(senderID,recipientID,amount);
                output.writeUTF(String.valueOf(money_sent));
                output.flush();

                try {

                }catch (Exception e){}
            }
            else if (command[0].equalsIgnoreCase("clientPassReset")){
                System.out.println("clientPassReset");
                String ID =  command[1];
                String PASS =  command[2];
                DataBase dataBase = new DataBase();
                boolean passReset = dataBase.clientResetPass(ID,PASS);
                output.writeUTF(String.valueOf(passReset));
                output.flush();

                try {

                }catch (Exception e){}
            }
            else if (command[0].equalsIgnoreCase("stuffPassReset")){
                System.out.println("clientPassReset");
                String ID =  command[1];
                String PASS =  command[2];
                DataBase dataBase = new DataBase();
                boolean passReset = dataBase.staffResetPass(ID,PASS);
                output.writeUTF(String.valueOf(passReset));
                output.flush();

                try {

                }catch (Exception e){}
            }
            else if (command[0].equalsIgnoreCase("withdraw")){
                System.out.println("MoneyWithdraw");
                String ID =  command[1];
                Double AMOUNT = Double.valueOf(command[2]);
                DataBase dataBase = new DataBase();
                boolean withdrawStatus = dataBase.cashOut(ID,AMOUNT);
                output.writeUTF(String.valueOf(withdrawStatus));
                output.flush();

                try {

                }catch (Exception e){}
            }
            else if (command[0].equalsIgnoreCase("deposit")){
                System.out.println("MoneyDeposit");
                String senderID  =  command[1];
                String recipientID  =  command[2];
                Double AMOUNT = Double.valueOf(command[3]);
                DataBase dataBase = new DataBase();
                boolean depositStatus = dataBase.depositMoney(senderID,recipientID,AMOUNT);
                output.writeUTF(String.valueOf(depositStatus));
                output.flush();

                try {

                }catch (Exception e){}
            }
            else if (command[0].equalsIgnoreCase("balStaff")){
                System.out.println("Checking Staff Balance");
                try{
                    DataBase dataBase = new DataBase();
                    if (dataBase.isUserVerified(command[1],command[2])){
                        double balance = dataBase.getBalanceStaff(command[1]);
                        output.writeUTF(String.valueOf(balance));
                    }
                    else  output.writeUTF("Not Verified");
                }
                catch (Exception e){
                    System.out.println("caught from server worker");
                }
            }
            else if (command[0].equalsIgnoreCase("addStaff")){
                System.out.println("Adding staff");
                String Name = command[1];
                String Pass = command[2];
                System.out.println(Name + Pass );
                try{
                    DataBase dataBase = new DataBase();
                    int id = random.nextInt(99)+101010;
                    String final_id = "temp";
                    boolean isUnique = dataBase.isUniqueStaff(Integer.toString(id));
                    System.out.println(isUnique);

                    while (!isUnique){
                        id = id+1;
                        isUnique = dataBase.isUniqueStaff(Integer.toString(id));
                    }
                    if (isUnique){
                        final_id = Integer.toString(id);
                    }
                    dataBase.add_stuff(final_id,Name,Pass);
                    output.writeUTF(final_id);
                    System.out.println(final_id);
                }
                catch (Exception e){}

                System.out.println("Staff account created");
            }
            else if (command[0].equalsIgnoreCase("depositToStaff")){
                System.out.println("MoneyDepositStaff");
                String ID  =  command[1];
                Double AMOUNT = Double.valueOf(command[2]);
                DataBase dataBase = new DataBase();
                boolean depositStatus = dataBase.depositToStaff(ID,AMOUNT);
                output.writeUTF(String.valueOf(depositStatus));
                output.flush();

                try {

                }catch (Exception e){}
            }




//            else if (command[0].equalsIgnoreCase("with")){
//                System.out.println("Withdraw");
//                System.out.println("Before: " +findUser(command[1]).bal);
//               try{
//                   findUser(command[1]).withdraw(Integer.parseInt(command[2]));
//               }
//               catch (Exception e){
//                   System.out.println("Something went wrong");
//               }
//
//            }
//            else if (command[0].equalsIgnoreCase("cashin")){
//                System.out.println("Cashin Balance");
//                try{
//                    findUser(command[1]).cashin(Integer.parseInt(command[2]));
//                }
//                catch (Exception e){}
//            }



        }

    }




}
