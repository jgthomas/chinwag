package server;

import java.net.*;


class User {
        private String name;
        private String id;


        User(Socket clientSocket) {
                this.name = "you";
                this.id = setID(clientSocket);
        }

        String id() {
                return id;
        }


        void setName(String name) {
                this.name = name;
        }

        String getName() {
                return name;
        }

        private String setID(Socket clientSocket){
                return clientSocket.getInetAddress().getHostAddress() + " " + clientSocket.getPort();
        }
}
