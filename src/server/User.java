package server;

import java.net.*;


class User {

        // userName is username
        private String userName;
        private String screenName;
        private String id;


        User(Socket clientSocket) {
                this.userName = "you";
                this.id = setID(clientSocket);
        }

        String id() {
                return id;
        }

        void setUserName(String userName) {
                this.userName = userName;
        }

        String getUserName() {
                return userName;
        }

        private String setID(Socket clientSocket){
                return clientSocket.getInetAddress().getHostAddress() + " " + clientSocket.getPort();
        }
}
