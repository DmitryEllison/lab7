package dimka.blinb.collection.commandsHadler;

import dimka.blinb.collection.commands.login;
import dimka.blinb.collection.interfaces.ICommand;
import dimka.blinb.collection.interfaces.ICommandHandler;
import dimka.blinb.collection.utilities.*;

public class loginHandler extends login implements ICommandHandler {
    @Override
    public Notification execute(CommandDispatcher cmd, ICommand SUPER_COMMAND) throws Exception{
        login super_command = (login) SUPER_COMMAND;
        if (ORM_API.userExist(super_command.login, super_command.password)){
            WorkWithNewUser.USER_LOGIN = super_command.login;
            return new Notification("success", super_command.login);
        }
        return new Notification("Login or password is incorrect!");
    }
}
