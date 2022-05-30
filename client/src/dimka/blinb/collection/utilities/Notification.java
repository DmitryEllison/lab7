package dimka.blinb.collection.utilities;

import dimka.blinb.collection.Enums.Color;

import java.io.Serializable;

/**
 * That class is a message from server to the Client
 */
public class Notification implements Serializable {
    static final long serialVersionUID = 123L;
    private String login = "";
    public String message = "EMPTY_STRING_MESSAGE";

    public Notification(String message){
        this.message = message;
    }

    public Notification(String message, String login){
        this.message = message;
        this.login = login;
    }

    // Добавить тип сообщения - Ошибка красная, успех зеленая, предупреждение и тд..

    public void setLogin(String login){
        this.login = login;
    }

    public String getLogin(){
        return this.login;
    }

    public Boolean loginIsEmpty(){
        return (this.getLogin() == "") ? true : false;
    }

    public static void println(String message, Color color){
        System.out.println(color + message + Color.STOP);
    }
    public static void println(String message){
        System.out.println( message );
    }
    public static void print(String message, Color color){
        System.out.print(color + message + Color.STOP);
    }

    @Override
    public String toString(){
        return this.message;
    }
}
