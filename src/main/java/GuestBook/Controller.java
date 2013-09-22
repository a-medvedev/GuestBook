package GuestBook;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class Controller implements IGuestBookController {
    private Connection link;
    private PreparedStatement addStatement;
    private PreparedStatement listStatement;

    //Данные для подключения
    private String login = "gb_admin", password = "test";

    public Controller(){
        String query = "jdbc:h2:mem:posts";
        try {
            Class.forName("org.h2.Driver");
            link = DriverManager.getConnection(query, login, password);
            createTable();
            prepareStatements();
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (ClassNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
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

    private boolean prepareStatements() throws SQLException {
        if (link != null){
            addStatement = link.prepareStatement("INSERT INTO posts (user, date, post) VALUES (?, ?, ?)");
            listStatement = link.prepareStatement("SELECT * FROM posts");
            return true;
        }
        return false;
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
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
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
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return records;  //To change body of implemented methods use File | Settings | File Templates.
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
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
