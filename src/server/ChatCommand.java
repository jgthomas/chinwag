package server;


class ChatCommand extends Command {

        ChatCommand(SessionTracker sessionTracker) {
                super(sessionTracker);
        }

        @Override
        public void execute(MessageBox messageBox) {
                getMessageSender().postMessage(getCurrentChat(), messageBox);
        }

        private ChatContext getCurrentChat() {
                return getSessionTracker().getCurrentSession();
        }
}
