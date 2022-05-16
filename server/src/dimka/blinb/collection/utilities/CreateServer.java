package dimka.blinb.collection.utilities;

import dimka.blinb.collection.commandsHadler.*;
import dimka.blinb.collection.exception.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.BindException;
import java.net.ServerSocket;

public class CreateServer {
    public static ServerSocket server;
    public static Collection collection = new Collection();
    public static CommandDispatcher commandDispatcher;


    static {
        try {
            commandDispatcher = new CommandDispatcher(collection);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void create() throws Exception{
        CreateServer.checkForExitSaveCommand();
        // ��������� ���� �������
        commandDispatcher.addCommands(new showHandler());

        try {
            server = new ServerSocket(2222);
            System.out.println("������ �������");
        } catch (BindException e) {
            System.out.println("������ ���� ��� �����,�������� ������ ��� �������!\n ������������� �������� ������.");
            System.exit(0);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void checkForExitSaveCommand() {
        Thread backgroundReaderThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))) {
                    while (!Thread.interrupted()) {
                        String line = bufferedReader.readLine();
                        if (line == null) {
                            break;
                        }
                        if (line.equalsIgnoreCase("exit")) {
                            //commandDispatcher.SAVE; <------------------------------------------------- save me please
                            System.out.println("�������� ������.");
                            System.exit(0);
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        backgroundReaderThread.setDaemon(true);
        backgroundReaderThread.start();
    }

}
