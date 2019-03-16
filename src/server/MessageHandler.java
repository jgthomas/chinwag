package server;


import protocol.MessageBox;

public interface MessageHandler extends Runnable {

        void handle(MessageBox messageBox);

        UserState getUserState();

        MessageSender getMessageSender();
}
