package server;

import java.io.*;
import java.net.*;


public class User {
        private String name;
        private String id;

        public User(Socket clientSocket) {
                this.name = "you";
                this.id = setID(clientSocket);
        }

        public String id() {
                return id;
        }

        public void setName(String name) {
                this.name = name;
        }

        public String getName() {
                return name;
        }

        private String setID(Socket clientSocket) {
                return clientSocket.getInetAddress().getHostAddress()
                        + "_"
                        + clientSocket.getPort();
        }
}
