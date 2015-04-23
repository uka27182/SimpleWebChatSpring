Простой web chat. 

Проверял на:
Java(TM) SE Runtime Environment (build 1.8.0_31-b13)
apache-tomcat-8.0.21

Контроллер на запрос истории чата возвращает DeferredResult поэтому на tomcat 6 не работает.
Можно раскомментировать в ChatController секцию: 
//  START variant ReentrantLock
    public @ResponseBody int addMessage
    public @ResponseBody DeferredResult<List<ChatItem>> poll
и закоментировать:
//  START variant with DeferredResult
    public @ResponseBody int addMessage
    public @ResponseBody List<ChatItem>  poll
будет работать на tomcat 6

База по умолчанию mysql jdbc:mysql://localhost:3306/chatdb пользователь root
настройки в .\src\main\resources\spring\data-access.properties 
если пользователь другой, нужно раскомментировать добавление привилегий
.\src\main\resources\db\mysql\initDB.sql 

Тесты работают на тестовых данных.
