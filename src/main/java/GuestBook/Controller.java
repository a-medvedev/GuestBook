package GuestBook;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class Controller implements IGuestBookController {
    private Connection link;
    private PreparedStatement addStatement;
    private PreparedStatement listStatement;
    private boolean isReady = false;

    //Данные для подключения
    private String login = "gb_admin", password = "test";

    public Controller(){
        String query = "jdbc:h2:mem:posts";
        try {
            Class.forName("org.h2.Driver");
            link = DriverManager.getConnection(query, login, password);
            createTable();
            prepareStatements();
            isReady = true;
        } catch (SQLException e) {
            System.out.println("Произошла ошибка работы с СУБД.");
            isReady = false;
            throw new RuntimeException();
        } catch (ClassNotFoundException e) {
            System.out.println("Драйвер БД не загружен.");
            isReady = false;
        }
    }

    private void createTable() throws SQLException {
        link.createStatement().executeUpdate(
            "CREATE TABLE `posts`" +
            "(`id` INT NOT NULL AUTO_INCREMENT," +
            "`user` TEXT," +
            "`post` TEXT," +
            "`date` FLOAT," +
            "PRIMARY KEY  (`id`))"
        );
    }

    private void prepareStatements() throws SQLException {
        if (link != null){
            addStatement = link.prepareStatement("INSERT INTO posts (user, date, post) VALUES (?, ?, ?)");
            listStatement = link.prepareStatement("SELECT * FROM posts");
        }
    }

    @Override
    public void addRecord(String message, String user) {
        if (link != null){
            try {
                addStatement.setLong(2, new Date().getTime());
                addStatement.setString(1, user);
                addStatement.setString(3, message);
                addStatement.execute();
            } catch (SQLException e) {
                System.out.println("Произошла ошибка добавления сообщения.");
                throw new RuntimeException();
            }
        }
    }

    @Override
    public List<Record> getRecords() {
        List<Record> records = new ArrayList<Record>();
        try {
            ResultSet rs = listStatement.executeQuery();
            while(rs.next()){
                Record r = new Record();
                r.id = rs.getInt("id");
                r.date = rs.getLong("date");
                r.message = rs.getString("post");
                r.user = rs.getString("user");
                records.add(r);
            }
        } catch (SQLException e) {
            System.out.println("Произошла ошибка чтения сообщений.");
            throw new RuntimeException();
        }
        return records;
    }

    public boolean isready(){
        return this.isReady;
    }

    public void closeConnections(){
        try {
            if (addStatement != null){
                addStatement.close();
            }
            if(listStatement != null){
                listStatement.close();
            }
            if (link != null){
                link.close();
            }
        } catch (SQLException e) {
            System.out.println("Работа с БД завершена некорректно.");
            throw new RuntimeException();
        }
    }
}
