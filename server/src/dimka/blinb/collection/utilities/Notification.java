package dimka.blinb.collection.utilities;

import java.io.Serializable;

/**
 * That class is a message from server to the Client
 */

public class Notification implements Serializable {
    public String login = "";
    public String message = "EMPTY_STRING";

    public Notification(String message){
        this.message = message;
    }

    public Notification(String message, String login){
        this.message = message;
        this.login = login;
    }

    public void setLogin(String login){
        this.login = login;
    }

    public String getLogin(){
        return this.login;
    }

    @Override
    public String toString(){
        return this.message;
    }
}
