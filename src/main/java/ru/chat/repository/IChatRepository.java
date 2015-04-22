package ru.chat.repository;


import org.springframework.dao.DataAccessException;
import ru.chat.model.ChatItem;

import java.util.Date;
import java.util.List;

public interface IChatRepository {

    /**
     * Add the ChatItem to storage and return its id
     * @param chatItem
     * @return the identifier of the added ChatItem
     * @throws DataAccessException
     */
    public int addChatItem(ChatItem chatItem) throws DataAccessException;

    /**
     * Get all chat history
     * @return list of ChatItem
     * @throws DataAccessException
     */
    public List<ChatItem> getChatHistory() throws DataAccessException;

    /**
     * Get the last count of chat history
     * @param count
     * @return list of ChatItem
     * @throws DataAccessException
     */
    public List<ChatItem> getChatHistory(int count) throws DataAccessException;

    /**
     * Get the chat history from id  (not including id)
     * @param id
     * @return list of ChatItem
     * @throws DataAccessException
     */
    public List getChatHistoryFromId(int id) throws DataAccessException;

    /**
     * Get chat history from dateFrom
     * @param dateFrom
     * @return list of ChatItem
     * @throws DataAccessException
     */
    public List<ChatItem> getChatHistory(Date dateFrom) throws DataAccessException;

    /**
     * Get chat history from dateFrom to dateTo
     * @param dateFrom
     * @param dateTo
     * @return list of ChatItem
     * @throws DataAccessException
     */
    public List<ChatItem> getChatHistory(Date dateFrom, Date dateTo) throws DataAccessException;

    /**
     * Get the last ChatItem id
     * @return the last ChatItem id
     * @throws DataAccessException
     */
    public int getLastId() throws DataAccessException;

    /**
     * Delete all ChatItem from storage
     * @throws DataAccessException
     */
    public int deleteAll() throws DataAccessException;

    /**
     * Get count of chat items in storage
     * @return count of chat items in storage
     * @throws DataAccessException
     */     
    public int getCountOfChatItems() throws DataAccessException;
}
