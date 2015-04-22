package ru.chat.utils;

public class Constants {
    static final public String ATTRIBUTE_NAME_TTILE = "title";
    static final public String TITLE_CHAT = "Chat";
    static final public String TITLE_HISTORY = "History";


    static final public String PARAM_USERNIKNAME = "userNikName";
    static final public String PARAM_TEXTMESSAGE = "textMessage";
    static final public String PARAM_DATEFROM = "dateFrom";
    static final public String PARAM_DATETO = "dateTo";

    static final public String URL_CHAT = "/chat";
    static final public String URL_HISTORY = "/history";
    static final public String URL_ADDMESSAGE = "/addmessage";
    static final public String URL_GETMESSAGE = "/getmessage";
    static final public String URL_POLL = "/poll/{lastID}";

    static final public String PAGE_CHAT = "chat";
    static final public String PAGE_HISTORY = "history";

    static final public String FIELD_ID = "id";
    static final public String FIELD_CREATEDT = "creatDT";
    static final public String FIELD_DATEFROM = "dateFrom";
    static final public String FIELD_DATETO = "dateTo";


    static final public int PERIOD_FOR_SHOWCHAT_AT_FIRST_TIME = 120*60*1000; // two hours
    static final public int POLLING_TIMEOUT = 30; // 30 seconds



}
