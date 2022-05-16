package dimka.blinb.collection.utilities;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerSender {
    ExecutorService executorService = Executors.newFixedThreadPool(10);

    public void send(Socket client, Notification notification) throws Exception  {
        executorService.execute(new Sender(client, notification));
        System.out.println("Sending to client:\t" + client.getLocalAddress() + "\t" + client.getPort());
    }

    public class Sender implements Runnable{
        private Socket client;
        private Notification notification;

        public Sender(Socket client, Notification notification){
            this.client = client;
            this.notification = notification;
        }

        @Override
        public void run(){
            try{
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(client.getOutputStream());
                objectOutputStream.writeObject(notification);
                System.out.println(notification.toString());
                objectOutputStream.flush();
            }catch(Exception e){
                System.err.println("Something went wrong with sending notification.");
            }
        }
    }
}
