package dimka.blinb.collection.utilities;

import dimka.blinb.collection.interfaces.ICommand;

import java.net.Socket;

public class WorkWithNewUser implements Runnable{
    private Socket clientSocket;
    private String newuser = "";
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
            while (newuser.equals("")) {
                cmd = (ICommand) serverReceiver.receive(clientSocket);
                Notification notification = CreateServer.commandDispatcher.handle(cmd);
                if(notification.message == "success")
                    this.newuser = notification.getLogin();
            }

            while (true) {
                try{
                    cmd = serverReceiver.receive(clientSocket);
                    // The command has delivered
                    System.out.println(cmd.toString());
                    Notification notification = CreateServer.commandDispatcher.handle(cmd);
                    serverSender.send(clientSocket, notification);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (NullPointerException e) {
            System.out.println("Client:\t" + clientSocket.getLocalAddress()+ "\t" + clientSocket.getPort() + " disconnected.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
