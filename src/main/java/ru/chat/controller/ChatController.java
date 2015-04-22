package ru.chat.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import ru.chat.model.ChatItem;
import ru.chat.service.IChatService;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static ru.chat.utils.ChatItemFactory.getChatItem;
import static ru.chat.utils.Constants.*;
import java.util.Map.Entry;

@Controller
public class ChatController {
    private final Lock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private final Logger logger = LoggerFactory.getLogger(ChatController.class);
    private final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private final Map<DeferredResult<List<ChatItem>>, Integer> chatRequests = new ConcurrentHashMap<DeferredResult<List<ChatItem>>, Integer>();

    @Autowired
    private IChatService chatService;

    @RequestMapping(value = URL_CHAT, method = RequestMethod.GET)
    public String chat(Model model) {
        model.addAttribute(ATTRIBUTE_NAME_TTILE, TITLE_CHAT);
        return PAGE_CHAT;
    }

    @RequestMapping(value = URL_HISTORY, method = RequestMethod.GET)
    public String history(Model model) {
        model.addAttribute(ATTRIBUTE_NAME_TTILE, TITLE_HISTORY);
        return PAGE_HISTORY;
    }

    @RequestMapping(value = URL_GETMESSAGE, method = RequestMethod.POST)
    public @ResponseBody List<ChatItem> getMessage(@RequestParam String dateFrom, @RequestParam String dateTo) {
        logger.debug("----- Request param dateFrom: {}, dateTo: {}", dateFrom, dateTo);
        List<ChatItem> list = Collections.emptyList();
        try {
            list = chatService.getChatHistory(getDataFromString(dateFrom), getDataFromString(dateTo));
        } catch (ParseException e) {
            logger.error("Error while parsing date from {} or to {}", dateFrom, dateTo);
        }
        return list;
    }

////  START variant ReentrantLock

//    @RequestMapping(value = URL_ADDMESSAGE, method = RequestMethod.POST)
//    public @ResponseBody int addMessage(@RequestParam String userNikName, @RequestParam String textMessage) {
//        logger.debug("----- Request param userNikName: {}, textMessage: {}", userNikName, textMessage);
//        userNikName = (userNikName != null && !userNikName.isEmpty()) ? userNikName : "anonymous";
//        int id = chatService.addChatItem(getChatItem(userNikName, textMessage));
//        lock.lock();
//        try {
//            condition.signalAll();
//        } finally {
//            lock.unlock();
//        }
//        return id;
//    }
//
//    @RequestMapping(value= URL_POLL, method = RequestMethod.POST)
//    public @ResponseBody List<ChatItem>  poll(@PathVariable Integer lastID){
//        List<ChatItem> list;
//        logger.debug("----- Last printed id - {}", lastID);
//        if (lastID == 0) {
//            list = chatService.getChatHistory(new Date(System.currentTimeMillis() - PERIOD_FOR_SHOWCHAT_AT_FIRST_TIME));
//        } else {
//            list = chatService.getChatHistoryFromId(lastID);
//        }
//        if (list.isEmpty()) {
//            // wait for the end of polling or adding a new message
//            lock.lock();
//            try {
//                condition.await(POLLING_TIMEOUT, TimeUnit.SECONDS);
//            } catch (InterruptedException e) {
//                logger.debug("Servlet was interrupted");
//            } finally {
//                lock.unlock();
//            }
//        }
//        return list;
//    }

////  END variant ReentrantLock



////  START variant with DeferredResult

    @RequestMapping(value = URL_ADDMESSAGE, method = RequestMethod.POST)
    public @ResponseBody int addMessage(@RequestParam String userNikName, @RequestParam String textMessage) {
        logger.debug("----- Request param userNikName: {}, textMessage: {}", userNikName, textMessage);
        userNikName = (userNikName != null && !userNikName.isEmpty()) ? userNikName : "anonymous";
        int id = chatService.addChatItem(getChatItem(userNikName, textMessage));

        // Update all chat requests as part of the POST request
        for (Entry<DeferredResult<List<ChatItem>>, Integer> entry : this.chatRequests.entrySet()) {
            entry.getKey().setResult(Collections.<ChatItem> emptyList());
        }
        return id;
    }

    @RequestMapping(value= URL_POLL, method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public DeferredResult<List<ChatItem>> poll(@PathVariable Integer lastID){
        List<ChatItem> list;
        final DeferredResult<List<ChatItem>> deferredResult = new DeferredResult<List<ChatItem>>(null, Collections.emptyList());
        this.chatRequests.put(deferredResult, lastID);
        deferredResult.onCompletion(new Runnable() {
            @Override
            public void run() {
                chatRequests.remove(deferredResult);
            }
        });
        if (lastID == 0) {
            list = chatService.getChatHistory(new Date(System.currentTimeMillis() - PERIOD_FOR_SHOWCHAT_AT_FIRST_TIME));
        } else {
            list = chatService.getChatHistoryFromId(lastID);
        }

        if (!list.isEmpty()) {
            deferredResult.setResult(list);
        }
        return deferredResult;
    }

////  END variant with DeferredResult

    @ExceptionHandler(IOException.class)
    public void clientAbortException(IOException e) {
        logger.warn("IOException: " + e);
    }

    @ExceptionHandler(DataAccessException.class)
    public void dataAccessException(DataAccessException e) {
        logger.warn("DataAccessException: " + e);
    }

    private Date getDataFromString(String date) throws ParseException {
        return dateFormat.parse(date);
    }
}
