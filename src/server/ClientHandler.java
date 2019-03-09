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
        private final Sessions sessions;
        private final ConnectedClients connectedClients;

        ClientHandler(Socket clientSocket, ChatSession global, ConnectedClients connectedClients) {
                messageReceiver = new Receiver(clientSocket, this);
                messageSender = new Sender(clientSocket);
                sessions = new Sessions(global);
                this.connectedClients = connectedClients;
        }

        @Override
        public void run() {
                log(THREAD_START);
                messageReceiver.listeningLoop();
                sessions.exitAll(messageSender);
                messageSender.closeSender();
                log(THREAD_END);
        }

        /**
         * Creates a command object of the right kind to run the
         * instructions sent by the client.
         *
         * The command object co-ordinates the sender, session tracker and
         * connected clients objects to carry out the client's instructions.
         *
         * @param messageBox the instruction from the client to perform
         */
        @Override
        public void handle(MessageBox messageBox) {
                Action action = messageBox.getAction();
                Command command =
                        CommandFactory.buildCommand(action, messageSender, sessions, connectedClients);
                command.execute(messageBox);
        }

        @Override
        public Sessions getSessions() {
                return sessions;
        }

        @Override
        public MessageSender getMessageSender() {
                return messageSender;
        }

        private void log(String msg) {
                System.out.println(msg + Thread.currentThread());
        }
}
