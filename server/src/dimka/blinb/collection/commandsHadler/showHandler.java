package dimka.blinb.collection.commandsHadler;

import dimka.blinb.collection.exception.*;
import dimka.blinb.collection.commands.show;
import dimka.blinb.collection.interfaces.ICommand;
import dimka.blinb.collection.interfaces.ICommandHandler;
import dimka.blinb.collection.utilities.CommandDispatcher;
import dimka.blinb.collection.utilities.Notification;
import dimka.blinb.collection.utilities.ORM_API;

import java.io.IOException;

public class showHandler extends show implements ICommandHandler {
    @Override
    public Notification execute(CommandDispatcher cmd, ICommand SUPER_COMMAND) throws Exception{
        if (cmd.getCollection().getLHM().size() > 0)
            return new Notification(cmd.getCollection().LHMtoString());
        else
            return new Notification("The collection is empty =( ");
    }
}
