package dimka.blinb.collection.utilities;

import dimka.blinb.collection.interfaces.ICommand;

import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

public class ServerReceiver {
    ForkJoinPool forkJoinPool = new ForkJoinPool();

    public ICommand receive(Socket client){
        Receiver receiver = new Receiver(client);
        Future future = forkJoinPool.submit(receiver);
        try {
            return (ICommand) future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public class Receiver implements Callable {
        private Socket client;

        public Receiver(Socket client) {
            this.client = client;
        }

        @Override
        public ICommand call() {
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(client.getInputStream());
                return (ICommand) objectInputStream.readObject();
            } catch (Exception e) {
                System.err.println("Something went wrong with receiving data from client."
                        + client.getLocalAddress() + " " + client.getPort());
                return null;
            }

        }
    }
}
