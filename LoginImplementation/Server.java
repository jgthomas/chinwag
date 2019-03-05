package serverclient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private ServerSocket serverSocket;
    private ExecutorService threadPool;
    private Database database;

    public Server() throws SQLException {
        database = new Database();
        try {
            threadPool = Executors.newFixedThreadPool(10);
            serverSocket = new ServerSocket(8888);
            System.out.println("Waiting for clients...");
            while (true){
                Socket newClient = serverSocket.accept();
                // System.out.println("A client is attempting to connect.");
                threadPool.execute(new LoginHandler(newClient));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            database.getConnection().close();
        }
    }

    private class LoginHandler implements Runnable {
        private Socket clientSocket;
        private ObjectInputStream fromClient;
        private ObjectOutputStream toClient;

        public LoginHandler(Socket clientSocket){ this.clientSocket = clientSocket; }

        @Override
        public void run(){
            try {
                fromClient = new ObjectInputStream(clientSocket.getInputStream());
                toClient = new ObjectOutputStream(clientSocket.getOutputStream());
                String username = (String) fromClient.readObject();
                ResultSet rs = database.makeLoginQuery(username);
                rs.next();
                if (rs.getInt(3) == 3) {
                    System.out.println("This account is locked.");
                    toClient.writeObject("This account is locked.");
                    fromClient.close();
                    toClient.close();
                    clientSocket.close();
                } else {
                    toClient.writeObject("Please provide password.");
                    String revPW = (String) fromClient.readObject();
                    if (!revPW.equals(rs.getString(2))){
                        System.out.println("The password is wrong.");
                        toClient.writeObject("The password is wrong.");
                        fromClient.close();
                        toClient.close();
                        clientSocket.close();
                        database.makeAttemptsIncrement(username, rs.getInt(3));
                    }
                    else
                        System.out.println("Connection established.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                System.out.println("This account does not exist.");
                try {
                    toClient.writeObject("This account does not exist.");
                    fromClient.close();
                    toClient.close();
                    clientSocket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws SQLException {
        new Server();
    }

}
