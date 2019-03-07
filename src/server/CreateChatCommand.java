package server;

public class CreateChatCommand extends Command {

    CreateChatCommand(MessageSender messageSender, SessionTracker sessionTracker) {
        super(messageSender, sessionTracker);
    }

    @Override
    void execute(MessageBox messageBox) {
    }

}
