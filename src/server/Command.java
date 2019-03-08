package server;

import protocol.MessageBox;

abstract class Command {
        private final MessageSender messageSender;
        private final SessionTracker sessionTracker;
        private final ConnectedClients connectedClients;

        Command(MessageSender messageSender,
                SessionTracker sessionTracker,
                ConnectedClients connectedClients)
        {
                this.messageSender = messageSender;
                this.sessionTracker = sessionTracker;
                this.connectedClients = connectedClients;
        }

        MessageSender getMessageSender() {
                return messageSender;
        }

        SessionTracker getSessionTracker() {
                return sessionTracker;
        }

        ConnectedClients getConnectedClients() {
                return connectedClients;
        }

        abstract void execute(MessageBox messageBox);
}
