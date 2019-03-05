package serverclient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {

    private Socket socket;
    private ObjectInputStream fromServer;
    private ObjectOutputStream toServer;

    public Client(String username, String password){
        try {
            socket = new Socket("localhost", 8888);
            fromServer = new ObjectInputStream(socket.getInputStream());
            toServer = new ObjectOutputStream(socket.getOutputStream());
            toServer.writeObject(username);
            if ((fromServer.readObject()).equals("This account does not exist.")){
                System.out.println("This account does not exist.");
                fromServer.close();
                toServer.close();
                socket.close();
            }
            else if ((fromServer.readObject()).equals("This account is locked.")){
                System.out.println("This account is locked.");
                fromServer.close();
                toServer.close();
                socket.close();
            }
            else {
                toServer.writeObject(password);
                if (fromServer.readObject().equals("The password is wrong.")){
                    System.out.println("The password is wrong.");
                    fromServer.close();
                    toServer.close();
                    socket.close();
                }
                else
                    System.out.println("Connection established.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        new Client("handsomedancerr", "234567");
    }

}
