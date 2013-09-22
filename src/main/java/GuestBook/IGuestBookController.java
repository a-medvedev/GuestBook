package GuestBook;

import java.util.List;

public interface IGuestBookController {
    void addRecord(String message, String user);
    List<Record> getRecords();
}
