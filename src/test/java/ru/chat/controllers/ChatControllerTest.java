package ru.chat.controllers;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import ru.chat.controller.ChatController;
import ru.chat.model.ChatItem;
import ru.chat.service.IChatService;

import java.util.Date;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.chat.utils.ChatItemFactory.getChatItemAsList;
import static ru.chat.utils.Constants.*;

@Ignore
@RunWith(MockitoJUnitRunner.class)
public class ChatControllerTest {

    private final Logger logger = LoggerFactory.getLogger(ChatControllerTest.class);

    private MockMvc mockMvc;

    @InjectMocks
    private ChatController chatController;

    @Mock
    private IChatService service;

    @Before
    public void setUp() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/pages/");
        viewResolver.setSuffix(".jsp");

        mockMvc = MockMvcBuilders.standaloneSetup(chatController)
                .setViewResolvers(viewResolver)
                .build();

        List<ChatItem> list = getChatItemAsList(2,"user", "message", new Date());
        when(service.getChatHistory(any(Date.class), any(Date.class))).thenReturn(list);
        when(service.getChatHistory(any(Date.class))).thenReturn(list);
        when(service.getChatHistoryFromId(any(Integer.class))).thenReturn(list);
    }

    @Test
    public void getChat() throws Exception {
        logger.info(" ********** {}", URL_CHAT);
        mockMvc.perform(get("/chat"))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/WEB-INF/pages/chat.jsp"));

        verifyZeroInteractions(service);
    }

    @Test
    public void getHistory() throws Exception {
        mockMvc.perform(get(URL_HISTORY))
                .andExpect(status().isOk())
                .andExpect(forwardedUrl("/WEB-INF/pages/history.jsp"));

        verifyZeroInteractions(service);
    }

    @Test
    public void getMessageTest() throws Exception {
        mockMvc.perform(post(URL_GETMESSAGE)
                .param(PARAM_DATEFROM, "2015-04-20")
                .param(PARAM_DATETO, "2015-04-20"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].user").value("user"));

        verify(service, times(1)).getChatHistory(any(Date.class), any(Date.class));
        verifyNoMoreInteractions(service);
    }


    @Test
    public void addMessageTest() throws Exception {
        mockMvc.perform(post(URL_ADDMESSAGE)
                .param(PARAM_USERNIKNAME, "user")
                .param(PARAM_TEXTMESSAGE, "message"))
                .andExpect(status().isOk())
                //.andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$").value(0));

        verify(service, times(1)).addChatItem(any(ChatItem.class));
        verifyNoMoreInteractions(service);
    }

    @Test
    @Ignore
    public void pollTest_AtFirstRequest() throws Exception {
        mockMvc.perform(post(URL_POLL.replaceAll("\\{.*\\}", "0")))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].user").value("user"));

        verify(service, times(1)).getChatHistory(any(Date.class));
        verifyNoMoreInteractions(service);
    }

    @Test
    @Ignore
    public void pollTest_Update() throws Exception {
        mockMvc.perform(post(URL_POLL.replaceAll("\\{.*\\}","1")).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].user").value("user"));

        verify(service, times(1)).getChatHistoryFromId(any(Integer.class));
        verifyNoMoreInteractions(service);
    }

    @Test
    public void pollTestAsync_AtFirstRequest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post(URL_POLL.replaceAll("\\{.*\\}", "0")))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].user").value("user"));

        verify(service, times(1)).getChatHistory(any(Date.class));
        verifyNoMoreInteractions(service);
    }

    @Test
    public void pollTestAsync_Update() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post(URL_POLL.replaceAll("\\{.*\\}", "1")))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].user").value("user"));

        verify(service, times(1)).getChatHistoryFromId(any(Integer.class));
        verifyNoMoreInteractions(service);
    }
}
