import dimka.blinb.collection.utilities.*;
import dimka.blinb.collection.interfaces.*;

import java.net.Socket;
import java.net.SocketTimeoutException;
import java.sql.SQLException;

/**
 * @author DimkaBlinb
 * @version lab7.server
 * */

public class server {
    /**
     * Main class
     */
    public static void main(String[] args) throws Exception {
        CreateServer.create();
        CreateServer.commandDispatcher.getCollection().openFile("C:\\Users\\dimka\\IdeaProjects\\lab6_client\\lab_6_client\\src\\File.txt");
        try {
            ORM_API orm_API = new ORM_API();
            ORM_API.ConnectionToDB();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        while (!CreateServer.server.isClosed()) {
            Socket socket = CreateServer.server.accept();
            new Thread(new WorkWithNewUser(socket)).start();
            System.out.println("Новое подключение: "+socket.getLocalAddress()+socket.getPort());
        }
    }
}
