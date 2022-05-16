package dimka.blinb.collection.utilities;

import dimka.blinb.collection.commandsHadler.*;
import dimka.blinb.collection.exception.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.BindException;
import java.net.ServerSocket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
                new loginHandler(), new registerHandler());

        try {
            server = new ServerSocket(2222);
            System.out.println("Сервер запущен");
        } catch (BindException e) {
            System.out.println("Данный порт уже занят,возможно сервер уже запущен!\n Принудительно завершаю работу.");
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
                            System.out.println("Завершаю работу.");
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

    public static String PasswordCoder(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

}
