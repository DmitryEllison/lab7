import dimka.blinb.collection.utilities.*;
import dimka.blinb.collection.commands.*;
import dimka.blinb.collection.interfaces.*;
import dimka.blinb.collection.Enums.Color;


import java.net.SocketTimeoutException;
import java.util.Scanner;

/**
 * @author DimkaBlinb
 * @version lab7.client
 * */

public class client {


    public static void main(String[] args) throws Exception {
        // Создание объекта который будет работать с командами
        CommandDispatcher commands = new CommandDispatcher();
        // Добавляем наши команды
        commands.addCommands(
                new execute_script(), new filter_starts_with_name(), new info(), new insert(),
                new print_field_ascending_distance(), new print_field_descending_distance(), new show(),
                new remove_key(), new remove_lower(), new update_id(),
                new history(), new help(), new exit(), new clear(), new login(), new register());
        // Начало работы с пользователем
        Scanner in = new Scanner(System.in, "windows-1251");

        Notification.println("The program is ready to work!", Color.PURPLE);
        //Notification.print("Choose the command: register or login", Color.GREEN);

        while (CommandDispatcher.IS_WORKING){
            // Подключение
            ClientSender.reconnect();
            while (ClientSender.SERVER_IS_CONNECTED){
                String s;
                try {
                    Notification.print(">> ", Color.GREEN);
                    s = in.nextLine();
                    // Создание объекта комманды с заполненными полями
                    ICommand command  = commands.handle(s.split(" "));
                    if (command != null) {
                        ClientSender.send(command);
                        try {

                            // Get data from server
                            Notification notification = ClientReceiver.receive();
                            if (notification != null){
                                System.out.println(notification.toString());
                                if (!notification.loginIsEmpty()) {
                                    CommandDispatcher.IS_LOGGED = true;
                                    commands.setLogin(notification.getLogin());
                                }
                            }
                            else
                                Notification.println("Message is empty!", Color.YELLOW);

                        } catch (SocketTimeoutException e) {

                           Notification.println("The Server is not connected or busy right now, trying reconnect...", Color.YELLOW);
                           ClientSender.SERVER_IS_CONNECTED = false;
                           ClientSender.reconnect();

                        } catch (Exception e) {

                            e.printStackTrace();
                            System.out.println(e.getCause());

                        }
                    }
                } catch (Exception e) {
                    continue;
                }

            }
        }
    }
}
