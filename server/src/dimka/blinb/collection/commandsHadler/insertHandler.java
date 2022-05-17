package dimka.blinb.collection.commandsHadler;

import dimka.blinb.collection.interfaces.ICommand;
import dimka.blinb.collection.interfaces.ICommandHandler;
import dimka.blinb.collection.utilities.*;
import dimka.blinb.collection.commands.*;

public class insertHandler extends insert implements ICommandHandler {
    @Override
    public Notification execute(CommandDispatcher cmd, ICommand SUPER_COMMAND) throws Exception{
        cmd.getCollection().add(super.r);
        return new Notification("Success");
    }
}
