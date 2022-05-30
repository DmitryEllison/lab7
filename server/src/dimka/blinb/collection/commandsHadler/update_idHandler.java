package dimka.blinb.collection.commandsHadler;

import dimka.blinb.collection.commands.update_id;
import dimka.blinb.collection.exception.AccessDenied;
import dimka.blinb.collection.interfaces.ICommand;
import dimka.blinb.collection.interfaces.ICommandHandler;
import dimka.blinb.collection.objects.Route;
import dimka.blinb.collection.utilities.CommandDispatcher;
import dimka.blinb.collection.utilities.Notification;
import dimka.blinb.collection.utilities.ORM_API;
import dimka.blinb.collection.utilities.WorkWithNewUser;

public class update_idHandler extends update_id implements ICommandHandler {
    @Override
    public Notification execute(CommandDispatcher cmd, ICommand tempCommand) throws Exception {
        update_id UPDATED_COMMAND = (update_id) tempCommand;
        UPDATED_COMMAND.route.setID(UPDATED_COMMAND.key);
        UPDATED_COMMAND.route.setLogin(WorkWithNewUser.USER_LOGIN);

        try {
            if (CommandDispatcher.collection.update(UPDATED_COMMAND.route)) {
                ORM_API.uploadCollection();
                return new Notification("Element with ID: " + UPDATED_COMMAND.route.getID() + " has been updated.");
            } else
                return new Notification("The collection hasn't this element!");
        }catch (AccessDenied e){
            return new Notification("Access denied");
        }
    }
}
