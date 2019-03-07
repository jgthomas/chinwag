package server;

import protocol.MessageBox;

abstract class Command {
        private final MessageSender messageSender;
        private final SessionTracker sessionTracker;

        Command(MessageSender messageSender, SessionTracker sessionTracker) {
                this.messageSender = messageSender;
                this.sessionTracker = sessionTracker;
        }

        MessageSender getMessageSender() {
                return messageSender;
        }

        SessionTracker getSessionTracker() {
                return sessionTracker;
        }

        abstract void execute(MessageBox messageBox);
}
