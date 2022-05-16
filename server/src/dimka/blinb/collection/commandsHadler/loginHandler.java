package dimka.blinb.collection.commandsHadler;

import dimka.blinb.collection.commands.login;
import dimka.blinb.collection.interfaces.ICommandHandler;
import dimka.blinb.collection.utilities.CommandDispatcher;
import dimka.blinb.collection.utilities.CreateServer;
import dimka.blinb.collection.utilities.Notification;
import dimka.blinb.collection.utilities.ORM_API;

public class loginHandler extends login implements ICommandHandler {
    @Override
    public Notification execute(CommandDispatcher cmd) throws Exception{
        String password = CreateServer.PasswordCoder((String) (super.password));
        if (ORM_API.userExist(super.login, super.password))
            return new Notification("success", super.login);
        return new Notification("Login or password is incorrect!");
    }
}
