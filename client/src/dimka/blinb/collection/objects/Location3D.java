package dimka.blinb.collection.objects;

import java.io.Serializable;

public class Location3D extends Location implements Serializable {
    static final long serialVersionUID = 2L;
    Location3D(String name, Float x, long y, Integer z){
        super(name, x, y, z);
    }
}
