package dimka.blinb.collection.commandsHadler;

import dimka.blinb.collection.interfaces.ICommand;
import dimka.blinb.collection.interfaces.ICommandHandler;
import dimka.blinb.collection.utilities.*;
import dimka.blinb.collection.commands.*;

public class insertHandler extends insert implements ICommandHandler {
    @Override
    public Notification execute(CommandDispatcher cmd, ICommand SUPER_COMMAND) throws Exception{
        insert super_command = (insert) SUPER_COMMAND;
        super_command.r.setLogin(WorkWithNewUser.USER_LOGIN);
        cmd.getCollection().add(super_command.r);
        ORM_API.uploadCollection();
        return new Notification("Success");
    }
}
