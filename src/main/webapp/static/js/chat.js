var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
function sendMessage(pageId, text, whomId) {
    let body = {
        pageId: pageId,
        text: text,
        whomId: whomId
    };

    $.ajax({

        url: "/messages",
        method: "POST",
        data: JSON.stringify(body),
        contentType: "application/json",
        dataType: "json",
        complete: function () {
            if (text === "Здравствуйте, чем я могу Вам помочь?") {
                receiveMessage(pageId)
            }
        },
        beforeSend: function( xhr ) {
            xhr.setRequestHeader(header, token);
        }
    });
}

// LONG POLLING
function receiveMessage(pageId) {

    $.ajax({
        url: "/messages?pageId=" + pageId,
        method: "GET",
        dataType: "json",
        contentType: "application/json",
        success: function (response) {
            var userName = pageId;
            if ((response[0]['whomId'] != "1" || response[0]['pageId'] === "89179060010@mail.ru")
                || response[0]['text'] === "Здравствуйте, чем я могу Вам помочь?") {
                userName = "admin";
            }
            $('#messages').first().after('<li>' + userName + " " + response[0]['text'] + '</li>');
            receiveMessage(pageId);
        },
        beforeSend: function( xhr ) {
            xhr.setRequestHeader(header, token);
        }
    });
}