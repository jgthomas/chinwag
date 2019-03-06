package server;

import java.net.*;


class User {
        private String name;
        private String username;
        private Socket clientSocket;

        User(Socket clientSocket) {
                this.name = null;
                this.username = null;
                this.clientSocket = clientSocket;
        }


        void setName(String name) {
                this.name = name;
        }

        String getName() {
                return name;
        }

        String getUsername() { return username; }

        void setUsername(String username) { this.username = username; }
}
