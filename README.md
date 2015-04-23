<html>
<h1>Простой web chat.</h1>
</br>
Проверял на:
Java(TM) SE Runtime Environment (build 1.8.0_31-b13) </br>
apache-tomcat-8.0.21 </br>
</br>
Контроллер на запрос истории чата возвращает DeferredResult поэтому на tomcat 6 не работает. </br>
Можно раскомментировать в ChatController секцию: </br>
//  START variant ReentrantLock </br>
    public @ResponseBody int addMessage </br>
    public @ResponseBody DeferredResult<List<ChatItem>> poll </br>
и закоментировать:  </br>
//  START variant with DeferredResult </br>
    public @ResponseBody int addMessage </br>
    public @ResponseBody List<ChatItem>  poll </br>
будет работать на tomcat 6 </br>
 </br>
База по умолчанию mysql jdbc:mysql://localhost:3306/chatdb пользователь root </br>
настройки в .\src\main\resources\spring\data-access.properties  </br>
если пользователь другой, нужно раскомментировать добавление привилегий </br>
.\src\main\resources\db\mysql\initDB.sql </br>
 </br>
Тесты работают на тестовых данных. </br>

</html>
