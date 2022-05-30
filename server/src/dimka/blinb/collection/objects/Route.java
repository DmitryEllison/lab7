package dimka.blinb.collection.objects;

import dimka.blinb.collection.exception.NameIsEmpty;
import dimka.blinb.collection.exception.OutOfRange;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * All information about our object
 * */

public class Route implements Comparable<Route>, Serializable {
    static final long serialVersionUID = 1L;
    private Integer id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private String creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Location from; //Поле может быть null
    private Location to; //Поле не может быть null
    private float distance; //Значение поля должно быть больше 1
    private String login = "";

    public Route(){

    };
    public Route(String[] set) throws NameIsEmpty, OutOfRange, NullPointerException{
        // For example Route r = new Route("Way 90", coordinates, from, to, 10F)
        if (Objects.isNull(set[0]) || Objects.isNull(set[2]) || Objects.isNull(set[5]) || Objects.isNull(set[3])){
            throw new NullPointerException("Some variables is null!");
        }

        setName(set[0]);
        this.id = Integer.valueOf(set[1]);
        this.coordinates = new Coordinates(Long.parseLong(set[2].split(",")[0]), Float.parseFloat(set[2].split(",")[1]));
        this.creationDate = set[3];
        this.from = setLocation(set[4].split(","));
        this.to = setLocation(set[5].split(","));
        this.distance = Float.parseFloat(set[6]);
    }

    public Route(String name, Coordinates coordinates, Location from, Location to, Float distance, String login) throws NameIsEmpty, OutOfRange, NullPointerException {
        if (Objects.isNull(name) || Objects.isNull(coordinates) || Objects.isNull(to)){
            throw new NullPointerException("Some variables is null!");
        }
        if (name == ""){
            throw new NameIsEmpty("Name is empty!");
        }
        if(distance <= 1){
            throw new OutOfRange("The distance cannot be less than 1");
        }

        this.name = name;
        this.creationDate = setLocalDate();
        this.coordinates = coordinates;
        this.from = from;
        this.to = to;
        this.distance = distance;
        // Добавить проверку уникальности и сделать положительным id
        this.id = createID();
        this.login = login;
    }

    @Override
    public String toString() {
        try {
            return   name + ";" + id + ";" + coordinates.toString() +
                    ";" + creationDate.toString() + ";" + from.toString() +
                    ";" + to.toString() + ";" + distance + ";" + login;
        } catch (NullPointerException e){
            return  name + ";" + id + ";"  + coordinates.toString() +
                    ";" + creationDate.toString() + ";" + "null" +
                    ";" + to.toString() + ";" + distance;
        }
    }

    /**
     * Getters and Setters for variables
     * **/

    public Integer createID(){
        return Math.abs(Objects.hash(name, coordinates, creationDate, from, to, distance));
    }

    public void setID(Integer id){
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return Float.compare(route.distance, distance) == 0 && id.equals(route.id) && name.equals(route.name) && coordinates.equals(route.coordinates) && creationDate.equals(route.creationDate) && Objects.equals(from, route.from) && to.equals(route.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, coordinates, creationDate, from, to, distance);
    }

    public int getID() {
        return id;
    }

    private String setLocalDate(){
        // Set the current time
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public void setCreationDate(Timestamp time){
        this.creationDate = time.toString();
    }

    public String getCreationDate(){
        return creationDate.toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) throws NameIsEmpty{
        if (name == "" || name == null)
            this.name = "Empty Name!";
        else
            this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public Location getFrom() {
        return from;
    }

    public Location setLocation(String[] str){
        if (str.length == 3){
            return new Location2D(str[0], Float.parseFloat(str[1]), Integer.parseInt(str[2]));
        }else if(str.length == 4){
            return new Location3D(str[0], Float.parseFloat(str[1]), Long.parseLong(str[2]), Integer.parseInt(str[3]));
        }
        return null;
    }

    public void setFrom(Location from) {
        this.from = from;
    }

    public Location getTo() {
        return to;
    }

    public void setTo(Location to) {
        this.to = to;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) throws OutOfRange{
        if(distance <= 1)
            throw new OutOfRange("The distance cannot be less than 1");
        else
            this.distance = distance;
    }

    public String getLogin(){ return this.login; }

    public void setLogin(String login){ this.login = login; }

    @Override
    public int compareTo(Route o) {
        return 0;
    }
}
