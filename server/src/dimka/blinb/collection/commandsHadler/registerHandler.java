package dimka.blinb.collection.commandsHadler;

import dimka.blinb.collection.commands.register;
import dimka.blinb.collection.interfaces.ICommand;
import dimka.blinb.collection.interfaces.ICommandHandler;
import dimka.blinb.collection.utilities.*;

public class registerHandler extends register implements ICommandHandler {
    @Override
    public Notification execute(CommandDispatcher cmd, ICommand SUPER_COMMAND) throws Exception{
        String password = CreateServer.PasswordCoder((String) (super.password));
        if (ORM_API.addNewUser(super.login, super.password))
            return new Notification("success", super.login);
        return new Notification("Something went wrong in\n\tregisterHandler.java");
    }
}
