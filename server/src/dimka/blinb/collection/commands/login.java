package dimka.blinb.collection.commands;

import dimka.blinb.collection.interfaces.ICommand;

import java.io.Serializable;

public class login extends ICommand implements Serializable {
    static final long serialVersionUID = 0L;
    public String login = "";
    public String password = "";

    @Override
    public String getName() {
        return "login";
    }

    @Override
    public String toString(){
        return "Logging an user";
    }
}
