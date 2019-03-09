package server;


import protocol.MessageBox;

interface MessageHandler extends Runnable {

        void handle(MessageBox messageBox);

        CurrentChatSessions getCurrentChatSessions();

        MessageSender getMessageSender();
}
