package server;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DataBase {
    public synchronized void add(String id, String name, String pass, String phone, String address, String gender){
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:user.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            String sql = "INSERT INTO user (Id,Name,Balance,Pass,Phone,Address,Gender) " +
                    "VALUES ('"+id+"', '"+name+"',"  +0+ ",  '"+pass+"', '"+phone+"', '"+address+"', '"+gender+"' );";
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Records created successfully");
    }

    public synchronized void add_stuff(String id, String name, String pass){
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:user.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            String sql = "INSERT INTO staff (Id,Name,Balance,Pass) " +
                    "VALUES ('"+id+"', '"+name+"',"  +0+ ",  '"+pass+"' );";
            stmt.executeUpdate(sql);
            stmt.close();
            c.commit();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Records created successfully to Staff");
    }

    public synchronized boolean isUnique(String id){
        Connection c = null;
        Statement stmt = null;
        String ID =  " \" " +id+ " \" ;";
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:user.db");
            c.setAutoCommit(false);
            System.out.println("Checking for unique id");
            stmt = c.createStatement();
            ResultSet result = stmt.executeQuery( "SELECT Id FROM user");
            int token = 0;
            if (!result.isClosed()){
                while (result.next()){
                    if (result.getString("Id").equals(id)){
                        token = 1;
                        break;
                    }
                    else token =2;
                }
            }
            stmt.close();
            c.commit();
            c.close();
            if (token ==1 ){
                return false;
            }
            else if (token ==2){
                return true;
            }
            else {
                return false;
            }

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return false;
        }
    }
    public synchronized boolean isUserVerified(String id, String pass){
        Connection c = null;
        Statement stmt = null;
        String ID =  " \" " +id+ " \" ;";
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:user.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");
            stmt = c.createStatement();
            ResultSet  resultSet = stmt.executeQuery( "SELECT * FROM user  where id = '"+id+"'" );
            System.out.println(resultSet.getString("Pass"));

            System.out.println("resultsset closedd :"+ resultSet.isClosed());
            if (!resultSet.isClosed() && resultSet.getString("Id").equals(id) && resultSet.getString("Pass").equals(pass) ){
                System.out.println("Verified");
                resultSet.close();
                stmt.close();
                c.close();
                return true;
            }
            else {
                System.out.println("not verified");
                resultSet.close();
                stmt.close();
                c.close();
                return false;
            }
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return false;
        }
    }
    public synchronized boolean isStaffVerified(String id, String pass){
        Connection c = null;
        Statement stmt = null;
        String ID =  " \" " +id+ " \" ;";
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:user.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");
            stmt = c.createStatement();
            ResultSet  resultSet = stmt.executeQuery( "SELECT * FROM staff  where id = '"+id+"'" );

            if ( !resultSet.isClosed() && resultSet.getString("Id").equals(id) && resultSet.getString("Pass").equals(pass) ){
                System.out.println("Verified");
                resultSet.close();
                stmt.close();
                c.close();
                return true;
            }
            resultSet.close();
            stmt.close();
            c.close();
            return false;

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
            return false;
        }
    }

    public synchronized int getBalance(String id){
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:user.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");
            stmt = c.createStatement();

            ResultSet  resultSet = stmt.executeQuery( "SELECT * FROM user  where id = '"+id+"'" );

            if(!resultSet.isClosed()){
                int balance = resultSet.getInt("Balance");
                resultSet.close();
                stmt.close();
                c.close();
                return balance;
            }
            return -96;

        } catch ( Exception e ) {
            System.out.println("caught from get balance");
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return 0;
        }
    }
    public synchronized void updateBal(String id, int amount){
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:user.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");
            stmt = c.createStatement();
            String sql = "UPDATE user SET Balance = "+amount+" WHERE Id = '"+id+"'";
            stmt.executeUpdate(sql);
            c.commit();
            stmt.close();
            c.commit();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Records created successfully");
    }

    public synchronized boolean cashIn(String id, double  amount){
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:user.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet result = stmt.executeQuery( "SELECT * FROM user  where id = "+id );
            double temp = result.getInt("Balance");
            double final_bal = temp+amount;
            String sql = "UPDATE user SET Balance = "+final_bal+" WHERE Id = '"+id+"'";
            stmt.executeUpdate(sql);
            c.commit();
            stmt.close();
            c.commit();
            c.close();
            System.out.println("Cashined successfully");
            return true;
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return false;
        }
    }

    public synchronized boolean cashOut(String id, double  amount){
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:user.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet result = stmt.executeQuery( "SELECT * FROM user  where id = "+id );
            if (!result.isClosed()){
                double temp = result.getInt("Balance");
                double final_bal = temp-amount;
                if (temp >= amount){
                    String sql = "UPDATE user SET Balance = "+final_bal+" WHERE Id = '"+id+"'";
                    stmt.executeUpdate(sql);
                    c.commit();
                    stmt.close();
                    c.commit();
                    c.close();
                    System.out.println("CashOut succeed");
                    return true;
                }
                else return false;
            }
            else {
                stmt.close();
                c.commit();
                c.close();
                return false;
            }


        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return false;
        }
    }

    public synchronized String getStaffInfo(String id){
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:user.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");
            stmt = c.createStatement();
            ResultSet  resultSet = stmt.executeQuery( "SELECT * FROM staff  where id = '"+id+"'" );

            if (!resultSet.isClosed()){
                String name = resultSet.getString("Name");
                String pass = resultSet.getString("pass");
                String ready = name+"~"+pass+"~";
                resultSet.close();
                stmt.close();
                c.close();
                return ready;
            }

            stmt.close();
            c.close();
            return "false";

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
            return "false";
        }
    }

    public synchronized String getUserInfo(String id){
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:user.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");
            stmt = c.createStatement();

            ResultSet  resultSet = stmt.executeQuery( "SELECT * FROM user  where id = '"+id+"'" );

            if(!resultSet.isClosed()){
                String name = resultSet.getString("Name");
                String userID = resultSet.getString("Id");
                String balance = String.valueOf(resultSet.getInt("Balance"));
                String pas = resultSet.getString("Pass");
                String address = resultSet.getString("Address");
                String gender  = resultSet.getString("Gender");
                String phone = resultSet.getString("Phone");

                String info = name+"~"+userID+"~"+balance+"~"+pas+"~"+phone+"~"+address+"~"+gender+'~';

                resultSet.close();
                stmt.close();
                c.close();
                return info;
            }
            return "Invalid ID";

        } catch ( Exception e ) {
            System.out.println("caught from get balance");
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return "Went wrong";
        }
    }

    public synchronized boolean sendMoney(String senderID, String recipientID, Double amount){
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:user.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet result = stmt.executeQuery( "SELECT * FROM user  where id = "+senderID );

            double temp=0;
            double final_bal=0;

            if (!result.isClosed()){
                temp = result.getInt("Balance");
                System.out.println(temp);
                final_bal = temp-amount;
                if (temp>=amount){
                    String sql = "UPDATE user SET Balance = "+final_bal+" WHERE Id = '"+senderID+"'";
                    result.close();
                    stmt.executeUpdate(sql);
                }
                else return false;
            }

            ResultSet result2 = stmt.executeQuery( "SELECT * FROM user  where id = '"+recipientID+"';" );
            double final_bal2=0;
            if (!result2.isClosed()){
                final_bal2 = result2.getInt("Balance") +amount;
            }
            if ( !result2.isClosed() ){
                String sql2 = "UPDATE user SET Balance = "+final_bal2+" WHERE Id = '"+recipientID+"';";
                stmt.executeUpdate(sql2);
                c.commit();
                System.out.println("Cashined successfully");
                return true;
            }
            else {
                stmt.close();
                c.close();
                return false;
            }
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return false;
        }
    }

    public synchronized boolean clientResetPass(String userID, String password) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:user.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            String pass = password;;
            String sql = "UPDATE user SET Pass = " + "'"+pass+"'" + " WHERE Id = '" + userID + "'";
            stmt.executeUpdate(sql);
            c.commit();
            stmt.close();
            c.commit();
            c.close();
            System.out.println("Password changed !");
            return true;

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return false;
        }
    }

    public synchronized boolean staffResetPass(String userID, String password) {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:user.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            String pass = password;;
            String sql = "UPDATE staff SET Pass = " + "'"+pass+"'" + " WHERE Id = '" + userID + "'";
            stmt.executeUpdate(sql);
            c.commit();
            stmt.close();
            c.commit();
            c.close();
            System.out.println("Password changed !");
            return true;
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            return false;
        }
    }

    public synchronized boolean depositMoney(String senderID, String recipientID, Double amount){
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:user.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet result = stmt.executeQuery( "SELECT * FROM staff  where id = "+senderID );

            double temp=0;
            double final_bal=0;

            if (!result.isClosed()){
                temp = result.getInt("Balance");
                final_bal = temp-amount;
                System.out.println("amount will be after deducting "+final_bal);
                if (temp>=amount){
                    String sql = "UPDATE staff SET Balance = "+final_bal+" WHERE Id = '"+senderID+"'";
                    result.close();
                    stmt.executeUpdate(sql);
                    System.out.println("Stuff balance deducted");
                }
                else return false;
            }

            ResultSet result2 = stmt.executeQuery( "SELECT * FROM user  where id = '"+recipientID+"';" );
            double final_bal2=0;
            if (!result2.isClosed()){
                final_bal2 = result2.getInt("Balance") +amount;
            }
            if ( !result2.isClosed() ){
                String sql2 = "UPDATE user SET Balance = "+final_bal2+" WHERE Id = '"+recipientID+"';";
                stmt.executeUpdate(sql2);
                c.commit();
                System.out.println("Cashined successfully");
                return true;
            }
            else {
                stmt.close();
                c.close();
                return false;
            }
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return false;
        }
    }
    public synchronized int getBalanceStaff(String id){
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:user.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");
            stmt = c.createStatement();

            ResultSet  resultSet = stmt.executeQuery( "SELECT * FROM staff  where id = '"+id+"'" );

            if(!resultSet.isClosed()){
                int balance = resultSet.getInt("Balance");
                resultSet.close();
                stmt.close();
                c.close();
                return balance;
            }
            return -96;

        } catch ( Exception e ) {
            System.out.println("caught from get balance");
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return 0;
        }
    }

    public synchronized boolean isUniqueStaff(String id){
        Connection c = null;
        Statement stmt = null;
        String ID =  " \" " +id+ " \" ;";
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:user.db");
            c.setAutoCommit(false);
            System.out.println("Checking for unique id for staff");
            stmt = c.createStatement();
            ResultSet result = stmt.executeQuery( "SELECT Id FROM staff");
            int token = 0;
            if (!result.isClosed()){
                while (result.next()){
                    if (result.getString("Id").equals(id)){
                        token = 1;
                        break;
                    }
                    else token =2;
                }
            }

            stmt.close();
            c.commit();
            c.close();

            if (token ==1 ){
                return false;
            }
            else if (token ==2){
                return true;
            }
            else {
                return false;
            }

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return false;
        }
    }

    public synchronized boolean depositToStaff(String id, double amount){
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:user.db");
            c.setAutoCommit(false);
            stmt = c.createStatement();
            ResultSet result = stmt.executeQuery( "SELECT * FROM staff  where id = "+id );

            if (!result.isClosed()){
                double temp = result.getInt("Balance");
                double final_bal = temp+amount;
                String sql = "UPDATE staff SET Balance = "+final_bal+" WHERE Id = '"+id+"'";
                stmt.executeUpdate(sql);
                c.commit();
                stmt.close();
                c.commit();
                c.close();
                System.out.println("Staff deposited successfully");
                return true;
            }
            else {
                stmt.close();
                c.commit();
                c.close();
                return false;
            }
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            return false;
        }
    }
}
