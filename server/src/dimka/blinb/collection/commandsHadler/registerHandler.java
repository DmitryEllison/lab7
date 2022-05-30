package dimka.blinb.collection.commandsHadler;

import dimka.blinb.collection.commands.register;
import dimka.blinb.collection.interfaces.ICommand;
import dimka.blinb.collection.interfaces.ICommandHandler;
import dimka.blinb.collection.utilities.*;

public class registerHandler extends register implements ICommandHandler {
    @Override
    public Notification execute(CommandDispatcher cmd, ICommand SUPER_COMMAND) throws Exception{
        register super_command = (register) SUPER_COMMAND;
        if (ORM_API.addNewUser(super_command.login, super_command.password)) {
            WorkWithNewUser.USER_LOGIN = super_command.login;
            return new Notification("success", super_command.login);
        }
        return new Notification("Something went wrong, please try again...");
    }
}
