package server;

import java.net.*;


class User {
        private String userName;
        private String screenName;

        User(Socket clientSocket) {
                this.userName = "you";
        }

        void setUserName(String userName) {
                this.userName = userName;
        }

        String getUserName() {
                return userName;
        }
}
