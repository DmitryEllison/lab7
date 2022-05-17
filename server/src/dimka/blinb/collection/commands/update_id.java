package dimka.blinb.collection.commands;

import dimka.blinb.collection.interfaces.ICommand;
import dimka.blinb.collection.objects.Coordinates;
import dimka.blinb.collection.objects.Location;
import dimka.blinb.collection.objects.Route;

import java.io.IOException;
import java.io.Serializable;

public class update_id extends ICommand implements Serializable {
    static final long serialVersionUID = 12342354L;
    public Integer key;
    public Route route;
    public update_id(){
        super(3);
    }
    /**
     * Change the value of element by key (ID).
     *
     * @return
     * @throws IOException
     **/

    @Override
    public String getName() {
        return "update_id";
    }

    @Override
    public String toString(){
        return "Change the value of element by key (ID).";
    }
}
