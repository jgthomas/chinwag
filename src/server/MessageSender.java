package server;


import protocol.MessageBox;

public interface MessageSender {

        void postMessage(ChatSession chatContext, MessageBox messageBox);

        void sendMessage(MessageBox messageBox);

        void closeSender();

        String id();

        void setUserName(String userName);

        String getUserName();
}
