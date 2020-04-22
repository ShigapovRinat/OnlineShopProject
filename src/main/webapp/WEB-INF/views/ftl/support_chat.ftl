<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <script
            src="https://code.jquery.com/jquery-3.4.1.min.js"
            integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
            crossorigin="anonymous"></script>
    <script src="/resources/js/chat.js"></script>
</head>
<body onload="sendMessage('${pageId}', 'Здравствуйте, чем я могу Вам помочь?', 1)">
<div>
    <input id="message" placeholder="Ваше сообщение">
    <#if is_admin>
        <input type="number" id="whomId" value="1" placeholder="Кому">
    </#if>
    <button onclick="sendMessage('${pageId}',
            $('#message').val(), $('#whomId').val())">Отправить
    </button>
</div>
<div>
    <ul id="messages">
        <#list messages as message>
            <#if message.fromId == 1>
                <li>admin ${message.text}</li>
            <#else>
                <li>${pageId} ${message.text}</li>
            </#if>

        </#list>
    </ul>
</div>
</body>
</html>