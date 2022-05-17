package dimka.blinb.collection.commandsHadler;

import dimka.blinb.collection.commands.history;
import dimka.blinb.collection.interfaces.ICommand;
import dimka.blinb.collection.interfaces.ICommandHandler;
import dimka.blinb.collection.utilities.CommandDispatcher;
import dimka.blinb.collection.utilities.Notification;

public class historyHandler extends history implements ICommandHandler{
    @Override
    public Notification execute(CommandDispatcher cmd, ICommand SUPER_COMMAND) throws Exception {
        return new Notification(cmd.getHistory().toString());
    }
}
