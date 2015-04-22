// <![CDATA[

$(document).ready(function() {
    
    var servlet_url = "chat";
    var chatList = $('div#chatList');
    
    $('div#content').on('submit','form#formHistory',function(e){
        e.preventDefault();
        var form = $(e.target);

        // html5 in chrome return date in format 2015-03-30
        if(form.find('input#dateFrom').val() > form.find('input#dateTo').val()){
            parent.alert('Начальная дата должна быть меньше или равна конечной!');
            return false;
        }    



        $.ajax({
            type: "POST",
            url: "getmessage",
            data: form.serialize(),
            success: function(data){
                addChatItems(data);
                form.find('input#textMessage').val('').focus();
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log( "ошибка: " + textStatus + ": " + errorThrown);
            }
        }); //
    });

    function addChatItems(chatItems){
        var createDT;
        chatList.empty();
        for (var i = 0; i < chatItems.length; i++) {
            createDT = new Date(chatItems[i].createDT);
            chatList.prepend('<div class="media bg-info"><div class="media-body"><h4 class="media-heading text-warning">'
            + chatItems[i].user + ' <small>' + createDT.toLocaleString() + '</small></h4>' + '<p>' + chatItems[i].message
            + '</p></div></div>').find('div.media').animate({opacity: 1}, "fast");
            lastID = chatItems[i].id;
        }
    }
    
});
// ]]>
