package server;

import protocol.Action;
import protocol.MessageBox;

import java.net.*;

/**
 * Acts as a 'mini-server' for a single thread.
 *
 * Creates two objects, one to handle receiving data, and one to
 * handle sending data out. These objects implement the MessageReceiver and
 * MessageSender interfaces, respectively.
 *
 * Third object tracks the current chat sessions for this thread.
 *
 * */
class ClientHandler implements MessageHandler {
        private static final String THREAD_START = "Connected to client using: ";
        private static final String THREAD_END = "Client connection terminated: ";
        private final MessageReceiver messageReceiver;
        private final MessageSender messageSender;
        private final SessionTracker sessionTracker;

        ClientHandler(Socket clientSocket, ChatContext global, ConnectedClients connectedClients) {
                messageReceiver = new Receiver(clientSocket, this);
                messageSender = new Sender(clientSocket);
                sessionTracker = new Sessions(messageSender, global, connectedClients);
        }

        @Override
        public void run() {
                log(THREAD_START);
                messageReceiver.listeningLoop();
                sessionTracker.exitAll();
                messageSender.closeSender();
                log(THREAD_END);
        }

        @Override
        public void handle(MessageBox messageBox) {
                Action action = messageBox.getAction();
                Command command =
                        CommandFactory.buildCommand(action, messageSender, sessionTracker);
                command.execute(messageBox);
        }

        private void log(String msg) {
                System.out.println(msg + Thread.currentThread());
        }
}
