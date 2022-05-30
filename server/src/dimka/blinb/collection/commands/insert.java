package dimka.blinb.collection.commands;

import dimka.blinb.collection.exception.NameIsEmpty;
import dimka.blinb.collection.exception.OutOfRange;
import dimka.blinb.collection.interfaces.ICommand;
import dimka.blinb.collection.objects.Coordinates;
import dimka.blinb.collection.objects.Location;
import dimka.blinb.collection.objects.Route;

import java.io.IOException;
import java.io.Serializable;

public class insert extends ICommand implements Serializable {
    static final long serialVersionUID = 3L;
    public Route r;
    public insert(){
        super(3);
    }
    /**
     * Insert new element by key [insert KEY {~element~}]
     * // insert NAME {Coordinates(Int,Float);from;to(Name,Float,Long);Float} : добавить новый элемент с заданным ключом
     * insert 3131 {1,1;;Чебоксары,1,1;200.5}
     * @return
     * @throws IOException
     **/

    @Override
    public String getName() {
        return "insert";
    }

    @Override
    public String toString(){
        return "Insert new element by key [insert KEY {~element~}]";
    }
}
