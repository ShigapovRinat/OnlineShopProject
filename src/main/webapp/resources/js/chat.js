function sendMessage(pageId, text, whomId) {
    let body = {
        pageId: pageId,
        text: text,
        whomId: whomId
    };

    // var token = $("meta[name='_csrf']").attr("content");
    // var header = $("meta[name='_csrf_header']").attr("content");

    $.ajax({

        url: "/messages",
        method: "POST",
        data: JSON.stringify(body),
        contentType: "application/json",
        dataType: "json",
        // beforeSend: function (xhr) {
        //     xhr.setRequestHeader(header, token);
        // },
        complete: function () {
            if (text === "Здравствуйте, чем я могу Вам помочь?") {
                receiveMessage(pageId)
            }
        }
    });
}

// LONG POLLING
function receiveMessage(pageId) {

    // var token = $("meta[name='_csrf']").attr("content");
    // var header = $("meta[name='_csrf_header']").attr("content");

    $.ajax({
        url: "/messages?pageId=" + pageId,
        method: "GET",
        dataType: "json",
        contentType: "application/json",
        // beforeSend: function(xhr) {
        //     xhr.setRequestHeader(header, token)
        // },
        success: function (response) {
            $('#messages').first().after('<li>' + response[0]['text'] + '</li>');
            receiveMessage(pageId);
        }
    })
}