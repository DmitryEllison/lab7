package dimka.blinb.collection.utilities;

import dimka.blinb.collection.Enums.Color;
import dimka.blinb.collection.commandsHadler.*;

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
        // Добавляем наши команды
        commandDispatcher.addCommands(new showHandler(), new insertHandler(), new historyHandler(),
                new loginHandler(), new registerHandler(), new infoHandler(), new update_idHandler());

        try {
            server = new ServerSocket(2228);
            Notification.println("Сервер запущен", Color.PURPLE);
        } catch (BindException e) {
            Notification.println("Данный порт уже занят\nЗавершаю работу.", Color.PURPLE);
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
                            Notification.println("Сохраняю коллекцию в Базу Данных.", Color.PURPLE);
                            ORM_API.uploadCollection();
                            Notification.println("Завершаю работу.", Color.PURPLE);
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
