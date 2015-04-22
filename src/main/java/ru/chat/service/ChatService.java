package ru.chat.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.chat.model.ChatItem;
import ru.chat.repository.IChatRepository;

import java.util.Date;
import java.util.List;

@Service
public class ChatService implements IChatService {

    @Autowired
    private IChatRepository chatRepository;

    private Logger logger = LoggerFactory.getLogger(ChatService.class);

    @Override
    @Transactional
    public int addChatItem(ChatItem chatItem) throws DataAccessException {
        return chatRepository.addChatItem(chatItem);
    }

    @Override
    @Transactional
    public List<ChatItem> getChatHistory() throws DataAccessException {
        return chatRepository.getChatHistory();
    }

    @Override
    @Transactional
    public List<ChatItem> getChatHistory(int count) throws DataAccessException {
        return chatRepository.getChatHistory(count);
    }

    @Override
    @Transactional
    public List<ChatItem> getChatHistoryFromId(int id) throws DataAccessException {
        return chatRepository.getChatHistoryFromId(id);
    }

    @Override
    @Transactional
    public List<ChatItem> getChatHistory(Date dateFrom) throws DataAccessException {
        return chatRepository.getChatHistory(dateFrom);
    }

    @Override
    @Transactional
    public List<ChatItem> getChatHistory(Date dateFrom, Date dateTo) throws DataAccessException {
        return chatRepository.getChatHistory(dateFrom, dateTo);
    }

    @Override
    @Transactional
    public int getLastId() throws DataAccessException {
        return chatRepository.getLastId();
    }

    @Override
    @Transactional
    public int deleteAll() throws DataAccessException {
        return chatRepository.deleteAll();
    }

    @Override
    @Transactional
    public int getCountOfChatItems() throws DataAccessException {
        return chatRepository.getCountOfChatItems();
    }
}
