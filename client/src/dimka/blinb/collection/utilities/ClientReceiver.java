package dimka.blinb.collection.utilities;

import dimka.blinb.collection.utilities.Notification;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;


public class ClientReceiver {
    /**
     * The constant client.
     */
    public static Socket socket;
    public static ObjectInputStream objectInputStream;

    public static Notification receive() throws IOException, ClassNotFoundException, SocketTimeoutException, InterruptedException {
        try{
            socket.setSoTimeout(2500);
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            Object obj = objectInputStream.readObject();
            Notification notification = (Notification) obj;

            return notification;
        } catch (SocketException e){
            System.err.println("Server is disconnected!");
        }
        return null;
    }
}

