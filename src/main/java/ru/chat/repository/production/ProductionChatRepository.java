
package ru.chat.repository.production;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import ru.chat.model.ChatItem;
import ru.chat.repository.IChatRepository;
import ru.chat.utils.Constants;

import java.util.Date;
import java.util.List;

import static ru.chat.utils.Constants.*;

@Repository
public class ProductionChatRepository implements IChatRepository {

    private Logger logger = LoggerFactory.getLogger(ProductionChatRepository.class);

    @Autowired(required=true)
    @Qualifier(value="hibernate4AnnotatedSessionFactory")
    private SessionFactory sessionFactory;

    public ProductionChatRepository() {
        logger.info("Production Chat Repository");
    }

    @Override
    public int addChatItem(ChatItem chatItem) {
        Session session = this.sessionFactory.getCurrentSession();
        int id = chatItem.getId();
        if (id == 0) {
            session.persist(chatItem);
            id = (Integer) session.save(chatItem);
        } else {
            session.update(chatItem);
        }
        return id;
    }

    @Override
    public List<ChatItem> getChatHistory() {
        return this.getChatHistory(Integer.MAX_VALUE);
    }
    
    @Override
    public List<ChatItem> getChatHistory(int count) {
        Session session = this.sessionFactory.getCurrentSession();
        return session.createQuery("from ChatItem order by id").setMaxResults(count).list();
    }

    @Override
    public List<ChatItem> getChatHistoryFromId(int id) throws DataAccessException {
        Session session = this.sessionFactory.getCurrentSession();
        return session.createQuery("from ChatItem where id > :id order by id").setInteger(FIELD_ID, id).list();
    }

    @Override
    public List<ChatItem> getChatHistory(Date dateFrom) {
        Session session = this.sessionFactory.getCurrentSession();
        List<ChatItem> chatHistory = session
                .createQuery("from ChatItem where createDT >= :dateFrom order by id").setTimestamp(FIELD_DATEFROM, dateFrom)
                .list();
        return chatHistory;
    }
    
    @Override
    public List<ChatItem> getChatHistory(Date dateFrom, Date dateTo) {
        Session session = this.sessionFactory.getCurrentSession();
        List<ChatItem> chatHistory = session.createQuery("from ChatItem where createDT >= :dateFrom and createDT < :dateTo  order by id").
                setDate(FIELD_DATEFROM, dateFrom).
                setDate(FIELD_DATETO, dateTo).
                list();
        return chatHistory;
    }    

    @Override
    public int getLastId() {
        Session session = this.sessionFactory.getCurrentSession();

        Criteria criteria = session
                .createCriteria(ChatItem.class)
                .setProjection(Projections.max(FIELD_ID));
        return (Integer)criteria.uniqueResult();
    }

    @Override
    public int deleteAll() {
        Session session = this.sessionFactory.getCurrentSession();
        int count = session.createQuery("delete from ChatItem").executeUpdate();
        logger.debug("Were deleted {} record(s)", count);
        return count;
    }

    @Override
    public int getCountOfChatItems() {
        Session session = this.sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(ChatItem.class).setProjection(Projections.rowCount());
        return ((Long) criteria.uniqueResult()).intValue();
    }
}
