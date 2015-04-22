package ru.chat.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.dao.DataAccessException;
import ru.chat.model.ChatItem;
import ru.chat.service.IChatService;

public class TestDataGenerator {

    private static final DateFormat dateFormat = new SimpleDateFormat("dd.MM.yy HH:mm");

    private static Date getDateFromString(String date) throws ParseException {
        return dateFormat.parse(date);
    }
    
    public static void fillTestData(IChatService chatStorage) throws DataAccessException {
        String[] createDTs = {"27.03.15 10:00", "28.03.15 11:00", "28.03.15 12:00", "29.03.15 13:00", "29.03.15 14:00",
                "30.03.15 10:00", "30.03.15 10:20", "30.03.15 10:30", "31.03.15 10:30", "31.03.15 13:40",
                "31.03.15 13:45"};
        String[] users = {"vasya", "kolya", "masha", "ira", "gosha",
                "vasya", "ira", "kolay", "masha", "vasya",
                "katya"};

        for (int i=0;i<users.length;i++) {
            try {
                Date date = getDateFromString(createDTs[i]);

                ChatItem chatItem = new ChatItem();
                chatItem.setId(i);
                chatItem.setCreateDT(new Date());
                chatItem.setUser(users[i]);
                chatItem.setMessage(String.format("Test message from [%s] %d.", users[i], i));

                chatStorage.addChatItem(chatItem);
            } catch (ParseException e) {
                //NOP
            }
        }
    }
}
