package server;


import protocol.MessageBox;

interface MessageSender {

        void postMessage(ChatContext chatContext, MessageBox messageBox);

        void sendMessage(MessageBox messageBox);

        void closeSender();

        String id();

        void setUserName(String userName);

        String getUserName();
}
