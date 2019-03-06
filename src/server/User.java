package server;

import java.net.*;


class User {
        private String screenName;
        private String username;

        User(String username) {
                this.screenName = null;
                this.username = username;
        }

        void setName(String name) {
                this.screenName = name;
        }

        String getScreenName() {
                return screenName;
        }

        String getUsername() { return username; }

        void setUsername(String username) { this.username = username; }
}
