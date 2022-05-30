package dimka.blinb.collection.utilities;

import dimka.blinb.collection.Enums.Color;
import dimka.blinb.collection.exception.*;
import dimka.blinb.collection.interfaces.*;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;


public class CommandDispatcher {
    private String USER_lOGIN = "";
    private static Map <String, ICommand> mapOfCommands = new TreeMap<>();
    private static LinkedList<String> history = new LinkedList<String>();
    public static Boolean IS_LOGGED = false;
    public static Boolean IS_WORKING = true;

    /**
     * Adding available command
     * @param commands
     */
    public void addCommands(ICommand... commands){
        for (ICommand command: commands){
            mapOfCommands.put(command.getName(), command);
        }
    }

    public Map getMapOfCommands(){
        return mapOfCommands;
    }

    public void setLogin(String login){
        this.USER_lOGIN = login;
    }
    public String getLogin(){
        return  this.USER_lOGIN;
    }

    public LinkedList<String> getHistory(){
        return history;
    }
    private void addToHistory(String command){
        this.getHistory().addFirst(command);
        if (this.getHistory().size()>5)
            this.getHistory().removeLast();
    }

    /**
     * Handle the current command out
     */
    public ICommand handle(String[] args) throws Exception {
        ICommand command;
        try {
            String nameOfCommand = args[0];
            // Контроль корректности команды
            if(mapOfCommands.get(nameOfCommand).EXPECTED_LEN != args.length)
                throw new IncorrectCommand();
            // Вызываем метод объекта ICommand, хранящийся в коллекции по ключу (Имя Команды)
            command = mapOfCommands.get(nameOfCommand);

            if (!this.IS_LOGGED && !(nameOfCommand.compareTo("login") == 0) && !(nameOfCommand.compareTo("register") == 0)){
                Notification.println("Only logged users able to change DataBase", Color.RED);
                Notification.println("Choose \"login\" or \"register\"", Color.BLUE);
                throw new Exception();
            }

            command.initialize(args);
            // Добавляем имя команды в историю
            this.addToHistory(nameOfCommand);
            return command;
        } catch (IncorrectCommand e){
            Notification.println("The arguments of command are wrong!", Color.YELLOW);
        } catch (NullPointerException e){
            Notification.println("The command is not recognized!", Color.YELLOW);
        }
        return null;
    }

    public static String PasswordCoder(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
