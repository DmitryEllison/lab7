import dimka.blinb.collection.utilities.*;
import dimka.blinb.collection.Enums.Color;

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
        CreateServer.commandDispatcher.getCollection().openFile("C:/Users/dimka/IdeaProjects/lab7/server/src/BaseCollection.txt");
        try {
            ORM_API orm_API = new ORM_API();
            ORM_API.ConnectionToDB();
            ORM_API.uploadCollection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        while (!CreateServer.server.isClosed()) {
            Socket socket = CreateServer.server.accept();
            new Thread(new WorkWithNewUser(socket)).start();
            Notification.print("Новое подключение: "+socket.getLocalAddress() + " " + socket.getPort(), Color.PURPLE);
        }
    }
}
