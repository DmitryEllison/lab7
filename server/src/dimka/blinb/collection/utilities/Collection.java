package dimka.blinb.collection.utilities;

import dimka.blinb.collection.exception.AccessDenied;
import dimka.blinb.collection.exception.NameIsEmpty;
import dimka.blinb.collection.exception.OutOfRange;
import dimka.blinb.collection.interfaces.CollectionAble;
import dimka.blinb.collection.objects.Route;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class Collection implements Comparable, CollectionAble, Serializable {
    /**
     * Basic collection processing methods.
     **/
    protected LinkedHashMap<Integer, Route> LHM = new LinkedHashMap<>();
    protected static String creationTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    protected String collectionName = "MyCollection";
    private String nameOfFile = "";
    public boolean IS_WORKING = true;

    public void printLHM() {
        LHM.values().forEach((v) -> System.out.println(v));
    }

    public LinkedHashMap<Integer, Route> getLHM() {
        return LHM;
    }

    public void setLHM(LinkedHashMap<Integer, Route> LHM){
        this.LHM = LHM;
    }

    /**
     * Closing the program
     **/
    public void turnOff(){
        this.IS_WORKING = false;
    }

    /**
     * Adding new element to collection
     * @param route
     * @return
     */
    public boolean add(Route route){
        assert hasElement(route.getID()) : "The collection has the element already\nThe element has been updated!";
        this.getLHM().put(route.getID(), route);
        return true;
    }

    public void changeKey(Route route){
        route.setID(route.createID());
    }

    public Boolean update(Route route) throws AccessDenied {
        if (!hasElement(route.getID()))
            return false;
        if (this.getLHM().get(route.getID()).getLogin().compareTo(route.getLogin())==0){
            this.getLHM().remove(route.getID());
            this.add(route);
            return true;
        }
        throw new AccessDenied();
    }

    public boolean hasElement(Integer key){
        for(Map.Entry<Integer, Route> element: LHM.entrySet())
            if (element.getKey().compareTo(key) == 0)
                return true;
        return false;
    }

    /**
     * Change the name of the collection
     * @param name
     **/
    public void setName(String name){
        this.collectionName = name;
    }

    /**
     * Return information about the collection
     * @return
     **/
    public String toString(){
        return "Collection name: " + this.collectionName + "\n" +
                "Collection type: " + LHM.getClass().getName() + '\n' +
                "Server working time: " + this.creationTime + '\n' +
                "Amount of collection: " + LHM.size();
    }

    public String LHMtoString(){
        String toString ="";
        for(Map.Entry<Integer, Route> element: LHM.entrySet()){
            toString += element.toString() + '\n';
        }
        return toString;
    }

    public Integer compareTo(Route r1, Route r2){
        return r2.getID()-r1.getID();
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }

    public void writeFile() throws IOException {
        /** Write collection in the file by name of file given as parameter **/
        try {
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(nameOfFile));
            // Write elements of collection line by line
            for(Map.Entry<Integer, Route> element: getLHM().entrySet()){
                stream.write((element.getValue().toString() + "\n").getBytes());
            }
            stream.close();
        } catch (IOException e){
            // If available memory is over
            e.printStackTrace();
        }
    }

    public void openFile(String path) throws NullPointerException{
        /** Open the file.csv by name of file given as parameter and read collection **/
        try{
            File file = new File(path);
            Scanner scanner = new Scanner(file, "windows-1251");
            this.scan(scanner); // here he is writing
            scanner.close();
            this.nameOfFile = path;
        } catch(Exception e){
            e.printStackTrace();
            System.out.println("No such file exists, please enter again!");
            this.openFile(askNameOfFile());
        }
    }

    public static String askNameOfFile(){
        /** Ask name of file from user **/
        Scanner input = new Scanner(System.in, "windows-1251");
        String line = input.nextLine();
        if (line.compareTo("exit") == 0)
            System.exit(0);
        return line;
    }

    public void scan(Scanner scanner) throws NullPointerException{
        /** Put elements in collection from opened file **/
        try {
            String line;
            if (!Objects.isNull(scanner.findInLine("-skip"))) scanner.nextLine();
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                Route route = new Route(line.split(";"));
                this.add(route);
            }
        } catch (NameIsEmpty nameIsEmpty) {
            System.out.println("The name cannot be empty!");
        } catch (OutOfRange outOfRange) {
            System.out.println("Data is incorrect!");
        }
    }
}
