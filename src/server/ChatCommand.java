package server;


import protocol.MessageBox;

class ChatCommand extends Command {

        ChatCommand(MessageSender messageSender, SessionTracker sessionTracker) {
                super(messageSender, sessionTracker);
        }

        @Override
        public void execute(MessageBox messageBox) {
                getMessageSender().postMessage(getCurrentChat(), messageBox);
        }

        private ChatContext getCurrentChat() {
                return getSessionTracker().getCurrentSession();
        }
}
