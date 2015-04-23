
package ru.chat.repository;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;
import ru.chat.model.ChatItem;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

@Ignore
@ContextConfiguration(locations = {"classpath:spring/root-context.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("production")
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class ChatRepositoryTest {

    private Logger logger = LoggerFactory.getLogger(ChatRepositoryTest.class);

//    @Autowired
//    private IChatService service;

    @Autowired
    public IChatRepository repository;

    @Test
    public void getDeleteAll() {
        int count = repository.deleteAll();
        assertThat(count, is(10));
    }

    @Test
    public void addChatItemTest() {
        ChatItem chatItem = new ChatItem();
        chatItem.setUser("TestUser 100");
        chatItem.setMessage("TestMessage 100");
        chatItem.setCreateDT(new Date());
        int id = repository.addChatItem(chatItem);

        ChatItem chatItem2 = new ChatItem();
        chatItem2.setUser("TestUser 101");
        chatItem2.setMessage("TestMessage 101");
        chatItem2.setCreateDT(new Date());
        int id2 = repository.addChatItem(chatItem2);
    }

    @Test
    public void getLastIdTest() {
        int id = repository.getLastId();
        logger.debug("---------- getLastIdTest - {}", id);
        assertThat(id, is(10));
    }

    @Test
    public void getHistoryCountTest() {
        int count = 5;
        List<ChatItem> list = repository.getChatHistory(count);
        for (ChatItem chatItem: list) {
            logger.debug("---------- getHistoryCountTest - {}", chatItem);
        }
        assertThat(list.size(), is(count));
    }

    @Test
    public void getHistoryFromId() {
        int id = 5;
        List<ChatItem> list = repository.getChatHistoryFromId(id);
        for (ChatItem chatItem: list) {
            logger.debug("---------- getHistoryFromId - {}", chatItem);
        }
        assertThat(list.size(), is(5));
    }

    @Test
    public void getHistoryDateFromTest() {
        Date dateFrom = getDate(2015, Calendar.APRIL, 22, 20, 20, 0);
        logger.debug("---------- getHistoryDateFromTest, data from - {}", dateFrom);
        List<ChatItem> list = repository.getChatHistory(dateFrom);
        for (ChatItem chatItem: list) {
            logger.debug("---------- getHistoryDateFromTest - {}", chatItem);
        }
        assertThat(list.size(), is(2));
    }

    @Test
    public void getHistoryDateFromToTest() {
        Date dateFrom = getDate(2015, Calendar.APRIL, 21, 0, 0, 0);
        Date dateTo = getDate(2015, Calendar.APRIL, 22, 0, 0, 0);
        List<ChatItem> list = repository.getChatHistory(dateFrom, dateTo);
        for (ChatItem chatItem: list) {
            logger.debug("---------- getHistoryDateFromToTest -" + chatItem);
        }
        assertThat(list.size(), is(3));
    }

    @Test
    public void getCountChatItem() {
        int count = repository.getCountOfChatItems();
        logger.debug("---------- getCountChatItem - {}", count);
        assertThat(repository.getChatHistory().size(), is(10));
    }


    @Test
    public void getEmptyHistory() {
        int id = repository.getLastId();
        List<ChatItem> list = repository.getChatHistoryFromId(id);
        for (ChatItem chatItem: list) {
            logger.debug("---------- getEmptyHistory - {}", chatItem);
        }
        assertThat(list.size(), is(0));
    }

    public Date getDate(int year, int month, int date, int hourOfDay, int minute, int second) {
        Calendar c = new GregorianCalendar();
        c.set(year, month, date, hourOfDay, minute, second);
        return c.getTime();
    }
}
