package dimka.blinb.collection.commands;

import dimka.blinb.collection.interfaces.ICommand;

import java.io.Serializable;

public class register extends ICommand implements Serializable {
    static final long serialVersionUID = 567L;
    public String login = "";
    public String password = "";

    @Override
    public String getName() {
        return "register";
    }

    @Override
    public String toString(){
        return "Register new user";
    }
}
