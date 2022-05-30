package dimka.blinb.collection.utilities;

import dimka.blinb.collection.interfaces.*;

import java.io.*;
import java.util.*;


public class CommandDispatcher implements ICommandDispatcher {
    private static Map <String, ICommandHandler> mapOfCommandHandler = new TreeMap<>();
    private static LinkedList<String> history = new LinkedList<String>();
    public static Collection collection;

    public CommandDispatcher(Collection collection) throws FileNotFoundException { this.collection = collection; }

    /**
     * Adding available command
     * @param commands
     */
    public void addCommands(ICommandHandler... commands){
        for (ICommandHandler command: commands){
            mapOfCommandHandler.put(command.getName(), command);
        }
    }

    /**
     * Gets collection
     *
     * @return Collection
     */
    public static synchronized Collection getCollection() { return CommandDispatcher.collection; }

    /**
     * Gets Map Of Commands that execute their super classes
     *
     * @return
     */
    public Map getmapOfCommandHandler(){
        return mapOfCommandHandler;
    }

    /**
     * Gets 5 last names of command, in other words - history
     *
     * @return LinkedList<String>
     */
    public LinkedList<String> getHistory(){
        return history;
    }

    /**
     * Adding name of command to the history
     *
     * @param command
     */
    private void addToHistory(String command){
        this.getHistory().addFirst(command);
        if (this.getHistory().size()>5)
            this.getHistory().removeLast();
    }

    /**
     * Handle the current command out
     * @return
     */
    public Notification handle(ICommand cmd){
        try {

            Notification notification = mapOfCommandHandler.get(cmd.getName()).execute(this, cmd);
            this.addToHistory(cmd.getName());
            return notification;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
