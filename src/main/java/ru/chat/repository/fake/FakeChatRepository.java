package ru.chat.repository.fake;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import ru.chat.model.ChatItem;
import ru.chat.repository.IChatRepository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class FakeChatRepository implements IChatRepository {

    private static int id = 0;
    private List<ChatItem> chatItemList = new ArrayList<ChatItem>();

    private Logger logger = LoggerFactory.getLogger(FakeChatRepository.class);

    public FakeChatRepository() {
        logger.info("Fake Chat Repositroy");
    }

    private int getNextId() {
        return ++id;
    }

    @Override
    public int addChatItem(ChatItem chatItem) {
        chatItemList.add(chatItem);
        return chatItem.getId();
    }

    @Override
    public List<ChatItem> getChatHistory() {
        return this.getChatHistory(Integer.MAX_VALUE);
    }
    
    @Override
    public List<ChatItem> getChatHistory(int amount) {
        if (amount > chatItemList.size()) {
            return new ArrayList<ChatItem>(chatItemList);
        }
        return new ArrayList<ChatItem>(chatItemList.subList(chatItemList.size() - amount, chatItemList.size()));        
    }

    @Override
    public List<ChatItem> getChatHistoryFromId(int id) throws DataAccessException {
        return getChatHistory();
    }

    @Override
    public List<ChatItem> getChatHistory(Date dateFrom) {
        return getChatHistory();
    }
    
    @Override
    public List<ChatItem> getChatHistory(Date dateFrom, Date dateTo) {
        return getChatHistory();
    }    

    @Override
    public int getLastId() {
        return id;
    }

    @Override
    public int deleteAll() {
        int count = chatItemList.size();
        chatItemList.clear();
        return count;
    }

    @Override
    public int getCountOfChatItems() {
        return chatItemList.size();
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("FakeChatStorage").append("\n");
        for (ChatItem chatItem : chatItemList) {
            sb.append(chatItem).append("\n");
        }
        return sb.toString();
    }
}
