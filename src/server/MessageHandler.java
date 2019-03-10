package server;


import protocol.MessageBox;

interface MessageHandler extends Runnable {

        void handle(MessageBox messageBox);

        UserChatSessions getUserChatSessions();

        MessageSender getMessageSender();
}
