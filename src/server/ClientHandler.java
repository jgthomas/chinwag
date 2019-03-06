package server;

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
        private final ConnectionTracker connectionTracker;

        ClientHandler(Socket clientSocket, ChatContext global) {
                messageReceiver = new Receiver(clientSocket, this);
                messageSender = new Sender(clientSocket, new User(clientSocket));
                connectionTracker = new Sessions(messageSender, global);
        }

        @Override
        public void run() {
                log(THREAD_START);
                messageReceiver.listeningLoop();
                connectionTracker.exitAll();
                messageSender.closeSender();
                log(THREAD_END);
        }

        @Override
        public void handle(MessageBox messageBox) {
                Action action = messageBox.getCommand();
                Command command =
                        CommandFactory.buildCommand(action, connectionTracker);
                command.execute(messageBox);
        }

        private void log(String msg) {
                System.out.println(msg + Thread.currentThread());
        }
}
