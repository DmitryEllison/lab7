package dimka.blinb.collection.objects;

import java.io.Serializable;

public class Location2D extends Location implements Serializable {
    Location2D(String name, Float x, Integer y){
        super(name, x, (long) y);
    }
}
