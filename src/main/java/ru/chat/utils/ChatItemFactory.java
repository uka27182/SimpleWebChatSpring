package ru.chat.utils;

import ru.chat.model.ChatItem;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatItemFactory {
    static public ChatItem getChatItem(int id, String user, String message, Date date) {
        ChatItem chatItem = new ChatItem();
        chatItem.setId(id);
        chatItem.setCreateDT(date);
        chatItem.setUser(user);
        chatItem.setMessage(message);
        return chatItem;
    }

    static public ChatItem getChatItem(int id, String user, String message) {
        return getChatItem(id, user, message, new Date());
    }

    static public ChatItem getChatItem(String user, String message) {
        return getChatItem(0, user, message, new Date());
    }

    static public List<ChatItem> getChatItemAsList(int id, String user, String message, Date date) {
        List<ChatItem> list = new ArrayList<>();
        list.add(getChatItem(id, user, message, date));
        return list;
    }
}
