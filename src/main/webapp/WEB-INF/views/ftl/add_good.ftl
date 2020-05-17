<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <script
            src="https://code.jquery.com/jquery-3.4.1.min.js"
            integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo="
            crossorigin="anonymous"></script>
    <script src="/js/sendFile.js"></script>
    <title>Add good</title>
</head>
<body>
<h1>Добавить продукт</h1>
<div>
        <input name="title" id="title" placeholder="Название">
        <input type="number" name="price" id="price" placeholder="Цена">
        <input type="text" name="manufacturer" id="manufacturer" placeholder="Производитель">
        <input type="text" name="description" id="description" placeholder="Описание">
        <p>
            Категория:
            <label>
                <select name="type" id="type">
                    <option value="PHONE">Телефон</option>
                    <option value="NOTEBOOK">Ноутбук</option>
                    <option value="TABLET">Планшет</option>
                </select>
            </label>
        </p>
        <div>
            <input type="file" id="file" name="file" placeholder="Name file..."/>
            <button onclick="sendFile()">
                Добавить
            </button>
            <input type="hidden" id="file_hidden">
            <div class="filename"></div>
        </div>
    <#if message??>
        ${message}
    </#if>
</div>
</body>
</html>