package dimka.blinb.collection.commandsHadler;

import dimka.blinb.collection.commands.help;
import dimka.blinb.collection.interfaces.ICommand;
import dimka.blinb.collection.interfaces.ICommandHandler;
import dimka.blinb.collection.utilities.CommandDispatcher;
import dimka.blinb.collection.utilities.Notification;

public class helpHandler extends help implements ICommandHandler{
    @Override
    public Notification execute(CommandDispatcher cmd, ICommand SUPER_COMMAND) throws Exception {
        return new Notification("Help help hell!");
    }
}
