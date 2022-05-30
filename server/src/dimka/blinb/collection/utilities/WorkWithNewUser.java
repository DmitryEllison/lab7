package dimka.blinb.collection.utilities;

import dimka.blinb.collection.Enums.Color;
import dimka.blinb.collection.interfaces.ICommand;

import java.net.Socket;
import java.net.SocketException;

public class WorkWithNewUser implements Runnable{
    private Socket clientSocket;
    private Boolean WORKING_WITH_USER = true;
    public static String USER_LOGIN = "";
    public WorkWithNewUser(Socket client) {
        this.clientSocket = client;
    }

    @Override
    public void run() {
        ServerSender serverSender = new ServerSender();
        ServerReceiver serverReceiver = new ServerReceiver();
        try {
            ICommand cmd;
            // while we don't know user's name

            while (WORKING_WITH_USER) {
                try {
                    cmd = (ICommand) serverReceiver.receive(clientSocket);
                    ORM_API.loadCollection();

                    // The command has been delivered
                    Notification.println(">> " + cmd.toString(), Color.BLUE);
                    Notification notification = CreateServer.commandDispatcher.handle(cmd);

                    if (notification != null)
                        serverSender.send(clientSocket, notification);
                    else {
                        Notification.println("Client is disconnected!", Color.YELLOW);
                        WORKING_WITH_USER = false;
                    }

                }catch (SocketException e){
                    clientSocket.close();
                    Notification.println("Client is disconnected!", Color.BLUE);
                    WORKING_WITH_USER = false;
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            Notification.println("Caught exception in working with User: " + clientSocket.getLocalAddress() + " "
                    + clientSocket.getPort(),Color.RED);
        }
    }
}
