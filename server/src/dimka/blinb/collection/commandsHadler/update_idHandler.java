package dimka.blinb.collection.commandsHadler;

import dimka.blinb.collection.commands.update_id;
import dimka.blinb.collection.interfaces.ICommand;
import dimka.blinb.collection.interfaces.ICommandHandler;
import dimka.blinb.collection.objects.Route;
import dimka.blinb.collection.utilities.CommandDispatcher;
import dimka.blinb.collection.utilities.Notification;

public class update_idHandler extends update_id implements ICommandHandler {
    @Override
    public Notification execute(CommandDispatcher cmd, ICommand tempCommand) throws Exception{
        update_id UPDATE_ID = (update_id) tempCommand;
        UPDATE_ID.route.setID(UPDATE_ID.key);
        if (CommandDispatcher.collection.update(UPDATE_ID.route))
            return new Notification("Element with ID: " + UPDATE_ID.route.getID() + " has been updated.");
        else
            return new Notification("The collection hasn't this element!");
    }
}
