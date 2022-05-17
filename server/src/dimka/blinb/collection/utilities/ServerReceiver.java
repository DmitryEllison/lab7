package dimka.blinb.collection.utilities;

import dimka.blinb.collection.Enums.Color;
import dimka.blinb.collection.commands.help;
import dimka.blinb.collection.interfaces.ICommand;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

public class ServerReceiver {
    ForkJoinPool forkJoinPool = new ForkJoinPool();

    public Object receive(Socket client) throws IOException {
        Receiver receiver = new Receiver(client);
        Future future = forkJoinPool.submit(receiver);
        try {
            return future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        Notification.print("IN SERVER_RECEIVER RETURNED NULL", Color.RED);
        return null;
    }

    public class Receiver implements Callable {
        protected Socket client;
        protected ObjectInputStream objectInputStream;

        public Receiver(Socket client) throws IOException {
            this.client = client;
            objectInputStream = new ObjectInputStream(client.getInputStream());
        }

        @Override
        public ICommand call() {
            try {
                ICommand cmd = (ICommand) objectInputStream.readObject();
                return cmd;
            } catch (Exception e) {
                Notification.print("Something went wrong with receiving data from client."
                        + client.getLocalAddress() + " " + client.getPort(), Color.RED);
                return null;
            }
        }
    }
}
