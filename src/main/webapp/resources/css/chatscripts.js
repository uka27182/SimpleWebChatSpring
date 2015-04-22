// <![CDATA[

$(document).ready(function() {
    
    var lastID = 0;    
    var chatList = $('div#chatList');

    $('div#content').on('submit','form#formChat',function(e){
        e.preventDefault();
        var form = $(e.target);

//        if(!form.find('input#userNikname').val()){
//            form.find('input#userNikname').focus();            
//            return false;
//        }
        if(!form.find('input#textMessage').val()){
            form.find('input#textMessage').focus();
            return false;
        }
        
        $.ajax({
            type: "POST",
            url: "addmessage",
            data: form.serialize(),
            success: function(data){
                form.find('input#textMessage').val('').focus();
                console.log( "Добавлено сообщение: " + data);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log( "ошибка: " + textStatus + ": " + errorThrown);
            }
        }); //
    });
    
    function addChatItems(chatItems){
        var createDT;
        for (var i = 0; i < chatItems.length; i++) {
            createDT = new Date(chatItems[i].createDT);
            chatList.prepend('<div class="media bg-info"><div class="media-body"><h4 class="media-heading text-warning">'
                    + chatItems[i].user + ' <small>' + createDT.toLocaleString() + '</small></h4>' + '<p>' + chatItems[i].message
                    + '</p></div></div>').find('div.media').animate({opacity: 1}, "fast");                    
            lastID = chatItems[i].id;
        }
    }
    
    var timeoutId;
    
    function pollData() {
        timeoutId = setTimeout(function() {
            $.ajax({
                type: "POST",
                url: "poll/" + lastID,
                // dataType: "json",
                cache: false,
                //data: "/poll/" + lastID,
                success: function(data) {
                    console.log( "poll: " + data);
                    console.log( "poll length: " + data.length);
                    if (data.length > 0) addChatItems(data);
                    pollData();
                },
                error: function(){
                    clearTimeout(timeoutId);
                }
           });
        }, 1000);
    }
    
    pollData();
    
});
// ]]>
