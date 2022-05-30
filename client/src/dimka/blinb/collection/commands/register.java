package dimka.blinb.collection.commands;

import dimka.blinb.collection.Enums.Color;
import dimka.blinb.collection.interfaces.ICommand;
import dimka.blinb.collection.utilities.CommandDispatcher;
import dimka.blinb.collection.utilities.Notification;

import java.io.Console;
import java.io.Serializable;
import java.util.Scanner;

public class register extends ICommand implements Serializable {
    static final long serialVersionUID = 567L;
    public String login = "";
    public String password = "";

    @Override
    public void initialize(String[] args) throws Exception{
        Notification.print("Enter login: ", Color.PURPLE);
        Scanner in = new Scanner(System.in);
        this.login = in.nextLine();
        Notification.print("Enter password: ", Color.PURPLE);
        this.password = CommandDispatcher.PasswordCoder(in.nextLine());
    }

    @Override
    public String getName() {
        return "register";
    }

    @Override
    public String toString(){
        return "Register new user";
    }
}
