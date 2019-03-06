package server;

import java.net.*;


class User {
        private String name;
        private String username;

        User(String username) {
                this.name = null;
                this.username = username;
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
