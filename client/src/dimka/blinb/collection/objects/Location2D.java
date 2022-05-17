package dimka.blinb.collection.objects;

import java.io.Serializable;

public class Location2D extends Location implements Serializable {
    static final long serialVersionUID = 3L;
    Location2D(String name, Float x, Integer y){
        super(name, x, y);
    }
}
