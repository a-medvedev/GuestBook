package GuestBook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
       Controller ctrl = new Controller();
//       ctrl.addRecord("Hello", "me");
//       ctrl.addRecord("Hi", "another user");
//       ctrl.addRecord("", "");
//
//        List<Record> recs = ctrl.getRecords();
//
//        for (Record r : recs){
//            System.out.println("ID: " + r.id);
//            System.out.println("User: " + r.user + "\t\tDate: " + new Date(r.date).toString());
//            System.out.println("Message: " + r.message);
//            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++");
//        }
        System.out.println("Гостевая книга");
        System.out.println("EXIT: выход");
        System.out.println("ADD: добавляет сообщение от имени указанного пользователя ");
        System.out.println("LIST: выводит список всех сообщений в гостевой книге");
        while (true) {
            System.out.print("Введите команду: ");
            Scanner input = new Scanner(System.in);
            String query = input.nextLine();
            switch (query.toUpperCase().trim()){
                case "EXIT":
                    return;
                case "ADD":
                    System.out.print("Представьтесь, пожалуйста: ");
                    String user = input.nextLine();
                    System.out.print("Введите сообщение, закончив нажатием <Enter>: ");
                    String message = input.nextLine();
                    if (user.trim().isEmpty() || message.trim().isEmpty()){
                        System.out.println("Не введено имя или сообщение, попробуйте снова.");
                        break;
                    }
                    ctrl.addRecord(message, user);
                    System.out.println("Ваше сообщение добавлено!");
                    break;
                case "LIST":
                    List<Record> recs = ctrl.getRecords();
                    if (recs.isEmpty()){
                        System.out.println("Записи отсутствуют.");
                        break;
                    }
                    for (Record r : recs){
                        System.out.println("ID: " + r.id);
                        System.out.println("User: " + r.user + "\t\tDate: " + new Date(r.date).toString());
                        System.out.println("Message: " + r.message);
                        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++");
                    }
                    break;
                default:
                    System.out.println("Неизвестная команда.");
                    continue;
            }
        }
    }
}
