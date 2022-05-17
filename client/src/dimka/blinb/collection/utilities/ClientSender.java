package dimka.blinb.collection.utilities;

import dimka.blinb.collection.Enums.Color;
import dimka.blinb.collection.interfaces.ICommand;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;

public class ClientSender {
    public static Boolean SERVER_IS_CONNECTED = false;

    public static void send(ICommand o) throws SocketException, ClassNotFoundException, InterruptedException {
        try {

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(ClientReceiver.socket.getOutputStream());
            objectOutputStream.writeObject(o);

        } catch (IOException e) {
            SERVER_IS_CONNECTED = false;
            ClientSender.reconnect();
        } catch (Exception e){
            System.err.println(e.getCause());
        }
    }

    public static Boolean reconnect(){
        while (!SERVER_IS_CONNECTED)
            try {
                Socket socket = new Socket("localhost", 2228);
                // Если не вывел ошибку значит подключился
                SERVER_IS_CONNECTED = true;
                ClientReceiver.socket = socket;
                Notification.println("Connected!", Color.BLUE);

            } catch (ConnectException e) {
                Notification.println("Something went wrong, trying to reconnect!.", Color.YELLOW);
            } catch (IOException e) {
                e.printStackTrace();
            }
        return SERVER_IS_CONNECTED;
    }
}