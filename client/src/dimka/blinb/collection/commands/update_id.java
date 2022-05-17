package dimka.blinb.collection.commands;

import dimka.blinb.collection.Enums.Color;
import dimka.blinb.collection.interfaces.ICommand;
import dimka.blinb.collection.objects.Coordinates;
import dimka.blinb.collection.objects.Location;
import dimka.blinb.collection.objects.Route;
import dimka.blinb.collection.utilities.Notification;

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
    public void initialize(String[] args) throws Exception {

        try{
            // Parsing the command line
            String value = args[2].replace("{", "").replace("}", "");
            String[] element = value.split(";");

            // Create arguments for Route class
            Coordinates coordinate = new Coordinates(element[1]);
            Location from = null;
            if (!element[2].equals(""))
                from = new Location(element[2]);
            Location to = new Location(element[3]);
            Float distance = Float.valueOf(element[4]);
            String login = "DEFAULT";

            // Create instance of Route and hand to collection method on
            Route route = new Route(element[0], coordinate, from, to, distance, login);
            this.route = route;
            this.key = Integer.valueOf(args[1]);
        } catch (Exception e){
            Notification.println("Incorrect command to insert a value in the collection! \nFor example:", Color.YELLOW);
            Notification.println("update_id ID {Name, Coordinates(Int,Float);Location(Name,Float,Long);Location(Name,Float,Long);Distance(Float)}");
            // update_id 133680 {StrangeWay;4523,4342;Âÿחלא,5423,546;Êנמםגא,53424,2345;84290.5}
            throw new Exception();
        }
    }

    @Override
    public String getName() {
        return "update_id";
    }

    @Override
    public String toString(){
        return "Change the value of element by key (ID).";
    }
}
