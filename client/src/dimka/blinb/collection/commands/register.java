package dimka.blinb.collection.commands;

import dimka.blinb.collection.interfaces.ICommand;

import java.io.Console;
import java.io.Serializable;
import java.util.Scanner;

public class register extends ICommand implements Serializable {
    static final long serialVersionUID = 0L;
    protected String login = "";
    protected String password = "";

    @Override
    public void initialize(String[] args) throws Exception{
        System.out.print("Enter login: ");
        Scanner in = new Scanner(System.in);
        this.login = in.nextLine();
        System.out.print("Enter password: ");
        this.password = in.nextLine();
        // Console cnsl = System.console();
        // this.password = String.valueOf(cnsl.readPassword("Enter password: "));
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
